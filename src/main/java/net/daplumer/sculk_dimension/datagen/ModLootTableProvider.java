package net.daplumer.sculk_dimension.datagen;

import net.daplumer.sculk_dimension.block.ModBlocks;
import net.daplumer.sculk_dimension.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModLootTableProvider extends FabricBlockLootTableProvider {
    public ModLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }
    @Override
    public void generate() {
        addDrop(ModBlocks.MEMORY_GEM_ORE, oreDrops(ModBlocks.MEMORY_GEM_ORE, ModItems.MEMORY_GEM));
        addDrop(ModBlocks.MEMORY_GEM_DEEPSLATE_ORE, oreDrops(ModBlocks.MEMORY_GEM_DEEPSLATE_ORE, ModItems.MEMORY_GEM));
        addDrop(ModBlocks.ENCHANTMENT_DUPLICATOR);
        addVinePlantDrop(ModBlocks.ECHOING_BLOOM,ModBlocks.ECHOING_BLOOM_TIP);
    }
}
