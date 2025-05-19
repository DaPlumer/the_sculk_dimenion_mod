package net.daplumer.sculk_dimension.block;

import net.daplumer.data_modification_utils.block_set_generation.wood.WoodBlockGroup;
import net.daplumer.data_modification_utils.block_set_generation.wood.WoodTypeSounds;
import net.minecraft.block.MapColor;

import static net.daplumer.sculk_dimension.block.ModBlocks.BLOCKS;

public interface InfectedBlocks {
    WoodBlockGroup infected = WoodBlockGroup.of(BLOCKS,"infected",true,MapColor.LIGHT_BLUE,MapColor.TERRACOTTA_CYAN,MapColor.TERRACOTTA_GRAY,WoodTypeSounds.NETHER);
}
