package net.daplumer.sculk_dimension.mixin;

import net.daplumer.sculk_dimension.item.ModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerEntity.class)
public abstract class KeepInventoryMedalMixin extends LivingEntity {
    protected KeepInventoryMedalMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }
    @Redirect(method = "dropInventory", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerInventory;dropAll()V"))
    private void injected(PlayerInventory instance){
        PlayerEntity This = (PlayerEntity)(Object) this;
        DefaultedList<ItemStack> stacks = instance.getMainStacks();
        for (ItemStack stack : stacks) {
            if (stack.isOf(ModItems.ECHO_MEDALLION)) {
                return;
            }
        }

        if(This.getOffHandStack().isOf(ModItems.ECHO_MEDALLION)){
            return;
        }
            instance.dropAll();
    }
}
