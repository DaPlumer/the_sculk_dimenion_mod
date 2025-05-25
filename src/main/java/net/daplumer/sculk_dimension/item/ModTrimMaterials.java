package net.daplumer.sculk_dimension.item;

import net.daplumer.sculk_dimension.TheSculkDimension;
import net.minecraft.client.data.ItemModelGenerator;
import net.minecraft.item.equipment.trim.ArmorTrimAssets;
import net.minecraft.item.equipment.trim.ArmorTrimMaterial;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModTrimMaterials {
    public static final ArmorTrimAssets MEMORY_GEM_ASSET = ArmorTrimAssets.of("memory_gem");
    public static final RegistryKey<ArmorTrimMaterial> MEMORY_GEM_TRIM_KEY = RegistryKey.of(RegistryKeys.TRIM_MATERIAL, Identifier.of(TheSculkDimension.MOD_ID, "memory_gem"));
    public static final ItemModelGenerator.TrimMaterial MEMORY_GEM_TRIM_MATERIAL = new ItemModelGenerator.TrimMaterial(MEMORY_GEM_ASSET,MEMORY_GEM_TRIM_KEY);
    public static void init(){
    }
}
