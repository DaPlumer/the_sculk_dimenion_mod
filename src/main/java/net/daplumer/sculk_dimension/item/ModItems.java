package net.daplumer.sculk_dimension.item;

import net.daplumer.data_modification_utils.mod_registries.GeneralDataRegisterer;
import net.daplumer.sculk_dimension.TheSculkDimension;
import net.daplumer.sculk_dimension.entity.ModEntityTypes;
import net.daplumer.sculk_dimension.item.custom.*;
import net.daplumer.sculk_dimension.item.custom.broken_echo.BrokenEcho;
import net.daplumer.sculk_dimension.util.statistics.SoulHolder;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistryEvents;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.item.*;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.text.Text;
import net.minecraft.util.Rarity;

import static net.daplumer.data_modification_utils.mod_registries.registering_functions.ItemsKt.BOAT;
import static net.daplumer.sculk_dimension.TheSculkDimension.REGISTERER;

public class ModItems {
    public static GeneralDataRegisterer<Item, Item.Settings> ITEMS = REGISTERER.ITEMS;
    public static final Item SCULK_BRICK = ITEMS.register("sculk_brick", new Item.Settings().rarity(Rarity.UNCOMMON));
    public static final Item SCULK_BRICK_CASING =ITEMS.register("sculk_brick_casing", new Item.Settings());

    public static final Item CRYSTALIZED_SOUL =ITEMS.register("crystalized_soul",
            new Item.Settings()
                    .maxCount(16)
                    .rarity(Rarity.UNCOMMON),
            CrystalizedSoul::new
    );
    public static final Item MOSSY_BOOTS = ITEMS.register("mossy_boots",
            new Item.Settings()
                    .armor(ModArmorMaterials.MOSSY_ARMOR, EquipmentType.BOOTS)
    );
    public static final Item RESOANATION_GEM_STAFF = ITEMS.register("resonation_gem_staff",
            new Item.Settings()
                    .maxCount(1)
                    .rarity(Rarity.UNCOMMON)
    );
    public static final Item RESOANATION_GEM = ITEMS.register("resonation_gem",
            new Item.Settings()
                    .maxDamage(25)
                    .maxCount(1)
                    .rarity(Rarity.UNCOMMON),
            ResonationGem::new
    );
    public static final Item BROKEN_ECHO = ITEMS.register("broken_echo",
            new Item.Settings()
                    .maxCount(1)
                    .rarity(Rarity.RARE),
            BrokenEcho::new
    );
    public static final Item MOSSY_BAG = ITEMS.register("mossy_bag",
            new Item.Settings()
                    .maxCount(16),
            MossyBag::new
    );
    public static final Item RESONANT_POLLEN = ITEMS.register("resonant_pollen",
            new Item.Settings()
                    .maxCount(16)
    );
    public static final Item WAX_BRICK = ITEMS.register("wax_brick",
            new Item.Settings()
                    .maxCount(16)
                    .useRemainder(MOSSY_BAG)
                    .component(DataComponentTypes.STORED_ENCHANTMENTS, ItemEnchantmentsComponent.DEFAULT),
            WaxBrick::new
    );
    public static final Item ECHO_MEDALLION = ITEMS.register("echo_totem",
            new Item.Settings()
                    .maxCount(1)
                    .rarity(Rarity.UNCOMMON)
    );
    public static final Item MEMORY_GEM = ITEMS.register("memory_gem",
            new Item.Settings()
                    .maxCount(1)
                    .maxDamage(256)
                    .rarity(Rarity.UNCOMMON),
            MemoryGemKT::new
    );
    public static final Item SOUL_BAG = ITEMS.register("soul_bag",
            new Item.Settings()
                    .maxCount(1),
            SoulBag::new
    );
    public static final Item SCYTHE = ITEMS.register("scythe",
            new Item.Settings()
                    .sword(ModToolMaterials.SCULK, 9.0F,-3.3F)
                    .rarity(Rarity.UNCOMMON)
                    .maxCount(1)
            , Scythe::new
    );
    public static final Item SCULK_CLOTH = ITEMS.register("sculk_cloth");

    public static final Item INFECTED_BOAT = ITEMS.register("infected_boat", BOAT(ModEntityTypes.INFECTED_BOAT));
    public static final Item INFECTED_CHEST_BOAT = ITEMS.register("infected_chest_boat", BOAT(ModEntityTypes.INFECTED_CHEST_BOAT));
    public static final Item RESONATION_HELMET = ITEMS.register("resonation_helmet",
            new Item.Settings()
            .armor(ModArmorMaterials.RESONATION_ARMOR,EquipmentType.HELMET),
            ResonationHelmet::new
    );
    public static final Item SCULKEN_BOOTS = ITEMS.register("sculken_boots",
            new Item.Settings()
                    .armor(ModArmorMaterials.SCULKEN_BOOTS, EquipmentType.BOOTS),
            SculkenBoots::new
    );
    public static final Item SCULKEN_SOUL_BAG = ITEMS.register("sculken_soul_bag",
            new Item.Settings()
                    .maxCount(1),
            SculkenSoulBag::new
    );
    public static final ItemStack MEMORY_GEM_STACK = MEMORY_GEM.getDefaultStack().copy();
    public static void registerModItems(){
        TheSculkDimension.LOGGER.info("Registering Mod items for " + TheSculkDimension.MOD_ID );
        BrokenEcho.registerCustomData();

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> {
                    entries.addBefore(Items.BUNDLE, SOUL_BAG);
                    entries.addAfter(Items.BAMBOO_CHEST_RAFT,INFECTED_BOAT);
                    entries.addAfter(INFECTED_BOAT,INFECTED_CHEST_BOAT);
                }
        );

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
            MEMORY_GEM_STACK.setDamage(0);
            entries.addAfter(Items.DIAMOND,MEMORY_GEM_STACK);
            entries.addBefore(CRYSTALIZED_SOUL,SCULK_CLOTH);

        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> {
                entries.addBefore(Items.GOLDEN_HELMET,RESONATION_HELMET);
                entries.addAfter(Items.LEATHER_BOOTS,MOSSY_BOOTS);
                entries.addAfter(Items.TOTEM_OF_UNDYING,ECHO_MEDALLION);
                entries.addAfter(Items.MACE,SCYTHE);
                entries.addAfter(Items.DIAMOND_BOOTS,SCULKEN_BOOTS);
            }
        );
        ItemTooltipCallback.EVENT.register(((stack, tooltipContext, tooltipType, lines) ->{
            if(stack.isOf(ModItems.RESOANATION_GEM)){
                lines.add(Text.translatable("tooltips.sculk_dimension.resonation_gem"));
                lines.add(Text.translatable("tooltips.sculk_dimension.resonation_gem_2"));
            }
            if(stack.isOf(ModItems.ECHO_MEDALLION)){
                lines.add(Text.translatable("tooltips.sculk_dimension.echo_medallion"));
                lines.add(Text.translatable("tooltips.sculk_dimension.echo_medallion_2"));
            }
            if(stack.getItem() instanceof SoulHolder &! stack.isOf(ModItems.CRYSTALIZED_SOUL)){
                if(SoulHolder.getSouls(stack) == 0) return;
                lines.add(Text.translatable("tooltips.sculk_dimension.soul_bag")
                        .append(Text.literal(SoulHolder.getSouls(stack) + "/" + SoulHolder.maxSouls(stack)))
                );
            }
        }));
    }
}
