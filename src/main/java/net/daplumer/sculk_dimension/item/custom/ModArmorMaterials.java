package net.daplumer.sculk_dimension.item.custom;

import net.daplumer.sculk_dimension.TheSculkDimension;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

public class ModArmorMaterials {
    public static final RegistryEntry<ArmorMaterial> MOSSY_ARMOR = registerArmorMaterial(
            "mossy_armor",
            () -> new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> map.put(ArmorItem.Type.BOOTS,2)),
                    20, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER,
                    () -> Ingredient.ofItems(Items.LEATHER),
                    List.of(new ArmorMaterial.Layer(Identifier.of(TheSculkDimension.MOD_ID, "mossy"),"",true),new ArmorMaterial.Layer(Identifier.of(TheSculkDimension.MOD_ID, "mossy"),"_overlay",false)), 0, 0));

    public static RegistryEntry<ArmorMaterial> registerArmorMaterial (String name, Supplier<ArmorMaterial> material){
        return Registry.registerReference(Registries.ARMOR_MATERIAL, Identifier.of(TheSculkDimension.MOD_ID, name), material.get());
    }
}
