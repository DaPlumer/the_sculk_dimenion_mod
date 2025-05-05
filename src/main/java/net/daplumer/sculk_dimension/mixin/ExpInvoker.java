package net.daplumer.sculk_dimension.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(LivingEntity.class)
public interface ExpInvoker {
    @Invoker("getExperienceToDrop")
    int invoker(ServerWorld world);
}
