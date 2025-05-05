package net.daplumer.sculk_dimension.item.custom;

import net.daplumer.sculk_dimension.component.ModDataComponentTypes;
import net.daplumer.sculk_dimension.item.ModItems;
import net.daplumer.sculk_dimension.util.Minimum;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.ClickType;
import net.minecraft.util.math.ColorHelper;


public class SoulBag extends Item {
    private static final int ITEM_BAR_COLOR = ColorHelper.fromFloats(1.0F, 0.44F, 0.53F, 1.0F);
    private static final int FULL_ITEM_BAR_COLOR = ColorHelper.fromFloats(1.0F, 1.0F, 0.33F, 0.33F);
    public SoulBag(Settings settings) {
        super(settings);
    }
    public static final int maxSouls = 256;

    @Override
    public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player) {
        int souls = getSouls(stack);
        ItemStack otherStack = slot.getStack();
        if (!otherStack.isOf(ModItems.CRYSTALIZED_SOUL) &! otherStack.isEmpty() &! otherStack.isOf(ModItems.SOUL_BAG)) return false;
        if(otherStack.isOf(ModItems.SOUL_BAG)){
            int stackSize = clickType == ClickType.LEFT?maxSouls:1;
            int otherSouls = getSouls(otherStack);
            int soulsTaken = Minimum.of(souls,stackSize,maxSouls - otherSouls);
            subtractSouls(stack,soulsTaken);
            addSouls(otherStack,soulsTaken);
            return soulsTaken > 0;
        }
        if (otherStack.isEmpty()) {
            if (clickType == ClickType.LEFT) return false;
            int soulsTaken = Math.min(souls, 64);
            subtractSouls(stack, soulsTaken);
            slot.setStack(ModItems.CRYSTALIZED_SOUL.getDefaultStack());
            slot.getStack().setCount(soulsTaken);
            return soulsTaken > 0;
        }
        if(clickType == ClickType.RIGHT){
            if (clickType == ClickType.LEFT) return false;
            int soulsTaken = Math.min(souls, 64 - otherStack.getCount());
            subtractSouls(stack, soulsTaken);
            otherStack.setCount(soulsTaken + otherStack.getCount());
            return soulsTaken > 0;

        }
        int soulsAdded = Math.min(otherStack.getCount(), maxSouls - souls);
        //add souls to bag
        addSouls(stack, soulsAdded);
        otherStack.decrement(soulsAdded);
        return soulsAdded > 0;

    }

    @Override
    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        int stackSize = clickType == ClickType.LEFT?64:1;
        int souls = getSouls(stack);
        if(!otherStack.isOf(ModItems.CRYSTALIZED_SOUL) && !otherStack.isEmpty()) return false;
        if(otherStack.isEmpty()){
            if(clickType == ClickType.LEFT) return false;
            int soulsTaken = Math.min(souls,64);
            subtractSouls(stack,soulsTaken);
            cursorStackReference.set(ModItems.CRYSTALIZED_SOUL.getDefaultStack());
            cursorStackReference.get().setCount(soulsTaken);
            return soulsTaken > 0;
        }
        int soulsAdded = Math.min(Math.min(otherStack.getCount(),stackSize),maxSouls - souls);
        addSouls(stack, soulsAdded);
        otherStack.decrement(soulsAdded);
        return soulsAdded > 0;
    }

    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        return getSouls(stack) > 0;
    }

    @Override
    public int getItemBarStep(ItemStack stack) {
        return (int)(getSouls(stack)*13.0f)/maxSouls;
    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        return getSouls(stack) == maxSouls?FULL_ITEM_BAR_COLOR:ITEM_BAR_COLOR;
    }

    @Override
    public void onItemEntityDestroyed(ItemEntity entity) {
        super.onItemEntityDestroyed(entity);
        if(entity.getWorld().isClient) return;
        ItemStack stack = entity.getStack();
        if(getSouls(stack) == 0) return;
        ItemStack spawnStack = ModItems.CRYSTALIZED_SOUL.getDefaultStack();
        spawnStack.setCount(getSouls(stack));
        entity.getWorld().spawnEntity(new ItemEntity(entity.getWorld(),entity.getX(),entity.getY(),entity.getZ(),spawnStack));
    }

    public static void setSouls(ItemStack stack, int souls){
        stack.set(ModDataComponentTypes.STORED_SOULS,souls);
    }
    public static int getSouls(ItemStack stack){
        return stack.getOrDefault(ModDataComponentTypes.STORED_SOULS,0);
    }
    public static void addSouls(ItemStack stack, int souls){
        setSouls(stack,getSouls(stack) + souls);
    }
    public static void subtractSouls(ItemStack stack, int souls){
        setSouls(stack,getSouls(stack) - souls);
    }
}
