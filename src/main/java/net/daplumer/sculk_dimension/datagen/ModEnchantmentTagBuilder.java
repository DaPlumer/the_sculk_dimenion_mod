package net.daplumer.sculk_dimension.datagen;

import net.daplumer.sculk_dimension.TheSculkDimension;
import net.daplumer.sculk_dimension.enchants.ModEnchantmentEffects;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalEnchantmentTags;
import net.minecraft.data.DataOutput;
import net.minecraft.data.tag.EnchantmentTagProvider;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.EnchantmentTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings("SameParameterValue")
public class ModEnchantmentTagBuilder extends EnchantmentTagProvider {
    public ModEnchantmentTagBuilder(DataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }
    public static final TagKey<Enchantment> SOUL_DROP_ENCHANTS = of("exclusive_set/soul_drops");
    public static final TagKey<Enchantment> MUFFLING_ENCHANTS = of("exclusive_set/muffling");
    private static TagKey<Enchantment> of(String id) {
        return TagKey.of(RegistryKeys.ENCHANTMENT, Identifier.of(TheSculkDimension.MOD_ID,id));
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries) {
        this.getOrCreateTagBuilder(EnchantmentTags.DAMAGE_EXCLUSIVE_SET).addOptional(ModEnchantmentEffects.REAPING.getValue());
        this.getOrCreateTagBuilder(SOUL_DROP_ENCHANTS).addOptional(ModEnchantmentEffects.REAPING.getValue());
        this.getOrCreateTagBuilder(ConventionalEnchantmentTags.INCREASE_ENTITY_DROPS).addOptional(ModEnchantmentEffects.REAPING.getValue());
        this.getOrCreateTagBuilder(MUFFLING_ENCHANTS).addOptional(ModEnchantmentEffects.MUFFLING.getValue());
    }
}
