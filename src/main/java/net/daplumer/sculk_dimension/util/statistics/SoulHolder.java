package net.daplumer.sculk_dimension.util.statistics;

import net.daplumer.sculk_dimension.item.ModItems;
import net.daplumer.sculk_dimension.util.Minimum;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

public interface SoulHolder {
    int GetSouls(ItemStack stack);
    void SetSouls(ItemStack stack, int value);

    /**
     * @param stack the stack souls are being added to
     * @param value the number of souls being added
     * @return remainder
     */
    static int addSouls(ItemStack stack, int value){
        if(!(stack.getItem() instanceof SoulHolder holder)) return value;
        holder.SetSouls(stack,Math.min(holder.GetSouls(stack) + value,holder.maxSouls()));
        return Math.max(0,value - (holder.maxSouls() -holder.GetSouls(stack)));
    }

    /**
     * @param stack the stack souls are being taken from
     * @param value number of souls being taken
     * @return souls still needed to be taken
     */
    static int takeSouls(ItemStack stack, int value){
        if(!(stack.getItem() instanceof SoulHolder holder)) return value;
        holder.SetSouls(stack,Math.max(holder.GetSouls(stack) - value,0));
        return Math.max(0,value - holder.GetSouls(stack));
    }
    static boolean hasSouls(ItemStack stack){
        if(stack.getItem() instanceof SoulHolder holder) return holder.GetSouls(stack) > 0;
        return false;
    }
    static int getSouls(ItemStack stack){
        if(stack.getItem() instanceof SoulHolder holder) return holder.GetSouls(stack);
        return 0;
    }
    static boolean setSouls(ItemStack stack, int value){
        if(stack.getItem() instanceof SoulHolder holder){
            holder.SetSouls(stack,value);
            return true;
        }
        return false;
    }

    int maxSouls();

    //returns any souls that can't fit
    static int giveSouls(Inventory inventory, int souls){
        for (int i = 0; i < inventory.size(); i++){
            souls = addSouls(inventory.getStack(i),souls);
            if (souls == 0) return 0;
        }
        for (int i = 0; i < inventory.size(); i++){
            if(inventory.getStack(i).isEmpty()){
                inventory.setStack(i, new ItemStack(ModItems.CRYSTALIZED_SOUL, Math.min(souls,64)));
                souls -= Math.min(souls,64);
            }
        }
        return souls;
    }

    static int totalSouls(Inventory inventory){
        int souls = 0;
        for (int i = 0; i < inventory.size(); i++){
            souls += getSouls(inventory.getStack(i));
        }
        return souls;
    }

    static int takeSouls(Inventory inventory, int souls){
        for (int i = 0; i < inventory.size(); i++){
            souls = takeSouls(inventory.getStack(i),souls);
            if (souls == 0) return 0;
        }
        return souls;

    }
    static int maxSouls(ItemStack stack){
        if(!(stack.getItem() instanceof SoulHolder holder)) return stack.isEmpty()? maxSouls(ModItems.CRYSTALIZED_SOUL.getDefaultStack()):0;
        return holder.maxSouls();
    }

    static void moveSouls(ItemStack giver, ItemStack receiver,Boolean maxOf1){
        int soulsMoved = Math.min(getSouls(giver),maxSouls(receiver) - getSouls(receiver));
        if(maxOf1) soulsMoved = Math.min(soulsMoved,1);
        takeSouls(giver, soulsMoved);
        addSouls(receiver, soulsMoved);
    }
}
