package net.daplumer.sculk_dimension.datagen;

import net.daplumer.sculk_dimension.TheSculkDimension;
import net.daplumer.sculk_dimension.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import java.util.concurrent.CompletableFuture;

public class ModBlockTags extends FabricTagProvider.BlockTagProvider {
    public ModBlockTags(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }
    public static final TagKey<Block> MEMORY_GEM_ORES = of("memory_gem_ores");
    public static TagKey<Block> of(String id){
        return TagKey.of(RegistryKeys.BLOCK,Identifier.of(TheSculkDimension.MOD_ID,id));
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(MEMORY_GEM_ORES).add(ModBlocks.MEMORY_GEM_ORE,ModBlocks.MEMORY_GEM_DEEPSLATE_ORE);
        getOrCreateTagBuilder(ConventionalBlockTags.ORES_IN_GROUND_DEEPSLATE).add(ModBlocks.MEMORY_GEM_DEEPSLATE_ORE);
        getOrCreateTagBuilder(ConventionalBlockTags.ORES_IN_GROUND_STONE).add(ModBlocks.MEMORY_GEM_ORE);
        getOrCreateTagBuilder(ConventionalBlockTags.ORES).addTag(MEMORY_GEM_ORES);
        getOrCreateTagBuilder(ConventionalBlockTags.FLOWERS).add(ModBlocks.ECHOING_BLOOM,ModBlocks.ECHOING_BLOOM_TIP);
        getOrCreateTagBuilder(ConventionalBlockTags.ORE_RATES_SINGULAR).addTag(MEMORY_GEM_ORES);
    }
}
