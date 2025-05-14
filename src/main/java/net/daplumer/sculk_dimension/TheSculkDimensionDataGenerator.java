package net.daplumer.sculk_dimension;

import net.daplumer.sculk_dimension.datagen.*;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class TheSculkDimensionDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(TheSculkDimensionModelProvider::new);
		pack.addProvider(EnchantmentGenerator::new);
		pack.addProvider(ModEnchantmentTagBuilder::new);
		pack.addProvider(ModItemTags::new);
		pack.addProvider(ModBlockTags::new);
		pack.addProvider(ModLootTableProvider::new);
		pack.addProvider(ModAdvancementProvider::new);
		pack.addProvider(ModRecipeProvider::new);
	}
}
