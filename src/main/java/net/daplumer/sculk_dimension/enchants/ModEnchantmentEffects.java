package net.daplumer.sculk_dimension.enchants;

import com.mojang.serialization.MapCodec;
import net.daplumer.sculk_dimension.TheSculkDimension;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

@SuppressWarnings({"SameParameterValue", "unused"})
public class ModEnchantmentEffects {
	public static final RegistryKey<Enchantment> REAPING = of("reaping");
	public static final RegistryKey<Enchantment> MUFFLING = of("muffling");
	public static MapCodec<ReapingEnchantmentEffect> REAPING_EFFECT = register("reaping_effect", ReapingEnchantmentEffect.CODEC);

	private static RegistryKey<Enchantment> of(String path) {
		Identifier id = Identifier.of(TheSculkDimension.MOD_ID, path);
		return RegistryKey.of(RegistryKeys.ENCHANTMENT, id);
	}

	private static <T extends EnchantmentEntityEffect> MapCodec<T> register(String id, MapCodec<T> codec) {
		return Registry.register(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, Identifier.of(TheSculkDimension.MOD_ID, id), codec);
	}

	public static void registerModEnchantmentEffects() {
		TheSculkDimension.LOGGER.info("Registering EnchantmentEffects for" + TheSculkDimension.MOD_ID);
	}
}