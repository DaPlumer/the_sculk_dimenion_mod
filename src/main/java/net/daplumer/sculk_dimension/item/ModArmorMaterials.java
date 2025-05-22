package net.daplumer.sculk_dimension.item;

import net.daplumer.data_modification_utils.mod_registries.ArmorMaterialSettings;
import net.daplumer.data_modification_utils.mod_registries.ModArmorMaterialRegisterer;
import net.daplumer.sculk_dimension.TheSculkDimension;
import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.item.equipment.ArmorMaterials;

import static net.daplumer.sculk_dimension.TheSculkDimension.REGISTERER;

public class ModArmorMaterials {
    public static final ModArmorMaterialRegisterer ARMOR_MATERIALS = REGISTERER.ARMOR_MATERIALS;
    public static final ArmorMaterial MOSSY_ARMOR = ARMOR_MATERIALS.register("mossy_armor",
            ArmorMaterialSettings.copy(ArmorMaterials.LEATHER).enchantmentValue(17));
    public static final ArmorMaterial RESONATION_ARMOR = ARMOR_MATERIALS.register("resonation_helmet",
            ArmorMaterialSettings.copy(ArmorMaterials.GOLD));
    public static void registerModArmorMaterials(){
        TheSculkDimension.LOGGER.info("Registering Mod Armor Materials for " + TheSculkDimension.MOD_ID);
    }
}
