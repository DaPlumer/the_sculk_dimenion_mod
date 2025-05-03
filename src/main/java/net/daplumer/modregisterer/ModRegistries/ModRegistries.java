package net.daplumer.modregisterer.ModRegistries;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.stat.Stat;
import net.minecraft.stat.StatFormatter;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * This is a wrapper class for the {@link GeneralModDataRegisterer} to merge all data registration into two files. Find more information there.
 * @see GeneralModDataRegisterer
 * @see Registry
 */
@SuppressWarnings({"rawtypes", "unchecked", "unused"})
public record ModRegistries() {

    public static final Function<String, ModDataRegisterer<Item, Item.Settings>>                                ITEM_REGISTERER_CONSTRUCTOR              = generalRegisterer(Registries.ITEM,       Item.Settings::registryKey, Item::new, Item.Settings::new);
    public static final Function<String, ModDataRegisterer<Block, Block.Settings>>                              BLOCK_REGISTERER_CONSTRUCTOR             = generalRegisterer(Registries.BLOCK,      AbstractBlock.Settings::registryKey, Block::new, AbstractBlock.Settings::create);
    public static final Function<String, ModDataRegisterer<ItemGroup, ItemGroup.Builder>>                       ITEM_GROUP_REGISTERER_CONSTRUCTOR        = generalRegisterer(Registries.ITEM_GROUP, (builder, itemGroupRegistryKey) -> builder, ItemGroup.Builder::build, FabricItemGroup::builder);
    public static final Function<String, ModDataRegisterer<BlockEntityType, FabricBlockEntityTypeBuilder>>      BLOCK_ENTITY_TYPE_REGISTERER_CONSTRUCTOR = generalRegisterer(Registries.BLOCK_ENTITY_TYPE,(builder, key) -> builder,FabricBlockEntityTypeBuilder::build,()->null);
    public static final Function<String, ModDataRegisterer<EntityType, EntityType.Builder>>                     ENTITY_TYPE_REGISTERER_CONSTRUCTOR       = ModEntityTypeRegisterer::new;
    public static final Function<String, ModDataRegisterer<Stat<Identifier>, StatFormatter>>                    STAT_REGISTERER_CONSTRUCTOR              = ModStatTypeRegisterer::new;
    public static final Function<String, ModDataRegisterer<ArmorMaterial, ModArmorMaterialRegisterer.Settings>> ARMOR_MATERIAL_REGISTERER_CONSTRUCTOR    = ModArmorMaterialRegisterer::new;


    private static <T,S> @NotNull Function<String, ModDataRegisterer<T,S>> generalRegisterer(Registry registry, BiFunction<S, RegistryKey<T>, S> registryKeySettingsFactory, Function<S, T> defaultInstanceFactory, Supplier defaultSettingsFactory){
        return (string) -> new GeneralModDataRegisterer<>(registry,string, registryKeySettingsFactory, defaultInstanceFactory, defaultSettingsFactory);
    }



}
