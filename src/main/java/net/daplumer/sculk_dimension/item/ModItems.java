package net.daplumer.sculk_dimension.item;

import net.daplumer.sculk_dimension.TheSculkDimension;
import net.daplumer.sculk_dimension.item.custom.ModArmorMaterials;
import net.daplumer.sculk_dimension.item.custom.MossyBag;
import net.daplumer.sculk_dimension.item.custom.WaxBrick;
import net.daplumer.sculk_dimension.item.custom.broken_echo.BrokenEcho;
import net.daplumer.sculk_dimension.item.custom.ResonationGem;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistryEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.util.Rarity;

import static net.daplumer.sculk_dimension.TheSculkDimensionRegistries.*;

public class ModItems {
    public static final Item SCULK_BRICK =ITEMS.register("sculk_brick", new Item.Settings().rarity(Rarity.UNCOMMON));
    public static final Item SCULK_BRICK_CASING =ITEMS.register("sculk_brick_casing", new Item.Settings());

    public static final Item CRYSTALIZED_SOUL =ITEMS.register("crystalized_soul", new Item.Settings().rarity(Rarity.UNCOMMON));
    public static final Item MOSSY_BOOTS = ITEMS.register("mossy_boots", new Item.Settings().armor(ModArmorMaterials.MOSSY_ARMOR, EquipmentType.BOOTS));
    public static final Item RESOANATION_GEM_STAFF = ITEMS.register("resonation_gem_staff", new Item.Settings().maxCount(1).rarity(Rarity.UNCOMMON));
    public static final Item RESOANATION_GEM = ITEMS.register("resonation_gem", new  Item.Settings().maxDamage(25).maxCount(1).rarity(Rarity.UNCOMMON), ResonationGem::new);
    public static final Item BROKEN_ECHO = ITEMS.register("broken_echo", new Item.Settings().maxCount(1).rarity(Rarity.RARE), BrokenEcho::new);
    public static final Item MOSSY_BAG = ITEMS.register("mossy_bag", new  Item.Settings().maxCount(16), MossyBag::new);
    public static final Item RESONANT_POLLEN = ITEMS.register("resonant_pollen", new Item.Settings().maxCount(16));
    public static final Item WAX_BRICK = ITEMS.register("wax_brick", new Item.Settings(), WaxBrick::new);

    public static void registerModItems(){
        TheSculkDimension.LOGGER.info("Registering Mod Items for " + TheSculkDimension.MOD_ID );
        BrokenEcho.registerCustomData();

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.addBefore(Items.IRON_INGOT, WAX_BRICK);
            FuelRegistryEvents.BUILD.register((builder, context) -> builder.add(WAX_BRICK,1200));

            entries.addBefore(WAX_BRICK, RESONANT_POLLEN);
            CompostingChanceRegistry.INSTANCE.add(RESONANT_POLLEN,.025F);
            entries.addBefore(RESONANT_POLLEN, MOSSY_BAG);
            FuelRegistryEvents.BUILD.register((builder, context) -> builder.add(RESONANT_POLLEN,100));

            CompostingChanceRegistry.INSTANCE.add(MOSSY_BAG,.02F);
            entries.addAfter(Items.GOLD_INGOT, SCULK_BRICK);
            entries.addAfter(SCULK_BRICK,SCULK_BRICK_CASING);
            entries.addBefore(Items.ECHO_SHARD,CRYSTALIZED_SOUL);
            entries.addAfter(Items.DISC_FRAGMENT_5, RESOANATION_GEM_STAFF);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.addAfter(Items.LEATHER_BOOTS,MOSSY_BOOTS));
    }
}
