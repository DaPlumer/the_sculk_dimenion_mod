package net.daplumer.sculk_dimension.item.custom.broken_echo;

import net.daplumer.sculk_dimension.TheSculkDimension;
import net.daplumer.sculk_dimension.component.ModDataComponentTypes;
import net.daplumer.sculk_dimension.item.ModItems;
import net.daplumer.sculk_dimension.util.statistics.Insanity;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

import static net.daplumer.sculk_dimension.item.custom.broken_echo.EchoStageData.sanityToTooltip;

public class BrokenEcho extends Item {


    //update tooltips based on insanity level
    public static void registerCustomData(){
        ItemTooltipCallback.EVENT.register((stack, context, lines, texts) ->
        {
            if (stack.getItem() == ModItems.BROKEN_ECHO){
                texts.addAll(sanityToTooltip.get(getStage(stack)));
            }
        });
    }
    public BrokenEcho(Settings settings) {
        super(settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, ServerWorld world, Entity entity, @Nullable EquipmentSlot slot) {

        if (!world.isClient) {
            // update insanity of item to insanity of player holding it
            if (entity instanceof PlayerEntity) {
                stack.set(ModDataComponentTypes.INSANITY, Insanity.get((PlayerEntity) entity));
            }
        }
        super.inventoryTick(stack,world,entity,slot);
    }
    public enum Stage {
        HINGED,   // for insanity equal to 0
        SANE,     // for insanity between 00 and 06
        NORMAL,   // for insanity between 07 and 14
        WIERD,    // for insanity between 15 and 29
        UNHINGED, // for insanity between 30 and 44
        CRAZY,    // for insanity between 45 and 59
        MAD,      // for insanity between 60 and 76
        INSANE,   // for insanity between 75 and 99
        ELDRITCH  // for insanity equal to 100
    }

    public static Stage getStage(ItemStack stack){
        //get a stage to approximate the insanity of the item
        try {
            //get base Stage based on insanity data stored within stack
            @SuppressWarnings("DataFlowIssue") int insanity = stack.get(ModDataComponentTypes.INSANITY);
            if      (insanity ==  0) {return Stage.HINGED;  }
            else if (insanity <   7) {return Stage.SANE;    }
            else if (insanity <  15) {return Stage.NORMAL;  }
            else if (insanity <  30) {return Stage.WIERD;   }
            else if (insanity <  45) {return Stage.UNHINGED;}
            else if (insanity <  60) {return Stage.CRAZY;   }
            else if (insanity <  75) {return Stage.MAD;     }
            else if (insanity < 100) {return Stage.INSANE;  }
            else                     {return Stage.ELDRITCH;}

        } catch (NullPointerException e) {
            //call if NullPointerException found and default to hinged
            return Stage.HINGED;
        } catch (Exception ex) {
            TheSculkDimension.LOGGER.info("HOW THE FUCK DID YOU CAUSE AN EXCEPTION OTHER THAN THE NULLPOINTEREXCEPTION IN THE BROKEN ECHO ITEM CLASS!!??");
            throw new RuntimeException(ex);
        }
    }
    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (getStage(context.getStack()).equals(Stage.ELDRITCH)){
            return ActionResult.FAIL;
        }
        Insanity.add(1, Objects.requireNonNull(context.getPlayer()));
        return ActionResult.SUCCESS;
    }

}
