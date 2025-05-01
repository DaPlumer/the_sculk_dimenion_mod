package net.daplumer.sculk_dimension.item.custom;

import net.daplumer.sculk_dimension.block.ModBlocks;
import net.daplumer.sculk_dimension.item.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;

public class MossyBag extends Item {
    public MossyBag(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        BlockState state = context.getWorld().getBlockState(context.getBlockPos());
        if (state.isOf(ModBlocks.ECHOING_BLOOM) && context.getPlayer() != null){
            context.getPlayer().setStackInHand(context.getHand(), ModItems.RESONANT_POLLEN.getDefaultStack().copyWithCount(context.getStack().getCount()));
            return ActionResult.SUCCESS;
        }
        return ActionResult.FAIL;
    }
}
