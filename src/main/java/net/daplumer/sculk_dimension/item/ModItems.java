package net.daplumer.sculk_dimension.item;

import net.daplumer.sculk_dimension.TheSculkDimension;
import net.daplumer.sculk_dimension.item.custom.broken_echo.BrokenEcho;
import net.daplumer.sculk_dimension.item.custom.ModArmorMaterials;
import net.daplumer.sculk_dimension.item.custom.ResonationGem;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class ModItems {
    public static final Item PINK_GARNET =registerItem("pink_garnet", new Item(new Item.Settings()));
    public static final Item RAW_PINK_GARNET =registerItem("raw_pink_garnet", new Item(new Item.Settings()));
    public static final Item SCULK_BRICK =registerItem("sculk_brick", new Item(new Item.Settings()));
    public static final Item SCULK_BRICK_CASING =registerItem("sculk_brick_casing", new Item(new Item.Settings()));

    public static final Item CRYSTALIZED_SOUL =registerItem("crystalized_soul", new Item(new Item.Settings().rarity(Rarity.UNCOMMON)));
    public static final Item MOSSY_BOOTS = registerItem("mossy_boots",
            new ArmorItem(ModArmorMaterials.MOSSY_ARMOR, ArmorItem.Type.BOOTS, new Item.Settings()
                    .maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(15))));
    public static final Item RESOANATION_GEM_STAFF = registerItem("resonation_gem_staff", new Item(new Item.Settings().maxCount(1).rarity(Rarity.UNCOMMON)));
    public static final Item RESOANATION_GEM = registerItem("resonation_gem", new ResonationGem(new Item.Settings().maxDamage(25).maxCount(1).rarity(Rarity.UNCOMMON)));
    public static final Item BROKEN_ECHO = registerItem("broken_echo", new BrokenEcho(new Item.Settings().maxCount(1).rarity(Rarity.RARE)));

    private static Item registerItem(String name, Item item){
        return Registry.register(Registries.ITEM, Identifier.of(TheSculkDimension.MOD_ID,name), item);
    }
    public static void registerModItems(){
        TheSculkDimension.LOGGER.info("Registering Mod Items for " + TheSculkDimension.MOD_ID );
        BrokenEcho.registerCustomData();

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(PINK_GARNET);
            entries.add(RAW_PINK_GARNET);
            entries.add(SCULK_BRICK);
            entries.add(SCULK_BRICK_CASING);
            entries.add(CRYSTALIZED_SOUL);
            entries.add(RESOANATION_GEM_STAFF);
            entries.add(BROKEN_ECHO);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(MOSSY_BOOTS));
    }
}
