package net.daplumer.sculk_dimension.mixin;
import com.llamalad7.mixinextras.sugar.Local;
import net.daplumer.sculk_dimension.component.ModProperties;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.event.BlockPositionSource;
import net.minecraft.world.event.PositionSource;
import net.minecraft.world.event.Vibrations;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net.minecraft.block.entity.SculkShriekerBlockEntity$VibrationCallback")
public abstract class SculkShriekerBlockEntityMixin implements Vibrations.Callback{
    @Mutable
    @Final
    @Shadow
    private final PositionSource positionSource;
    protected SculkShriekerBlockEntityMixin(BlockPositionSource positionSource) {
        this.positionSource = positionSource;
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Inject(method = "accepts(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/registry/entry/RegistryEntry;Lnet/minecraft/world/event/GameEvent$Emitter;)Z", at=@At("RETURN"), cancellable = true)
    public void injected(CallbackInfoReturnable<Boolean> cir, @Local(argsOnly = true) ServerWorld world){
        cir.setReturnValue(cir.getReturnValue() &! world.getBlockState(BlockPos.ofFloored(this.positionSource.getPos(world).get())).get(ModProperties.WAXED));
    }

}