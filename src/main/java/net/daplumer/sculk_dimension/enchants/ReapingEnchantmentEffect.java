package net.daplumer.sculk_dimension.enchants;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.daplumer.sculk_dimension.item.ModItems;
import net.daplumer.sculk_dimension.mixin.ExpInvoker;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
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
        if(!user.isAlive() && user instanceof LivingEntity entity && context.owner() != null) {
            ItemStack stack = new ItemStack(ModItems.CRYSTALIZED_SOUL);
            stack.setCount(getRandomNumber(level,((ExpInvoker) entity).invoker(world),world));
            user.dropStack(world,stack);
        }

    }
    private static int getRandomNumber(int lvl, int exp,ServerWorld world){
        return 1+(lvl*exp/4)+world.random.nextBetweenExclusive(-1,lvl+1);
    }


    @Override
    public MapCodec<? extends EnchantmentEntityEffect> getCodec() {
        return CODEC;
    }
}
