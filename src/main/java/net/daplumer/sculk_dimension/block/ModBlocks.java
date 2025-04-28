package net.daplumer.sculk_dimension.block;

import net.daplumer.sculk_dimension.TheSculkDimension;
import net.daplumer.sculk_dimension.block.custom.EchoingBloom;
import net.daplumer.sculk_dimension.block.custom.EchoingBloomTip;
import net.daplumer.sculk_dimension.block.custom.SculkCaptureBlock;
import net.daplumer.sculk_dimension.block.custom.SculkCaptureNeighbor;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.sound.BlockSoundGroup;

import static net.daplumer.sculk_dimension.TheSculkDimensionRegistries.*;


public class ModBlocks {
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
    public static final BlockItem ECHOING_BLOOM_ITEM = (BlockItem) ITEMS.register("echoing_bloom",new Item.Settings(),BLOCK_ITEM(ECHOING_BLOOM_TIP));
    public static final BlockItem SCULK_CAPTURE_ITEM = (BlockItem) ITEMS.register("sculk_capture",new Item.Settings(),BLOCK_ITEM(SCULK_CAPTURE));

    public static void registerModBlocks(){
        TheSculkDimension.LOGGER.info("Registering Mod Blocks for " + TheSculkDimension.MOD_ID);
        CompostingChanceRegistry.INSTANCE.add(ModBlocks.ECHOING_BLOOM_ITEM, 0.5F);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(entries -> entries.addAfter(Blocks.SCULK,SCULK_CAPTURE_ITEM));
    }
}
