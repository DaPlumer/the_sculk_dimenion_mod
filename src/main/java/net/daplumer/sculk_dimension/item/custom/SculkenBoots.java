package net.daplumer.sculk_dimension.item.custom;

import net.daplumer.sculk_dimension.util.statistics.Insanity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SculkenBoots extends Item {
    public SculkenBoots(Settings settings) {
        super(settings);
    }

    @Override
    public void onCraftByPlayer(ItemStack stack, PlayerEntity player) {
        if(player instanceof Insanity insanity) insanity.addInsanity(5);
        super.onCraftByPlayer(stack, player);
    }
}
