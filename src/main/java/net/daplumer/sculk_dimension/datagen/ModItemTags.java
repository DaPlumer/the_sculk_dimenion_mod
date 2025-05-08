package net.daplumer.sculk_dimension.datagen;

import net.daplumer.sculk_dimension.TheSculkDimension;
import net.daplumer.sculk_dimension.block.ModBlocks;
import net.daplumer.sculk_dimension.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import java.util.concurrent.CompletableFuture;

public class ModItemTags extends FabricTagProvider.ItemTagProvider {
    public ModItemTags(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }
    public static final TagKey<Item> MUFFLERS = of("mufflers");
    public static final TagKey<Item> MEMORY_GEM_ORES = of("memory_gem_ores");
    public static final TagKey<Item> MOSS = of("moss");
    public static TagKey<Item> of(String id){
        return TagKey.of(RegistryKeys.ITEM, Identifier.of(TheSculkDimension.MOD_ID,id));
    }
    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(MOSS).add(Items.MOSS_BLOCK,Items.MOSS_CARPET,Items.PALE_MOSS_BLOCK,Items.PALE_MOSS_CARPET);
        getOrCreateTagBuilder(MEMORY_GEM_ORES).add(ModBlocks.MEMORY_GEM_ORE_ITEM,ModBlocks.MEMORY_GEM_DEEPSLATE_ORE_ITEM);
        getOrCreateTagBuilder(ItemTags.SWORDS).add(ModItems.SCYTHE);
        getOrCreateTagBuilder(ConventionalItemTags.GEMS).add(ModItems.MEMORY_GEM);
        getOrCreateTagBuilder(ConventionalItemTags.ORES_IN_GROUND_DEEPSLATE).add(ModBlocks.MEMORY_GEM_DEEPSLATE_ORE_ITEM);
        getOrCreateTagBuilder(ConventionalItemTags.ORES_IN_GROUND_STONE).add(ModBlocks.MEMORY_GEM_ORE_ITEM);
        getOrCreateTagBuilder(ConventionalItemTags.ORES).addTag(MEMORY_GEM_ORES);
        getOrCreateTagBuilder(ConventionalItemTags.FLOWERS).add(ModBlocks.ECHOING_BLOOM_ITEM);
        getOrCreateTagBuilder(MUFFLERS).add(ModItems.MOSSY_BOOTS);
    }
}
