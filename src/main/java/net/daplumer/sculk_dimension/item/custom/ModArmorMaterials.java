package net.daplumer.sculk_dimension.item.custom;

import net.daplumer.mod_registerer.mod_registries.ModArmorMaterialRegisterer;
import net.daplumer.mod_registerer.mod_registries.ModDataRegisterer;
import net.daplumer.sculk_dimension.TheSculkDimension;
import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.item.equipment.ArmorMaterials;

import static net.daplumer.sculk_dimension.TheSculkDimension.REGISTERER;

public class ModArmorMaterials {
    public static final ModDataRegisterer<ArmorMaterial, ModArmorMaterialRegisterer.Settings> ARMOR_MATERIALS = REGISTERER.ARMOR_MATERIALS;
    public static final ArmorMaterial MOSSY_ARMOR = ARMOR_MATERIALS.register("mossy_armor",
            ModArmorMaterialRegisterer.Settings.copy(ArmorMaterials.LEATHER).enchantmentValue(17));
    public static void registerModArmorMaterials(){
        TheSculkDimension.LOGGER.info("Registering Mod Armor Materials for " + TheSculkDimension.MOD_ID);
    }
}
