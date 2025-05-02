package net.daplumer.sculk_dimension.mixin;

import net.daplumer.sculk_dimension.item.ModItems;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Util;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Mixin(ExperienceOrbEntity.class)
public class MendingMixin {


    @Shadow
    private int repairPlayerGears(ServerPlayerEntity player, int amount) {
        return 0;
    }

    @Inject(method = "repairPlayerGears",at = @At("HEAD"), cancellable = true)
    void aVoid(ServerPlayerEntity player, int amount, CallbackInfoReturnable<Integer> cir){
        List<EnchantmentEffectContext> list = new ArrayList<>();

        for (ItemStack itemStack : player.getInventory()) {
            if (itemStack.isOf(ModItems.MEMORY_GEM) && itemStack.isDamaged()) {
                list.add(new EnchantmentEffectContext(itemStack,null,player));
            }
        }

        Optional<EnchantmentEffectContext> optional = Util.getRandomOrEmpty(list, player.getRandom());
        if (optional.isPresent()) {
            ItemStack itemStack = (optional.get()).stack();
            int i = EnchantmentHelper.getRepairWithExperience(player.getServerWorld(), itemStack, amount);
            int j = Math.min(i, itemStack.getDamage());
            itemStack.setDamage(itemStack.getDamage() - j);
            if (j > 0) {
                int k = amount - j * amount / i;
                if (k > 0) {
                    cir.setReturnValue(repairPlayerGears(player,k));
                    cir.cancel();
                }
            }
        }
    }

}
