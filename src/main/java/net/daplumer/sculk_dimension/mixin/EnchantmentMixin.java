package net.daplumer.sculk_dimension.mixin;

import net.daplumer.sculk_dimension.item.ModItems;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Enchantment.class)
public abstract class EnchantmentMixin {
    @Inject(method = "isAcceptableItem",at = @At("HEAD"), cancellable = true)
    void aVoid(ItemStack stack, CallbackInfoReturnable<Boolean> cir){
        if(stack.isOf(ModItems.RESINANT_WAX)){
            cir.setReturnValue(true);
            cir.cancel();
        }
    }
}
