package net.daplumer.sculk_dimension.mixin;

import net.daplumer.sculk_dimension.block.ModBlockTags;
import net.daplumer.sculk_dimension.component.ModProperties;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(AxeItem.class)
public class AxeScrapeMixin extends Item {

    public AxeScrapeMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "tryStrip(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/block/BlockState;)Ljava/util/Optional;", at = @At(value = "RETURN"), cancellable = true)
    private void injected(World world, BlockPos pos, @Nullable PlayerEntity player, BlockState state, CallbackInfoReturnable<Optional<BlockState>> cir){
        if(cir.getReturnValue().isEmpty() && state.isIn(ModBlockTags.VIBRATOR)){
            if (state.get(ModProperties.WAXED)) {
                world.playSound(player, pos, SoundEvents.ITEM_HONEYCOMB_WAX_ON, SoundCategory.BLOCKS, 1.0F, 1.5F);
                cir.setReturnValue(Optional.ofNullable(state.with(ModProperties.WAXED, false)));
                world.syncWorldEvent(player, WorldEvents.WAX_REMOVED, pos, 0);
                if (state.isOf(Blocks.SCULK_SHRIEKER) && world instanceof ServerWorld && player instanceof ServerPlayerEntity){
                    world.getBlockEntity(pos, BlockEntityType.SCULK_SHRIEKER).ifPresent(blockEntity -> blockEntity.shriek((ServerWorld) world, (ServerPlayerEntity) player));
                }
            }
        }
    }
}
