package net.daplumer.sculk_dimension.mixin;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Entity.class)
public class MossyMuffleMisstepMixin {
    @SuppressWarnings("SpellCheckingInspection")
    @Redirect(method = "bypassesLandingEffects()Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;isSneaking()Z"))
    private boolean injected(Entity entity){
        if (entity instanceof LivingEntity) {
            return entity.isSneaking() || ((LivingEntity) entity).getEquippedStack(EquipmentSlot.FEET).getRegistryEntry().matchesId(Identifier.of("sculk_dimension:mossy_boots"));
        }
        return entity.isSneaking();
    }
}
