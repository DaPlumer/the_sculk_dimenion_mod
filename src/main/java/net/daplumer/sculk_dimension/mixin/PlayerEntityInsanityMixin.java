package net.daplumer.sculk_dimension.mixin;

import net.daplumer.sculk_dimension.TheSculkDimension;
import net.daplumer.sculk_dimension.util.statistics.Insanity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.throwables.MixinException;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityInsanityMixin extends LivingEntity implements Insanity {
    private PlayerEntityInsanityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType,world);
        throw new MixinException("fuck you");
    }
    @Unique
    private int insanity;

    @Override
    public int getInsanity() {
        return insanity;
    }

    @Override
    public void setInsanity(int insanity) {
        this.insanity = insanity;
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    void readInsanity(NbtCompound nbt, CallbackInfo ci){
        this.setInsanity(nbt.getInt(TheSculkDimension.MOD_ID + ":insanity",0));
    }
    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    void writeInsanity(NbtCompound nbt, CallbackInfo ci){
        nbt.putInt(TheSculkDimension.MOD_ID + ":insanity", getInsanity());
    }

}
