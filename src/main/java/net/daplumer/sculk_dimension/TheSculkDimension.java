package net.daplumer.sculk_dimension;

import net.daplumer.sculk_dimension.block.ModBlocks;
import net.daplumer.sculk_dimension.block.custom.ModScreenHandlerTypes;
import net.daplumer.sculk_dimension.component.ModDataComponentTypes;
import net.daplumer.sculk_dimension.enchants.ModEnchantmentEffects;
import net.daplumer.sculk_dimension.eventhandlers.ModEventHandlers;
import net.daplumer.sculk_dimension.item.ModItemGroups;
import net.daplumer.sculk_dimension.item.ModItems;
import net.daplumer.sculk_dimension.util.statistics.ModStatistics;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TheSculkDimension implements ModInitializer {
	public static final String MOD_ID = "sculk_dimension";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModEventHandlers.registerEvents();
		ModDataComponentTypes.registerDataComponentTypes();
		ModStatistics.registerModStatistics();


		ModItemGroups.registerItemGroups();

		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModScreenHandlerTypes.initialize();
		ModEnchantmentEffects.registerModEnchantmentEffects();

	}
	/*
	TODO:
		make craftable cythe that always has the reaping enchantment and larger attack range
		add reaping enchant into librarian trades and enchantment loot tab les
		make reaping enchant incompatible with looting and silk touch
		make reaping enchant only work on melee weapons
		debug enchantment combinations for reaping enchant
		debug
		set durability from loot table instead of from the rng provider for mem gem
	 */
}