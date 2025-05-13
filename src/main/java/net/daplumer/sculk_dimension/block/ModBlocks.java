package net.daplumer.sculk_dimension.block;

import net.daplumer.mod_registerer.mod_registries.ModDataRegisterer;
import net.daplumer.sculk_dimension.TheSculkDimension;
import net.daplumer.sculk_dimension.block.custom.*;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import static net.daplumer.mod_registerer.mod_registries.Registerer.*;
import static net.daplumer.sculk_dimension.TheSculkDimension.*;

public class ModBlocks {
    public static final ModDataRegisterer<Block, AbstractBlock.Settings> BLOCKS = REGISTERER.BLOCKS;
    public static final ModDataRegisterer<Item, Item.Settings> ITEMS = REGISTERER.ITEMS;
    public static final Block SCULK_CAPTURE = BLOCKS.register(
            "sculk_capture",
                    AbstractBlock.Settings
                            .create()
                            .mapColor(MapColor.BLACK)
                            .noCollision()
                            .sounds(BlockSoundGroup.SCULK)
                            .strength(0.2F)
                            .pistonBehavior(PistonBehavior.DESTROY),
            SculkCaptureBlock::new
    );
    public static final Block SCULK_NEIGHBOR = BLOCKS.register(
            "sculk_neighbor",
                    AbstractBlock.Settings
                            .create()
                            .nonOpaque()
                            .mapColor(MapColor.BLACK)
                            .noCollision()
                            .sounds(BlockSoundGroup.SCULK)
                            .strength(0.2F)
                            .pistonBehavior(PistonBehavior.DESTROY),
            SculkCaptureNeighbor::new
    );
    public static final Block ECHOING_BLOOM = BLOCKS.register(
            "echoing_bloom",
                    AbstractBlock.Settings
                            .create()
                            .pistonBehavior(PistonBehavior.DESTROY)
                            .breakInstantly()
                            .nonOpaque()
                            .luminance(state -> 6)
                            .sounds(BlockSoundGroup.GRASS)
                            .noCollision()
                            .mapColor(MapColor.TERRACOTTA_PURPLE),
            EchoingBloom::new
    );
    public static final Block ECHOING_BLOOM_TIP = BLOCKS.register("echoing_bloom_tip",
            AbstractBlock.Settings.create()
                    .pistonBehavior(PistonBehavior.DESTROY)
                    .nonOpaque()
                    .breakInstantly()
                    .sounds(BlockSoundGroup.GRASS)
                    .noCollision()
                    .ticksRandomly()
                    .mapColor(MapColor.TERRACOTTA_PURPLE),
            EchoingBloomTip::new
    );
    public static final Block MEMORY_GEM_ORE = BLOCKS.register("memory_gem_ore",
            AbstractBlock.Settings.copy(Blocks.DIAMOND_ORE)
                    .mapColor(MapColor.STONE_GRAY)
                    .strength(3.0F,3.0F)
                    .instrument(NoteBlockInstrument.BASS)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)
    );
    public static final Block MEMORY_GEM_DEEPSLATE_ORE = BLOCKS.register("memory_gem_deepslate_ore",
            AbstractBlock.Settings.copy(Blocks.DEEPSLATE_DIAMOND_ORE)
                    .mapColor(MapColor.DEEPSLATE_GRAY)
                    .strength(4.5F, 3.0F)
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .requiresTool()
                    .sounds(BlockSoundGroup.DEEPSLATE)
            , EXP_DROPPER(UniformIntProvider.create(3,7))
    );
    public static final Block ENCHANTMENT_DUPLICATOR = BLOCKS.register("enchantment_duplicator",
            AbstractBlock.Settings.create(),
            EnchantmentDuplicator::new
    );

    public static final BlockItem ECHOING_BLOOM_ITEM = (BlockItem) ITEMS.register("echoing_bloom",new Item.Settings(),BLOCK_ITEM(ECHOING_BLOOM_TIP));
    public static final BlockItem SCULK_CAPTURE_ITEM = (BlockItem) ITEMS.register("sculk_capture",new Item.Settings(),BLOCK_ITEM(SCULK_CAPTURE));
    public static final BlockItem MEMORY_GEM_ORE_ITEM = (BlockItem) ITEMS.register("memory_gem_ore",new Item.Settings(),BLOCK_ITEM(MEMORY_GEM_ORE));
    public static final BlockItem MEMORY_GEM_DEEPSLATE_ORE_ITEM = (BlockItem) ITEMS.register("memory_gem_deepslate_ore",new Item.Settings(),BLOCK_ITEM(MEMORY_GEM_DEEPSLATE_ORE));
    public static final BlockItem ENCHANTMENT_DUPLICATOR_ITEM = (BlockItem) ITEMS.register("enchantment_duplicator",new Item.Settings(),BLOCK_ITEM(ENCHANTMENT_DUPLICATOR));
    public static void registerModBlocks(){
        TheSculkDimension.LOGGER.info("Registering Mod Blocks for " + MOD_ID);
        CompostingChanceRegistry.INSTANCE.add(ModBlocks.ECHOING_BLOOM_ITEM, 0.5F);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(entries -> {
            entries.addAfter(Blocks.SCULK,SCULK_CAPTURE);
            entries.addAfter(Blocks.DEEPSLATE_DIAMOND_ORE,MEMORY_GEM_ORE);
            entries.addAfter(MEMORY_GEM_ORE,MEMORY_GEM_DEEPSLATE_ORE);
        });
    }
}
