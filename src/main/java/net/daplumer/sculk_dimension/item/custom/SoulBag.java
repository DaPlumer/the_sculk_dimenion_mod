package net.daplumer.sculk_dimension.item.custom;

import net.daplumer.sculk_dimension.component.ModDataComponentTypes;
import net.daplumer.sculk_dimension.item.ModItems;
import net.daplumer.sculk_dimension.util.statistics.SoulHolder;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.ClickType;
import net.minecraft.util.math.ColorHelper;


public class SoulBag extends Item implements SoulHolder {
    private static final int ITEM_BAR_COLOR = ColorHelper.fromFloats(1.0F, 0.44F, 0.53F, 1.0F);
    private static final int FULL_ITEM_BAR_COLOR = ColorHelper.fromFloats(1.0F, 1.0F, 0.33F, 0.33F);
    public SoulBag(Settings settings) {
        super(settings);
    }
    public static final int maxSouls = 256;

    @Override
    public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player) {
        int souls = GetSouls(stack);
        ItemStack otherStack = slot.getStack();
        if(otherStack.isEmpty()){
            if(clickType == ClickType.RIGHT){
                int movedSouls = Math.min(souls,SoulHolder.maxSouls(ModItems.CRYSTALIZED_SOUL.getDefaultStack()));
                slot.setStack(new ItemStack(ModItems.CRYSTALIZED_SOUL, movedSouls));
                SoulHolder.takeSouls(stack,movedSouls);
                return true;
            }
            return false;
        }
        if(otherStack.isOf(ModItems.CRYSTALIZED_SOUL)){
            SoulHolder.moveSouls(otherStack,stack,clickType == ClickType.RIGHT);
            return true;
        }
        if(otherStack.getItem() instanceof SoulHolder){
            SoulHolder.moveSouls(stack,otherStack,clickType == ClickType.RIGHT);
            return true;
        }
        return false;
    }

    @Override
    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        int souls = GetSouls(stack);
        if(otherStack.isOf(ModItems.CRYSTALIZED_SOUL)){
            SoulHolder.moveSouls(otherStack,stack,clickType == ClickType.RIGHT);
            return true;
        }
        if(otherStack.isEmpty() && clickType == ClickType.RIGHT){
            int soulsTaken = Math.min(souls,SoulHolder.maxSouls(ModItems.CRYSTALIZED_SOUL.getDefaultStack()));
            SoulHolder.takeSouls(stack,soulsTaken);
            cursorStackReference.set(new ItemStack(ModItems.CRYSTALIZED_SOUL, soulsTaken));
            cursorStackReference.get().setCount(soulsTaken);
            return true;
        }
        return false;
    }

    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        return GetSouls(stack) > 0;
    }

    @Override
    public int getItemBarStep(ItemStack stack) {
        return (int)(GetSouls(stack)*13.0f)/maxSouls;
    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        return GetSouls(stack) == maxSouls?FULL_ITEM_BAR_COLOR:ITEM_BAR_COLOR;
    }

    @Override
    public void onItemEntityDestroyed(ItemEntity entity) {
        super.onItemEntityDestroyed(entity);
        if(entity.getWorld().isClient) return;
        ItemStack stack = entity.getStack();
        if(GetSouls(stack) == 0) return;
        ItemStack spawnStack = ModItems.CRYSTALIZED_SOUL.getDefaultStack();
        spawnStack.setCount(GetSouls(stack));
        entity.getWorld().spawnEntity(new ItemEntity(entity.getWorld(),entity.getX(),entity.getY(),entity.getZ(),spawnStack));
    }

    @Override
    public int maxSouls() {
        return maxSouls;
    }

    @Override
    public int GetSouls(ItemStack stack) {
        return stack.getOrDefault(ModDataComponentTypes.STORED_SOULS, 0);
    }

    @Override
    public void SetSouls(ItemStack stack, int value) {
        stack.set(ModDataComponentTypes.STORED_SOULS,value);
    }
}
