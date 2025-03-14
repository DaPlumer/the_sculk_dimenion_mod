package net.daplumer.sculk_dimension;

import net.daplumer.sculk_dimension.block.ModBlocks;
import net.daplumer.sculk_dimension.component.ModDataComponentTypes;
import net.daplumer.sculk_dimension.eventhandlers.ModEventHandlers;
import net.daplumer.sculk_dimension.item.ModItemGroups;
import net.daplumer.sculk_dimension.item.ModItems;
import net.daplumer.sculk_dimension.util.ModModelPredicates;
import net.daplumer.sculk_dimension.util.statistics.ModStatistics;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TheSculkDimension implements ModInitializer {
	public static final String MOD_ID = "sculk_dimension";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModEventHandlers.registerEvents();
		ModDataComponentTypes.registerDataComponentTypes();
		ModModelPredicates.registerModelPredicates();
		ModStatistics.registerModStatistics();


		ModItemGroups.registerItemGroups();

		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
	}
}