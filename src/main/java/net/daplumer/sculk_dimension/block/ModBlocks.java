package net.daplumer.sculk_dimension.block;

import net.daplumer.data_modification_utils.block_set_generation.Shift;
import net.daplumer.data_modification_utils.mod_registries.GeneralDataRegisterer;
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
import net.minecraft.item.ItemGroups;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.intprovider.UniformIntProvider;

import static net.daplumer.data_modification_utils.mod_registries.Registerer.EXP_DROPPER;
import static net.daplumer.data_modification_utils.mod_registries.Registerer.registerBlockItem;
import static net.daplumer.sculk_dimension.TheSculkDimension.MOD_ID;
import static net.daplumer.sculk_dimension.TheSculkDimension.REGISTERER;

@SuppressWarnings("unused")
public class ModBlocks implements InfectedBlocks {
    public static final GeneralDataRegisterer<Block, AbstractBlock.Settings> BLOCKS = REGISTERER.BLOCKS;

    public static final  Block MEMORY_GEM_BLOCK = BLOCKS.register("memory_gem_block",
            AbstractBlock.Settings.create()
                    .requiresTool()
                    .strength(5.0F, 6.0F)
                    .mapColor(MapColor.MAGENTA)
                    .sounds(BlockSoundGroup.METAL)
                    .instrument(NoteBlockInstrument.BIT)
                    .luminance((state) -> 7)
    );
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
            AbstractBlock.Settings.create()
                    .strength(5.0F, 1200.0F)
                    .mapColor(MapColor.CYAN)
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .luminance((state) -> 7)
                    .requiresTool(),
            EnchantmentDuplicator::new
    );
    public static final BlockItem ECHOING_BLOOM_ITEM = registerBlockItem(ECHOING_BLOOM_TIP);
    public static final BlockItem SCULK_CAPTURE_ITEM = registerBlockItem(SCULK_CAPTURE);
    public static final BlockItem MEMORY_GEM_ORE_ITEM = registerBlockItem(MEMORY_GEM_ORE);
    public static final BlockItem MEMORY_GEM_DEEPSLATE_ORE_ITEM = registerBlockItem(MEMORY_GEM_DEEPSLATE_ORE);
    public static final BlockItem ENCHANTMENT_DUPLICATOR_ITEM = registerBlockItem(ENCHANTMENT_DUPLICATOR);
    public static final BlockItem MEMORY_GEM_BLOCK_ITEM = registerBlockItem(MEMORY_GEM_BLOCK);
    public static void registerModBlocks(){
        TheSculkDimension.LOGGER.info("Registering Mod Blocks for " + MOD_ID);
        CompostingChanceRegistry.INSTANCE.add(ModBlocks.ECHOING_BLOOM_ITEM, 0.5F);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(entries -> {
            entries.addAfter(Blocks.SCULK,SCULK_CAPTURE);
            entries.addAfter(Blocks.DEEPSLATE_DIAMOND_ORE,MEMORY_GEM_ORE);
            entries.addAfter(MEMORY_GEM_ORE,MEMORY_GEM_DEEPSLATE_ORE);;
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(entries -> {
            InfectedBlocks.infected.addToItemGroups(entries, Shift.BEFORE,Blocks.CRIMSON_STEM);
        });
    }
}
