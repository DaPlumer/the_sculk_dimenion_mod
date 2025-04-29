package net.daplumer.sculk_dimension.item.custom;

import net.daplumer.sculk_dimension.block.ModBlockTags;
import net.daplumer.sculk_dimension.component.ModProperties;
import net.daplumer.sculk_dimension.item.ModItems;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.BlockState;
import net.minecraft.item.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.world.WorldEvents;

public class WaxBrick extends Item {
    public WaxBrick(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (context.getPlayer() == null) {
            return ActionResult.PASS;
        }
        BlockState state = context.getWorld().getBlockState(context.getBlockPos());
        if (state.isIn(ModBlockTags.VIBRATOR)){
            if (!state.get(ModProperties.WAXED)) {
                if (context.getPlayer() instanceof ServerPlayerEntity) {
                    Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity)context.getPlayer(), context.getBlockPos(), context.getStack());
                }

                context.getWorld().setBlockState(context.getBlockPos(),context.getWorld().getBlockState(context.getBlockPos()).with(ModProperties.WAXED, true));
                context.getStack().decrementUnlessCreative(1, context.getPlayer());
                if(!context.getPlayer().isCreative()) context.getPlayer().giveOrDropStack(ModItems.MOSSY_BAG.getDefaultStack());
                context.getWorld().syncWorldEvent(context.getPlayer(), WorldEvents.BLOCK_WAXED, context.getBlockPos(), 0);
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.PASS;
    }
}
