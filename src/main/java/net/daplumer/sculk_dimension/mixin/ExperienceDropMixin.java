package net.daplumer.sculk_dimension.mixin;

import net.daplumer.sculk_dimension.item.ModItems;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnchantmentHelper.class)
public class ExperienceDropMixin {
    @Inject(method = "getMobExperience",at = @At("RETURN"),cancellable = true)
    private static void invoke(ServerWorld world, Entity attacker, Entity mob, int baseMobExperience, CallbackInfoReturnable<Integer> cir){
        if(attacker instanceof LivingEntity entity){
            if(entity.getEquippedStack(EquipmentSlot.MAINHAND).isOf(ModItems.SCYTHE)) cir.setReturnValue(0);
        }

    }
}
