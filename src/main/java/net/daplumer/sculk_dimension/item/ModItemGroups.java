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

@SuppressWarnings("unused")
public class ModItemGroups  {
    public static final ItemGroup SCULK_DIMENSION_ITEMS = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(TheSculkDimension.MOD_ID, "sculk_dimension_items"),
            FabricItemGroup.builder().icon(() -> new ItemStack(ModItems.BROKEN_ECHO)
                    )
                    .displayName(Text.translatable("itemgroup.sculk_dimension.sculk_dimension_items"))
                    .entries((displayContext, entries) -> {
                        entries.add(ModBlocks.SCULK_CAPTURE_ITEM);
                        entries.add(ModItems.CRYSTALIZED_SOUL);
                        entries.add(ModItems.ECHO_MEDALLION);
                        entries.add(ModItems.SCULK_BRICK);
                        entries.add(ModItems.SCULK_BRICK_CASING);
                        entries.add(ModItems.RESOANATION_GEM_STAFF);
                        entries.add(ModItems.RESOANATION_GEM);
                        entries.add(ModItems.BROKEN_ECHO);
                        entries.add(ModItems.MEMORY_GEM);
                        entries.add(ModItems.MOSSY_BOOTS);
                        entries.add(ModItems.MOSSY_BAG);
                        entries.add(ModItems.RESONANT_POLLEN);
                        entries.add(ModItems.WAX_BRICK);
                        entries.add(ModBlocks.ECHOING_BLOOM_ITEM);
                        entries.add(ModBlocks.MEMORY_GEM_ORE_ITEM);
                        entries.add(ModBlocks.MEMORY_GEM_DEEPSLATE_ORE_ITEM);
                    })

                    .build());

    public static void registerItemGroups() {
        TheSculkDimension.LOGGER.info("Registering Item groups for " +TheSculkDimension.MOD_ID);
    }
}