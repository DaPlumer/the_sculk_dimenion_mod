package net.daplumer.sculk_dimension.enchants;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.minecraft.component.EnchantmentEffectComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.enchantment.effect.EnchantmentEffectTarget;
import net.minecraft.entity.EntityType;
import net.minecraft.loot.condition.EntityPropertiesLootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.entity.EntityTypePredicate;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import java.util.concurrent.CompletableFuture;

public class EnchantmentGenerator extends FabricDynamicRegistryProvider {

	public EnchantmentGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
		super(output, registriesFuture);
		System.out.println("REGISTERING ENCHANTS");
	}

	@Override
	protected void configure(RegistryWrapper.WrapperLookup registries, Entries entries) {
		register(entries, ModEnchantmentEffects.REAPING,
				Enchantment.builder(
								Enchantment.definition(
										registries.getOrThrow(RegistryKeys.ITEM).getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
										2,
										3,
										Enchantment.leveledCost(15, 9),
										Enchantment.leveledCost(65, 9),
										4,
										AttributeModifierSlot.MAINHAND
								)
						)
						.addEffect(
								EnchantmentEffectComponentTypes.POST_ATTACK,
								EnchantmentEffectTarget.ATTACKER,
								EnchantmentEffectTarget.VICTIM,
								new ReapingEnchantmentEffect(EnchantmentLevelBasedValue.linear(1F)),
								EntityPropertiesLootCondition.builder(
										LootContext.EntityTarget.ATTACKER, EntityPredicate.Builder.create().type(EntityTypePredicate.create(registries.getOrThrow(RegistryKeys.ENTITY_TYPE), EntityType.PLAYER))
								)
						)
		);
	}

	private void register(Entries entries, RegistryKey<Enchantment> key, Enchantment.Builder builder, ResourceCondition... resourceConditions) {
		entries.add(key, builder.build(key.getValue()), resourceConditions);
	}

	@Override
	public String getName() {
		return "SculkDimensionEnchantmentGenerator";
	}
}