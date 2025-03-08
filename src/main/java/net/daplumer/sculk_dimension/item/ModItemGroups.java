package net.daplumer.sculk_dimension.item;

import net.daplumer.sculk_dimension.TheSculkDimension;
import net.daplumer.sculk_dimension.block.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups  {
    public static final ItemGroup PINK_GARNET_ITEMS_GROUP = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(TheSculkDimension.MOD_ID, "pink_garnet_items"),
            FabricItemGroup.builder().icon(() -> new ItemStack(ModItems.PINK_GARNET))
                    .displayName(Text.translatable("itemgroup.sculk_dimension.pink_garnet_items"))
                    .entries((displayContext, entries) -> {
                        entries.add(ModItems.PINK_GARNET);
                        entries.add(ModItems.RAW_PINK_GARNET);
                    })
                    .build());
    public static final ItemGroup PINK_GARNET_BLOCKS_GROUP = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(TheSculkDimension.MOD_ID, "pink_garnet_blocks"),
            FabricItemGroup.builder().icon(() -> new ItemStack(ModBlocks.PINK_GARNET_BLOCK))
                    .displayName(Text.translatable("itemgroup.sculk_dimension.pink_garnet_blocks"))
                    .entries((displayContext, entries) -> {
                        entries.add(ModBlocks.PINK_GARNET_BLOCK);
                        entries.add(ModBlocks.RAW_PINK_GARNET_BLOCK);
                        entries.add(ModBlocks.PINK_GARNET_ORE);
                        entries.add(ModBlocks.DEEPSLATE_PINK_GARNET_ORE);
                    })
                    .build());
    public static ItemStack insaneEcho = new ItemStack(ModItems.BROKEN_ECHO);
    public static final ItemGroup SCULK_DIMENSION_ITEMS = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(TheSculkDimension.MOD_ID, "sculk_dimension_items"),
            FabricItemGroup.builder().icon(() -> new ItemStack(ModItems.BROKEN_ECHO)
                    )
                    .displayName(Text.translatable("itemgroup.sculk_dimension.sculk_dimension_items"))
                    .entries((displayContext, entries) -> {
                        entries.add(ModItems.CRYSTALIZED_SOUL);
                        entries.add(ModItems.SCULK_BRICK);
                        entries.add(ModItems.SCULK_BRICK_CASING);
                        entries.add(ModItems.RESOANATION_GEM_STAFF);
                        entries.add(ModItems.RESOANATION_GEM);
                        entries.add(ModItems.BROKEN_ECHO);
                        entries.add(ModItems.MOSSY_BOOTS);
                    })
                    .build());

    public static void registerItemGroups() {
        TheSculkDimension.LOGGER.info("Registering Item groups for " +TheSculkDimension.MOD_ID);
    }
}