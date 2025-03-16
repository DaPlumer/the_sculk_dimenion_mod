package net.daplumer.sculk_dimension.item;

import net.daplumer.sculk_dimension.TheSculkDimension;
import net.daplumer.sculk_dimension.item.custom.MossyBag;
import net.daplumer.sculk_dimension.item.custom.WaxBrick;
import net.daplumer.sculk_dimension.item.custom.broken_echo.BrokenEcho;
import net.daplumer.sculk_dimension.item.custom.ModArmorMaterials;
import net.daplumer.sculk_dimension.item.custom.ResonationGem;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class ModItems {
    public static final Item PINK_GARNET =registerItem("pink_garnet", new Item(new Item.Settings()));
    public static final Item RAW_PINK_GARNET =registerItem("raw_pink_garnet", new Item(new Item.Settings()));
    public static final Item SCULK_BRICK =registerItem("sculk_brick", new Item(new Item.Settings().rarity(Rarity.UNCOMMON)));
    public static final Item SCULK_BRICK_CASING =registerItem("sculk_brick_casing", new Item(new Item.Settings()));

    public static final Item CRYSTALIZED_SOUL =registerItem("crystalized_soul", new Item(new Item.Settings().rarity(Rarity.UNCOMMON)));
    public static final Item MOSSY_BOOTS = registerItem("mossy_boots",
            new ArmorItem(ModArmorMaterials.MOSSY_ARMOR, ArmorItem.Type.BOOTS, new Item.Settings()
                    .maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(15))));
    public static final Item RESOANATION_GEM_STAFF = registerItem("resonation_gem_staff", new Item(new Item.Settings().maxCount(1).rarity(Rarity.UNCOMMON)));
    public static final Item RESOANATION_GEM = registerItem("resonation_gem", new ResonationGem(new Item.Settings().maxDamage(25).maxCount(1).rarity(Rarity.UNCOMMON)));
    public static final Item BROKEN_ECHO = registerItem("broken_echo", new BrokenEcho(new Item.Settings().maxCount(1).rarity(Rarity.RARE)));
    public static final Item MOSSY_BAG = registerItem("mossy_bag", new MossyBag(new Item.Settings().maxCount(16)));
    public static final Item RESONANT_POLLEN = registerItem("resonant_pollen", new Item(new Item.Settings().maxCount(16)));
    public static final Item WAX_BRICK = registerItem("wax_brick", new WaxBrick(new Item.Settings()));

    private static Item registerItem(String name, Item item){
        return Registry.register(Registries.ITEM, Identifier.of(TheSculkDimension.MOD_ID,name), item);
    }
    public static void registerModItems(){
        TheSculkDimension.LOGGER.info("Registering Mod Items for " + TheSculkDimension.MOD_ID );
        BrokenEcho.registerCustomData();

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.addAfter(Items.DIAMOND,PINK_GARNET);
            entries.addBefore(Items.IRON_INGOT, WAX_BRICK);
            FuelRegistry.INSTANCE.add(ModItems.WAX_BRICK,1200);
            entries.addBefore(WAX_BRICK, RESONANT_POLLEN);
            CompostingChanceRegistry.INSTANCE.add(RESONANT_POLLEN,.025F);
            entries.addBefore(RESONANT_POLLEN, MOSSY_BAG);
            FuelRegistry.INSTANCE.add(MOSSY_BAG,100);
            CompostingChanceRegistry.INSTANCE.add(MOSSY_BAG,.02F);
            entries.addAfter(Items.GOLD_INGOT, SCULK_BRICK);
            entries.addAfter(SCULK_BRICK,SCULK_BRICK_CASING);
            entries.addAfter(Items.RAW_GOLD, RAW_PINK_GARNET);
            entries.addBefore(Items.ECHO_SHARD,CRYSTALIZED_SOUL);
            entries.addAfter(Items.DISC_FRAGMENT_5, RESOANATION_GEM_STAFF);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.addAfter(Items.LEATHER_BOOTS,MOSSY_BOOTS));
    }
}
