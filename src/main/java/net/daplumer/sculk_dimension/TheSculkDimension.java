package net.daplumer.sculk_dimension;

import net.daplumer.data_modification_utils.mod_registries.Registerer;
import net.daplumer.sculk_dimension.block.BlockSets;
import net.daplumer.sculk_dimension.block.ModBlocks;
import net.daplumer.sculk_dimension.block.ModScreenHandlerTypes;
import net.daplumer.sculk_dimension.component.ModDataComponentTypes;
import net.daplumer.sculk_dimension.enchants.ModEnchantmentEffects;
import net.daplumer.sculk_dimension.entity.ModEntityTypes;
import net.daplumer.sculk_dimension.eventhandlers.ModEventHandlers;
import net.daplumer.sculk_dimension.item.*;
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


		ModEntityTypes.Initialize();

		ModItems.registerModItems();
		ModItemGroups.registerItemGroups();
		BlockSets.initialize();
		ModBlocks.registerModBlocks();
		ModScreenHandlerTypes.initialize();
		ModEnchantmentEffects.registerModEnchantmentEffects();
		ModToolMaterials.registerModToolMaterials();
		ModTrimMaterials.init();
	}
	/*
	TODO:
		add reaping enchant into librarian trades and enchantment loot tables
		debug enchantment combinations for reaping enchant
	 */
}