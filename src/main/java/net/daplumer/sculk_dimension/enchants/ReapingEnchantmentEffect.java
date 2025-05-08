package net.daplumer.sculk_dimension.enchants;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.daplumer.sculk_dimension.item.ModItems;
import net.daplumer.sculk_dimension.mixin.ExpInvoker;
import net.daplumer.sculk_dimension.util.statistics.SoulHolder;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

public record ReapingEnchantmentEffect(EnchantmentLevelBasedValue amount) implements EnchantmentEntityEffect {
    public static final MapCodec<ReapingEnchantmentEffect> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    EnchantmentLevelBasedValue.CODEC.fieldOf("amount").forGetter(ReapingEnchantmentEffect::amount)
            ).apply(instance, ReapingEnchantmentEffect::new)
    );
    @Override
    public void apply(ServerWorld world, int level, EnchantmentEffectContext context, Entity user, Vec3d pos) {
        if (!user.isAlive() && user instanceof LivingEntity victim) {
            if (context.owner() instanceof PlayerEntity player) {
                giveSouls(player, victim, level, world);
            } else {
                victim.dropStack(world, new ItemStack(ModItems.CRYSTALIZED_SOUL, getRandomNumber(level, ((ExpInvoker) victim).invoker(world), world)));
            }
        }

    }
    public static int getRandomNumber(int lvl, int exp,ServerWorld world){
        return 2+lvl*exp/4+world.random.nextBetweenExclusive(-1,2);
    }

    public static void giveSouls(PlayerEntity player, LivingEntity victim, int level, ServerWorld world){
        int excess = SoulHolder.giveSouls(player.getInventory(),getRandomNumber(level,((ExpInvoker) victim).invoker(world),world));
        ItemStack stack = ModItems.CRYSTALIZED_SOUL.getDefaultStack();
        stack.setCount(excess);
        player.giveOrDropStack(stack);
    }


    @Override
    public MapCodec<? extends EnchantmentEntityEffect> getCodec() {
        return CODEC;
    }
}
