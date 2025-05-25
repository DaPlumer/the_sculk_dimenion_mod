package net.daplumer.sculk_dimension.mixin;

import net.daplumer.sculk_dimension.item.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.AnvilScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AnvilScreenHandler.class)
public class AnvilMixin {
    @Redirect(method = "updateResult",at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z",ordinal = 1))
    boolean aVoid(ItemStack instance, Item item){
        return instance.isOf(Items.ENCHANTED_BOOK) || instance.isOf(ModItems.RESINANT_WAX);
    }
}
