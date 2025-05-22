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

@SuppressWarnings({"unused"})
public class ModItemGroups  {
    public static final ItemGroup SCULK_DIMENSION_ITEMS = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(TheSculkDimension.MOD_ID, "sculk_dimension_items"),
            FabricItemGroup.builder().icon(() -> new ItemStack(ModItems.BROKEN_ECHO)
                    )
                    .displayName(Text.translatable("itemgroup.sculk_dimension.sculk_dimension_items"))
                    .entries((displayContext, entries) -> {
                        entries.add(ModBlocks.SCULK_CAPTURE_ITEM);
                        entries.add(ModItems.CRYSTALIZED_SOUL);
                        entries.add(ModItems.SCULK_CLOTH);
                        entries.add(ModItems.SCULK_BRICK);
                        entries.add(ModItems.SCULK_BRICK_CASING);
                        entries.add(ModItems.ECHO_MEDALLION);
                        entries.add(ModItems.SCYTHE);
                        entries.add(ModItems.RESOANATION_GEM_STAFF);
                        entries.add(ModItems.RESOANATION_GEM);
                        entries.add(ModItems.BROKEN_ECHO);
                        ItemStack stack = ModItems.MEMORY_GEM.getDefaultStack().copy();
                        entries.add(ModItems.MEMORY_GEM_STACK);
                        entries.add(ModBlocks.MEMORY_GEM_BLOCK_ITEM);
                        entries.add(ModItems.RESONATION_HELMET);
                        entries.add(ModItems.SCULKEN_BOOTS);
                        entries.add(ModItems.MOSSY_BOOTS);
                        entries.add(ModItems.MOSSY_BAG);
                        entries.add(ModItems.RESONANT_POLLEN);
                        entries.add(ModItems.WAX_BRICK);
                        entries.add(ModItems.SOUL_BAG);
                        entries.add(ModBlocks.ECHOING_BLOOM_ITEM);
                        entries.add(ModBlocks.MEMORY_GEM_ORE_ITEM);
                        entries.add(ModBlocks.MEMORY_GEM_DEEPSLATE_ORE_ITEM);
                        entries.add(ModBlocks.ENCHANTMENT_DUPLICATOR_ITEM);
                    })

                    .build());

    public static void registerItemGroups() {
        TheSculkDimension.LOGGER.info("Registering Item groups for " +TheSculkDimension.MOD_ID);
    }
}