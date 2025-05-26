package net.daplumer.sculk_dimension.block.custom;

import net.daplumer.sculk_dimension.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootWorldContext;

import java.util.List;

public class MemoryGemOre extends Block {
    public MemoryGemOre(Settings settings) {
        super(settings);
    }

    @Override
    protected List<ItemStack> getDroppedStacks(BlockState state, LootWorldContext.Builder builder) {
        return super.getDroppedStacks(state, builder).stream().peek((stack)-> {
            if(stack.isOf(ModItems.MEMORY_GEM)) stack.setDamage(stack.getMaxDamage());
        }).toList();
    }
}
