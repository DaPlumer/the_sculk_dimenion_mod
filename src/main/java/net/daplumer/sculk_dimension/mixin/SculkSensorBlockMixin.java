package net.daplumer.sculk_dimension.mixin;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.daplumer.sculk_dimension.component.ModProperties;
import net.minecraft.block.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.block.SculkSensorBlock;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SculkSensorBlock.class)
public abstract class SculkSensorBlockMixin extends AbstractBlock implements Waterloggable{

    public SculkSensorBlockMixin(Settings settings) {
        super(settings);
    }
    @Inject(method = "getPlacementState(Lnet/minecraft/item/ItemPlacementContext;)Lnet/minecraft/block/BlockState;",at = @At("RETURN"), cancellable = true)
    private void injected0(ItemPlacementContext ctx, CallbackInfoReturnable<BlockState> cir){
        cir.setReturnValue(cir.getReturnValue().with(ModProperties.WAXED,false));
    }


    @Inject(method = "appendProperties(Lnet/minecraft/state/StateManager$Builder;)V", at =@At(value = "TAIL"))
    private void injected1(CallbackInfo ci, @Local(argsOnly = true) LocalRef<StateManager.Builder<Block, BlockState>> builder){
        builder.set(builder.get().add(ModProperties.WAXED));
    }
}