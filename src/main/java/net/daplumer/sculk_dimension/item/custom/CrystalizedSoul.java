package net.daplumer.sculk_dimension.item.custom;

import net.daplumer.sculk_dimension.util.statistics.SoulHolder;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

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
        return 16;
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        ActionResult firstResult = super.use(world, user, hand);
        if (firstResult == ActionResult.PASS) return SoulHolder.tryHealItems(user.getInventory(),user.getStackInHand(hand));
        return firstResult;
    }
}
