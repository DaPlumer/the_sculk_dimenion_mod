package net.daplumer.sculk_dimension.mixin;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.daplumer.sculk_dimension.datagen.ModEnchantmentTagBuilder;
import net.daplumer.sculk_dimension.datagen.ModItemTags;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Entity.class)
public abstract class MossyMuffleMovementMixin {
    @ModifyReturnValue(method = "bypassesLandingEffects", at = @At(value = "RETURN"))
    private boolean injected(boolean original){
        if ((Object)this instanceof LivingEntity livingEntity){
            ItemStack boots = livingEntity.getEquippedStack(EquipmentSlot.FEET);
            original |= boots.isIn(ModItemTags.MUFFLERS) ||
            EnchantmentHelper.hasAnyEnchantmentsIn(boots, ModEnchantmentTagBuilder.MUFFLING_ENCHANTS);
        }
        return original;
    }
    @ModifyReturnValue(method = "bypassesSteppingEffects", at = @At(value = "RETURN"))
    private boolean injected2(boolean original){
        if ((Object)this instanceof LivingEntity livingEntity){
            ItemStack boots = livingEntity.getEquippedStack(EquipmentSlot.FEET);
            original |= boots.isIn(ModItemTags.MUFFLERS) ||
                    EnchantmentHelper.hasAnyEnchantmentsIn(boots, ModEnchantmentTagBuilder.MUFFLING_ENCHANTS);
        }
        return original;
    }
}
