package net.daplumer.sculk_dimension.block;

import net.daplumer.sculk_dimension.TheSculkDimension;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.WoodType;

public class BlockSets {
    public static final BlockSetType INFECTED_BLOCKS = net.daplumer.data_modification_utils.mod_registries.registering_functions.BlocksKt.registerBlockSetType(new BlockSetType("infected_wood"),"infected_wood");
    public static final WoodType INFECTED_WOOD = net.daplumer.data_modification_utils.mod_registries.registering_functions.BlocksKt.registerWoodType(new WoodType("infected_wood",INFECTED_BLOCKS),"infected_wood");

    public static void initialize(){
        TheSculkDimension.LOGGER.info("Registering Block Sets for " + TheSculkDimension.MOD_ID);
    }
}
