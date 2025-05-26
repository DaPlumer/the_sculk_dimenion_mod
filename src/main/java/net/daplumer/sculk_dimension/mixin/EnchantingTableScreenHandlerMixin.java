package net.daplumer.sculk_dimension.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.daplumer.sculk_dimension.item.ModItems;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.EnchantmentScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EnchantmentScreenHandler.class)
public abstract class EnchantingTableScreenHandlerMixin extends ScreenHandler {
    @Shadow @Final private Inventory inventory;

    protected EnchantingTableScreenHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId) {
        super(type, syncId);
    }
    @ModifyExpressionValue(method = "onContentChanged", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isEnchantable()Z"))
    boolean aVoid(boolean original){

        return original &! inventory.getStack(0).isOf(ModItems.RESINANT_WAX);
    }
}
