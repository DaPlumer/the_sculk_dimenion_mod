package net.daplumer.sculk_dimension.item.custom;

import net.daplumer.sculk_dimension.enchants.ModEnchantmentEffects;
import net.daplumer.sculk_dimension.enchants.ReapingEnchantmentEffect;
import net.daplumer.sculk_dimension.item.ModItems;
import net.daplumer.sculk_dimension.mixin.ExpInvoker;
import net.daplumer.sculk_dimension.util.statistics.Insanity;
import net.fabricmc.fabric.api.item.v1.EnchantingContext;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import java.util.Optional;
import static net.daplumer.sculk_dimension.enchants.ReapingEnchantmentEffect.getRandomNumber;

public class Scythe extends Item {
    public Scythe(Settings settings) {
        super(settings);
    }

    @Override
    public void onCraftByPlayer(ItemStack stack, PlayerEntity player) {
        Insanity.add(7,player);
    }

    @Override
    public void postProcessComponents(ItemStack stack) {
        super.postProcessComponents(stack);
    }

    @Override
    public void postDamageEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (target.isAlive()) return;
        if (!(target.getWorld() instanceof ServerWorld serverWorld)) return;
        if (attacker instanceof PlayerEntity player) {
            ReapingEnchantmentEffect.giveSouls(player, target, 1, serverWorld);
        } else {
            target.dropStack((ServerWorld) target.getWorld(), new ItemStack(ModItems.CRYSTALIZED_SOUL,
                    getRandomNumber(1, ((ExpInvoker) target).invoker(serverWorld), serverWorld)));
        }
    }

    @Override
    public boolean canBeEnchantedWith(ItemStack stack, RegistryEntry<Enchantment> enchantment, EnchantingContext context) {
        return super.canBeEnchantedWith(stack, enchantment, context) &! enchantment.getKey().equals(Optional.of(ModEnchantmentEffects.REAPING));
    }
}
