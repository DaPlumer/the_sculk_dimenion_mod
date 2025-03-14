package net.daplumer.sculk_dimension.item.custom;

import net.daplumer.sculk_dimension.block.custom.EchoingBloom;
import net.daplumer.sculk_dimension.item.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.item.*;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;

public class MossyBag extends Item {
    public MossyBag(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        BlockState state = context.getWorld().getBlockState(context.getBlockPos());
        if (state.getBlock().getClass().equals(EchoingBloom.class) && context.getPlayer() != null){
            if (state.get(Properties.DOUBLE_BLOCK_HALF).equals(DoubleBlockHalf.UPPER)){
                context.getPlayer().setStackInHand(context.getHand(),ModItems.RESONANT_POLLEN.getDefaultStack());
                return ActionResult.SUCCESS;            }
        }
        return ActionResult.FAIL;
    }
}
