package net.daplumer.sculk_dimension.mixin;

import com.mojang.authlib.GameProfile;
import net.daplumer.sculk_dimension.item.ModItems;
import net.daplumer.sculk_dimension.util.statistics.Insanity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity {
    public ServerPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile);
    }

    @Inject(method = "copyFrom",at = @At(value = "INVOKE", target = "Lnet/minecraft/world/GameRules;getBoolean(Lnet/minecraft/world/GameRules$Key;)Z"))
    private void injected(ServerPlayerEntity oldPlayer, boolean alive, CallbackInfo ci){
        ServerPlayerEntity This =(ServerPlayerEntity)(Object) this;
        if(!(This.getServerWorld().getGameRules().getBoolean(GameRules.KEEP_INVENTORY) || oldPlayer.isSpectator()) && hasMedallion(oldPlayer)){
            This.getInventory().clone(oldPlayer.getInventory());
        }
    }

    @Unique
    private boolean hasMedallion(@NotNull PlayerEntity player){
        PlayerInventory inventory = player.getInventory();
        DefaultedList<ItemStack> stacks = inventory.getMainStacks();
        for (int i = 0; i < stacks.size(); i++) {
            if (stacks.get(i).isOf(ModItems.ECHO_MEDALLION)) {
                inventory.removeStack(i);
                Insanity.add(7,this);
                return true;
            }
        }
        if(player.getOffHandStack().isOf(ModItems.ECHO_MEDALLION)){
            player.setStackInHand(Hand.OFF_HAND, ItemStack.EMPTY);
            return true;
        }
        return false;
    }
}
