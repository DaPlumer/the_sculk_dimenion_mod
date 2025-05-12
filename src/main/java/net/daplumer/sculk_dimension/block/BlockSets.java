package net.daplumer.sculk_dimension.block;

import net.daplumer.mod_registerer.utils.BlockSetCreator;
import net.daplumer.mod_registerer.mod_registries.BlockSetTypeRegisterer;
import net.daplumer.mod_registerer.mod_registries.ModDataRegisterer;
import net.daplumer.mod_registerer.mod_registries.WoodTypeReigsterer;
import net.daplumer.sculk_dimension.TheSculkDimension;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.MapColor;
import net.minecraft.block.WoodType;

import static net.daplumer.sculk_dimension.TheSculkDimension.REGISTERER;

public class BlockSets {
    public static final ModDataRegisterer<BlockSetType, BlockSetTypeRegisterer.Settings> BLOCK_SET_TYPES = REGISTERER.BLOCK_SET_TYPES;
    public static final ModDataRegisterer<WoodType, WoodTypeReigsterer.Settings> WOOD_TYPES = REGISTERER.WOOD_TYPES;
    public static final BlockSetType INFECTED_WOOD_SET = BLOCK_SET_TYPES.register("infected");
    public static final WoodType INFECTED_WOOD = WOOD_TYPES.register("infected", WoodTypeReigsterer.Settings.of(INFECTED_WOOD_SET));
    public static final BlockSetCreator.FullBlockSet INFECTED_BLOCKS = BlockSetCreator.registerFullWoodSet(TheSculkDimension.MOD_ID,
            INFECTED_WOOD_SET,
            INFECTED_WOOD,
            MapColor.TERRACOTTA_CYAN,
            MapColor.TERRACOTTA_CYAN,
            MapColor.TERRACOTTA_GRAY,
            true
    );
    public static void registerBlockSets(){
        TheSculkDimension.LOGGER.info("Registering Mod Block Sets for " + TheSculkDimension.MOD_ID);
    }
}
