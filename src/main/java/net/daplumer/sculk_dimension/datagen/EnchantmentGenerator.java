package net.daplumer.sculk_dimension.datagen;

import net.daplumer.sculk_dimension.enchants.ModEnchantmentEffects;
import net.daplumer.sculk_dimension.enchants.ReapingEnchantmentEffect;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.fabricmc.fabric.api.item.v1.EnchantmentEvents;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalEnchantmentTags;
import net.minecraft.component.EnchantmentEffectComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.enchantment.effect.EnchantmentEffectTarget;
import net.minecraft.enchantment.effect.value.SetEnchantmentEffect;
import net.minecraft.entity.EntityType;
import net.minecraft.loot.condition.EntityPropertiesLootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.entity.EntityTypePredicate;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.ItemTags;
import java.util.concurrent.CompletableFuture;

public class EnchantmentGenerator extends FabricDynamicRegistryProvider {
	public EnchantmentGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected void configure(RegistryWrapper.WrapperLookup registries, Entries entries) {
		RegistryWrapper.Impl<Enchantment> enchantments = registries.getOrThrow(RegistryKeys.ENCHANTMENT);
        register(entries, ModEnchantmentEffects.REAPING,
				Enchantment.builder(
								Enchantment.definition(
										registries.getOrThrow(RegistryKeys.ITEM).getOrThrow(ItemTags.SHARP_WEAPON_ENCHANTABLE),
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
						).addEffect(
								EnchantmentEffectComponentTypes.MOB_EXPERIENCE,
                                new SetEnchantmentEffect(EnchantmentLevelBasedValue.constant(0F))
								)
						.exclusiveSet(enchantments.getOrThrow(ConventionalEnchantmentTags.INCREASE_ENTITY_DROPS))

		);
		RegistryEntryList.Named<Enchantment> increaseDrops = enchantments.getOrThrow(ConventionalEnchantmentTags.INCREASE_ENTITY_DROPS);
		EnchantmentEvents.MODIFY.register(((key, builder, source) ->  {
			if (increaseDrops.contains(registries.getEntryOrThrow(key))){
				builder.exclusiveSet(enchantments.getOrThrow(ModEnchantmentTagBuilder.SOUL_DROP_ENCHANTS));
			}
		}));
	}

		private void register(Entries entries, RegistryKey<Enchantment> key, Enchantment.Builder builder, ResourceCondition... resourceConditions) {
			entries.add(key, builder.build(key.getValue()), resourceConditions);
		}

	@Override
	public String getName() {
		return "SculkDimensionEnchantmentGenerator";
	}
}