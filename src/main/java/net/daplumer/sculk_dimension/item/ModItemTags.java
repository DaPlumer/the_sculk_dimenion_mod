package net.daplumer.sculk_dimension.item;

import net.daplumer.sculk_dimension.TheSculkDimension;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModItemTags {
    public static final TagKey<Item> MUFFLERS = TagKey.of(RegistryKeys.ITEM, Identifier.of(TheSculkDimension.MOD_ID,"muffler"));
}
