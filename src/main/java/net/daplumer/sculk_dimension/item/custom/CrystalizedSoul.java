package net.daplumer.sculk_dimension.item.custom;

import net.daplumer.sculk_dimension.util.statistics.SoulHolder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CrystalizedSoul extends Item implements SoulHolder {
    public CrystalizedSoul(Settings settings) {
        super(settings);
    }

    @Override
    public int GetSouls(ItemStack stack) {
        return stack.getCount();
    }

    @Override
    public void SetSouls(ItemStack stack, int value) {
        stack.setCount(value);
    }

    @Override
    public int maxSouls() {
        return 64;
    }
}
