package net.daplumer.sculk_dimension;

import net.daplumer.mod_registerer.mod_registries.Registerer;
import net.daplumer.sculk_dimension.block.ModBlocks;
import net.daplumer.sculk_dimension.block.custom.ModScreenHandlerTypes;
import net.daplumer.sculk_dimension.component.ModDataComponentTypes;
import net.daplumer.sculk_dimension.enchants.ModEnchantmentEffects;
import net.daplumer.sculk_dimension.eventhandlers.ModEventHandlers;
import net.daplumer.sculk_dimension.item.ModItemGroups;
import net.daplumer.sculk_dimension.item.ModItems;
import net.daplumer.sculk_dimension.item.custom.ModArmorMaterials;
import net.daplumer.sculk_dimension.item.custom.ModToolMaterials;
import net.daplumer.sculk_dimension.util.statistics.ModStatistics;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TheSculkDimension implements ModInitializer {
	public static final String MOD_ID = "sculk_dimension";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final Registerer REGISTERER = Registerer.of(MOD_ID);

	@Override
	public void onInitialize() {
		ModArmorMaterials.registerModArmorMaterials();
		ModEventHandlers.registerEvents();
		ModDataComponentTypes.registerDataComponentTypes();
		ModStatistics.registerModStatistics();


		ModItemGroups.registerItemGroups();

		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModScreenHandlerTypes.initialize();
		ModEnchantmentEffects.registerModEnchantmentEffects();
		ModToolMaterials.registerModToolMaterials();
	}
	/*
	TODO:
		add reaping enchant into librarian trades and enchantment loot tables
		debug enchantment combinations for reaping enchant
	 */
}