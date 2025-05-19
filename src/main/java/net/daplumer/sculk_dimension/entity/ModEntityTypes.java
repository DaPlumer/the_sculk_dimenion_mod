package net.daplumer.sculk_dimension.entity;

import net.daplumer.data_modification_utils.mod_registries.ModBlockEntityTypeRegisterer;
import net.daplumer.data_modification_utils.mod_registries.ModEntityTypeRegisterer;
import net.daplumer.sculk_dimension.TheSculkDimension;
import net.daplumer.sculk_dimension.block.InfectedBlocks;
import net.daplumer.sculk_dimension.item.ModItems;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.entity.vehicle.ChestBoatEntity;

import static net.daplumer.data_modification_utils.mod_registries.registering_functions.EntityTypesKt.BOAT;
import static net.daplumer.data_modification_utils.mod_registries.registering_functions.EntityTypesKt.CHEST_BOAT;

@SuppressWarnings("unchecked")
public class ModEntityTypes {
    public static final ModEntityTypeRegisterer ENTITY_TYPES = TheSculkDimension.REGISTERER.ENTITY_TYPES;
    public static final EntityType<BoatEntity> INFECTED_BOAT = (EntityType<BoatEntity>) ENTITY_TYPES.register("infected_boat",BOAT(()->ModItems.INFECTED_BOAT));
    public static final EntityType<ChestBoatEntity> INFECTED_CHEST_BOAT = (EntityType<ChestBoatEntity>) ENTITY_TYPES.register("infected_chest_boat",CHEST_BOAT(()->ModItems.INFECTED_CHEST_BOAT));
    public static void Initialize(){
        TheSculkDimension.LOGGER.info("Registering Mod Entity Types for " + TheSculkDimension.MOD_ID);
    }
}
