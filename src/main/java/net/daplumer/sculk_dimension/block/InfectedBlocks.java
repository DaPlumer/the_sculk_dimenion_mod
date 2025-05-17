package net.daplumer.sculk_dimension.block;

import net.daplumer.data_modification_utils.block_set_generation.wood.WoodBlockGroup;
import net.daplumer.data_modification_utils.block_set_generation.wood.WoodTypeSounds;
import net.daplumer.data_modification_utils.mod_registries.GeneralDataRegisterer;
import net.daplumer.data_modification_utils.mod_registries.registering_functions.ItemsKt;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.item.Item;

import static net.daplumer.data_modification_utils.mod_registries.registering_functions.BlocksKt.*;
import static net.daplumer.sculk_dimension.TheSculkDimension.REGISTERER;
import static net.daplumer.sculk_dimension.block.ModBlocks.BLOCKS;

public interface InfectedBlocks {
    WoodBlockGroup infected = WoodBlockGroup.of(BLOCKS,"infected",true,MapColor.LIGHT_BLUE,MapColor.TERRACOTTA_CYAN,MapColor.TERRACOTTA_GRAY,WoodTypeSounds.NETHER);

    Block INFECTED_SIGN = BLOCKS.register("infected_sign", SIGN(BlockSets.INFECTED_WOOD),
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.LIGHT_BLUE)
                    .solid()
                    .instrument(NoteBlockInstrument.BASS)
                    .noCollision()
                    .strength(1.0F)
                    .burnable()
    );
    Block INFECTED_WALL_SIGN = BLOCKS.register("infected_wall_sign", WALL_SIGN(BlockSets.INFECTED_WOOD),
            copyLootTable(INFECTED_SIGN, true)
                    .mapColor(MapColor.LIGHT_BLUE)
                    .solid()
                    .instrument(NoteBlockInstrument.BASS)
                    .noCollision()
                    .strength(1.0F)
                    .burnable()
    );
    Block INFECTED_HANGING_SIGN = BLOCKS.register("infected_hanging_sign", HANGING_SIGN(BlockSets.INFECTED_WOOD),
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.LIGHT_BLUE)
                    .solid()
                    .instrument(NoteBlockInstrument.BASS)
                    .noCollision()
                    .strength(1.0F)
                    .burnable()
    );
    Block INFECTED_WALL_HANGING_SIGN = BLOCKS.register("infected_wall_hanging_sign", WALL_HANGING_SIGN(BlockSets.INFECTED_WOOD),
            copyLootTable(INFECTED_WALL_SIGN, true)
                    .mapColor(MapColor.LIGHT_BLUE)
                    .solid()
                    .instrument(NoteBlockInstrument.BASS)
                    .noCollision()
                    .strength(1.0F)
                    .burnable()
    );
    GeneralDataRegisterer<Item, Item.Settings> ITEMS = REGISTERER.ITEMS;
    Item INFECTED_HANGING_SIGN_ITEM = ITEMS.register("infected_hanging_sign", ItemsKt.SIGN(INFECTED_HANGING_SIGN,INFECTED_WALL_HANGING_SIGN),new Item.Settings().maxCount(16));
    Item INFECTED_SIGN_ITEM = ITEMS.register("infected_sign", ItemsKt.SIGN(INFECTED_SIGN,INFECTED_WALL_SIGN),new Item.Settings().maxCount(16));
}
