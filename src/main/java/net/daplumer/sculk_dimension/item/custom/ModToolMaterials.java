package net.daplumer.sculk_dimension.item.custom;

import net.daplumer.sculk_dimension.TheSculkDimension;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModToolMaterials {
    public static final TagKey<Item> SCULK_TOOL_MATERIALS = TagKey.of(RegistryKeys.ITEM, Identifier.of(TheSculkDimension.MOD_ID,"sculk_tool_materials"));
    public static final ToolMaterial SCULK = new ToolMaterial(
            BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 1500, 7.5F, 2.0F, 25, SCULK_TOOL_MATERIALS);
    public static void registerModToolMaterials(){
        TheSculkDimension.LOGGER.info("Registering Mod Tool Materials for " + TheSculkDimension.MOD_ID);
    }
}
