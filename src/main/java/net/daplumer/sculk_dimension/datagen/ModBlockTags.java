package net.daplumer.sculk_dimension.datagen;

import net.daplumer.sculk_dimension.TheSculkDimension;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

import static net.daplumer.sculk_dimension.block.ModBlocks.*;

public class ModBlockTags extends FabricTagProvider.BlockTagProvider {
    public ModBlockTags(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }
    public static final TagKey<Block> VIBRATOR = of("vibrators");
    public static final TagKey<Block> MEMORY_GEM_ORES = of("memory_gem_ores");
    public static TagKey<Block> of(String id){
        return TagKey.of(RegistryKeys.BLOCK,Identifier.of(TheSculkDimension.MOD_ID,id));
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(MEMORY_GEM_ORES).add(MEMORY_GEM_ORE,MEMORY_GEM_DEEPSLATE_ORE);
        getOrCreateTagBuilder(ConventionalBlockTags.ORES_IN_GROUND_DEEPSLATE).add(MEMORY_GEM_DEEPSLATE_ORE);
        getOrCreateTagBuilder(ConventionalBlockTags.ORES_IN_GROUND_STONE).add(MEMORY_GEM_ORE);
        getOrCreateTagBuilder(ConventionalBlockTags.ORES).addTag(MEMORY_GEM_ORES);
        getOrCreateTagBuilder(ConventionalBlockTags.FLOWERS).add(ECHOING_BLOOM,ECHOING_BLOOM_TIP);
        getOrCreateTagBuilder(ConventionalBlockTags.ORE_RATES_SINGULAR).addTag(MEMORY_GEM_ORES);
        getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL).add(ENCHANTMENT_DUPLICATOR,MEMORY_GEM_BLOCK).addTag(MEMORY_GEM_ORES);
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(ENCHANTMENT_DUPLICATOR,MEMORY_GEM_BLOCK).addTag(MEMORY_GEM_ORES);
        infected.registerBlockTags(this);
    }
}
