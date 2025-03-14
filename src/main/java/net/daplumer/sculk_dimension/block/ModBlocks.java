package net.daplumer.sculk_dimension.block;

import net.daplumer.sculk_dimension.TheSculkDimension;
import net.daplumer.sculk_dimension.block.custom.EchoingBloom;
import net.daplumer.sculk_dimension.block.custom.SculkCaptureBlock;
import net.daplumer.sculk_dimension.block.custom.SculkCaptureNeighbor;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;


public class ModBlocks {
    public static final Block PINK_GARNET_BLOCK = registerBlock("pink_garnet_block", new Block(AbstractBlock.Settings.create()
            .strength(4f)
            .requiresTool()
            .sounds(BlockSoundGroup.AMETHYST_BLOCK
            )
        ),true
    );
    public static final Block RAW_PINK_GARNET_BLOCK = registerBlock("raw_pink_garnet_block",
        new Block(
            AbstractBlock.Settings.create()
                .strength(3f)
                .requiresTool()
                .sounds(BlockSoundGroup.STONE
            )
        ),true
    );
    public static final Block PINK_GARNET_ORE = registerBlock("pink_garnet_ore",
            new ExperienceDroppingBlock(
                    UniformIntProvider.create(2,3),
                    AbstractBlock.Settings.create()
                            .strength(3f)
                            .requiresTool()
                            .sounds(BlockSoundGroup.STONE)
            ),true
    );
    public static final Block DEEPSLATE_PINK_GARNET_ORE = registerBlock(
            "deepslate_pink_garnet_ore",
            new ExperienceDroppingBlock(
                    UniformIntProvider.create(3,6),
                    AbstractBlock.Settings
                            .create()
                            .strength(4f)
                            .requiresTool()
                            .sounds(BlockSoundGroup.DEEPSLATE)
            ), true
    );
    public static final Block SCULK_CAPTURE = registerBlock(
            "sculk_capture",
            new SculkCaptureBlock(
                    AbstractBlock.Settings
                            .create()
                            .mapColor(MapColor.BLACK)
                            .noCollision()
                            .sounds(BlockSoundGroup.SCULK)
                            .strength(0.2F)
                            .pistonBehavior(PistonBehavior.DESTROY)
            ), true
    );
    public static final Block SCULK_NEIGHBOR = registerBlock(
            "sculk_neighbor",
            new SculkCaptureNeighbor(
                    AbstractBlock.Settings
                            .create()
                            .nonOpaque()
                            .mapColor(MapColor.BLACK)
                            .noCollision()
                            .sounds(BlockSoundGroup.SCULK)
                            .strength(0.2F)
                            .pistonBehavior(PistonBehavior.DESTROY)
            ), false
    );
    public static final Block ECHOING_BLOOM = registerBlock(
            "echoing_bloom",
            new EchoingBloom(
                    AbstractBlock.Settings
                            .create()
                            .pistonBehavior(PistonBehavior.DESTROY)
                            .breakInstantly()
                            .nonOpaque()
                            .breakInstantly()
                            .luminance(state -> 6)
                            .sounds(BlockSoundGroup.GRASS)
                            .noCollision()
            )
    );
    private static Block registerBlock (String name, Block block){
       return registerBlock(name, block, true);
    }
    private static Block registerBlock (String name, Block block,boolean includeItem ){
        if (includeItem){
        registerBlockItem(name, block);
        }
        return Registry.register(Registries.BLOCK,Identifier.of(TheSculkDimension.MOD_ID, name), block);
    }
    private static void registerBlockItem(String name, Block block){
        Registry.register(Registries.ITEM, Identifier.of(TheSculkDimension.MOD_ID,name), new BlockItem(block, new Item.Settings()));
    }
    public static void registerModBlocks(){
        TheSculkDimension.LOGGER.info("Registering Mod Blocks for " + TheSculkDimension.MOD_ID);
        CompostingChanceRegistry.INSTANCE.add(ModBlocks.ECHOING_BLOOM, 0.5F);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(entries -> {
            entries.add(ModBlocks.PINK_GARNET_BLOCK);
            entries.add(ModBlocks.RAW_PINK_GARNET_BLOCK);
            entries.add(ModBlocks.PINK_GARNET_ORE);
            entries.add(ModBlocks.DEEPSLATE_PINK_GARNET_ORE );
            entries.addAfter(Blocks.SCULK,SCULK_CAPTURE);
        });
    }
}
