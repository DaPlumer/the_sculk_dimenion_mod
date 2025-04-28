package net.daplumer.sculk_dimension.mixin;
import net.daplumer.sculk_dimension.item.ModItemTags;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Entity.class)
public class MossyMuffleMisstepMixin {
    @Redirect(method = "bypassesLandingEffects()Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;isSneaking()Z"))
    private boolean injected(Entity entity){
        if (entity instanceof LivingEntity) {
            return entity.isSneaking() || ((LivingEntity) entity).getEquippedStack(EquipmentSlot.FEET).isIn(ModItemTags.MUFFLERS);
        }
        return entity.isSneaking();
    }
}
