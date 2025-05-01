package net.daplumer.sculk_dimension;

import net.daplumer.modregisterer.ModRegistries.ModArmorMaterialRegisterer;
import net.daplumer.modregisterer.ModRegistries.ModDataRegisterer;
import net.daplumer.modregisterer.ModRegistries.ModEntityTypeRegisterer;
import net.daplumer.modregisterer.ModRegistries.ModRegistries;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.ExperienceDroppingBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.stat.Stat;
import net.minecraft.stat.StatFormatter;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.IntProvider;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

import static net.daplumer.modregisterer.ModRegistries.ModRegistries.*;

@SuppressWarnings({"unused", "SameParameterValue", "rawtypes"})
public record TheSculkDimensionRegistries() {

    /**
     * The general registerer for item creation. Member of the {@link net.daplumer.modregisterer.ModRegistries.GeneralModDataRegisterer GeneralModDataRegisterer} class.
     * @implNote Documentation on general data registration can be found in the
     * {@link net.daplumer.modregisterer.ModRegistries.GeneralModDataRegisterer#register(String, Object, Function) register} method of the
     * {@link net.daplumer.modregisterer.ModRegistries.GeneralModDataRegisterer GeneralModDataRegisterer} class.
     * @see net.daplumer.modregisterer.ModRegistries.GeneralModDataRegisterer GeneralModDataRegisterer
     * @see net.daplumer.modregisterer.ModRegistries.GeneralModDataRegisterer#register(String, Object, Function) register(name, instanceSettings, instanceFactory)
     * @see net.daplumer.modregisterer.ModRegistries.GeneralModDataRegisterer#register(String, Object) register(name, instanceSettings)
     * @see net.daplumer.modregisterer.ModRegistries.GeneralModDataRegisterer#register(String) register(name)
     * @see ModDataRegisterer
     * @see ModRegistries
     * @see ModRegistries#ITEM_REGISTERER_CONSTRUCTOR
     */
    public static ModDataRegisterer<Item, Item.Settings> ITEMS = (ITEM_REGISTERER_CONSTRUCTOR.apply(getNamespace()));
    /**
     * The general registerer for block creation. Member of the {@link net.daplumer.modregisterer.ModRegistries.GeneralModDataRegisterer GeneralModDataRegisterer} class.
     * @implNote Documentation on general data registration can be found in the
     * {@link net.daplumer.modregisterer.ModRegistries.GeneralModDataRegisterer#register(String, Object, Function) register} method of the
     * {@link net.daplumer.modregisterer.ModRegistries.GeneralModDataRegisterer GeneralModDataRegisterer} class.
     * @see net.daplumer.modregisterer.ModRegistries.GeneralModDataRegisterer GeneralModDataRegisterer
     * @see net.daplumer.modregisterer.ModRegistries.GeneralModDataRegisterer#register(String, Object, Function) register(name, instanceSettings, instanceFactory)
     * @see net.daplumer.modregisterer.ModRegistries.GeneralModDataRegisterer#register(String, Object) register(name, instanceSettings)
     * @see net.daplumer.modregisterer.ModRegistries.GeneralModDataRegisterer#register(String) register(name)
     * @see ModDataRegisterer
     * @see ModRegistries
     * @see ModRegistries#BLOCK_REGISTERER_CONSTRUCTOR
     */
    public static ModDataRegisterer<Block, AbstractBlock.Settings> BLOCKS = (BLOCK_REGISTERER_CONSTRUCTOR.apply(getNamespace()));

    /**
     * The general registerer for item group creation. Member of the {@link net.daplumer.modregisterer.ModRegistries.GeneralModDataRegisterer GeneralModDataRegisterer} class.
     * @implNote Documentation on general data registration can be found in the
     * {@link net.daplumer.modregisterer.ModRegistries.GeneralModDataRegisterer#register(String, Object, Function) register} method of the
     * {@link net.daplumer.modregisterer.ModRegistries.GeneralModDataRegisterer GeneralModDataRegisterer} class.
     * @see net.daplumer.modregisterer.ModRegistries.GeneralModDataRegisterer GeneralModDataRegisterer
     * @see net.daplumer.modregisterer.ModRegistries.GeneralModDataRegisterer#register(String, Object, Function) register(name, instanceSettings, instanceFactory)
     * @see net.daplumer.modregisterer.ModRegistries.GeneralModDataRegisterer#register(String, Object) register(name, instanceSettings)
     * @see net.daplumer.modregisterer.ModRegistries.GeneralModDataRegisterer#register(String) register(name)
     * @see ModDataRegisterer
     * @see ModRegistries
     * @see ModRegistries#ITEM_GROUP_REGISTERER_CONSTRUCTOR
     */
    public static ModDataRegisterer<ItemGroup, ItemGroup.Builder> ITEM_GROUPS = (ITEM_GROUP_REGISTERER_CONSTRUCTOR.apply(getNamespace()));


    /**
     * Mod registerer for statistic registration
     * @implNote see {@link net.daplumer.modregisterer.ModRegistries.ModStatTypeRegisterer ModStatTypeRegisterer} for implementation information.
     * @see net.daplumer.modregisterer.ModRegistries.ModStatTypeRegisterer ModStatTypeRegisterer
     * @see net.daplumer.modregisterer.ModRegistries.ModStatTypeRegisterer#register(String, StatFormatter, Function) register(name, instanceSettings, instanceFactory)
     * @see ModDataRegisterer
     * @see ModRegistries
     * @see ModRegistries#STAT_REGISTERER_CONSTRUCTOR
     *
     */
    public static ModDataRegisterer<Stat<Identifier>, StatFormatter> STATS = (STAT_REGISTERER_CONSTRUCTOR.apply(getNamespace()));

    /**
     * Mod registerer for entity types
     * @implNote see {@link ModEntityTypeRegisterer ModEntityTypeRegisterer} for implementation details.
     * @see ModEntityTypeRegisterer ModEntityTypeRegisterer
     * @see ModEntityTypeRegisterer#register(String, EntityType.Builder, Function) register(String, EntityType.Builder, Function)
     * @see ModDataRegisterer
     * @see ModRegistries
     * @see ModRegistries#ENTITY_TYPE_REGISTERER_CONSTRUCTOR
     */
    public static ModDataRegisterer<EntityType, EntityType.Builder> ENTITY_TYPES = (ENTITY_TYPE_REGISTERER_CONSTRUCTOR.apply(getNamespace()));

    public static ModDataRegisterer<ArmorMaterial, ModArmorMaterialRegisterer.Settings> ARMOR_MATERIALS = (ARMOR_MATERIAL_REGISTERER_CONSTRUCTOR.apply(getNamespace()));
    public static @NotNull String getNamespace(){
        return "sculk_dimension";
    }

    /**
     * This function is used for telling the registerer that the {@code item} being registered has a {@code block} associated with it.
     * @param block The block associated with the item currently being registered.
     * @implNote Place this function in the {@code instanceFactory} parameter in the {@linkplain ModDataRegisterer#register(String, Object, Function) registration function,}
     *  E.g.
     *
     * <p>
     *     <h>
     *  {@link  ModDataRegisterer#register(String, Object, Function)
     * public static final BlockItem FOO_ITEM =
     * <p>
     * ModDataRegisterer.register(
     *  <pre>
     *     "foo",
     *     new Item.Settings(),
     *     BLOCK_ITEM(FOO_BLOCK)</pre>
     * );}
     * <p>
     * @see #BLOCKS
     * @see #ITEMS
     * @see ModDataRegisterer#register(String, Object, Function) register(String, Object, Function)
     * @see ModRegistries
     * @see ModDataRegisterer
     * @see net.daplumer.modregisterer.ModRegistries.GeneralModDataRegisterer GeneralModDataRegisterer
     */
     public static @NotNull Function<Item.Settings, Item> BLOCK_ITEM(Block block){
        return (settings -> new BlockItem(block, settings));
    }

    public static Function<AbstractBlock.Settings, Block> EXP_DROPPER(IntProvider provider){
         return (settings -> new ExperienceDroppingBlock(provider,settings));
    }

    /**
     * Register static registerers for usage in the mod initializer.
     * @see net.daplumer.modregisterer.ModRegistries.GeneralModDataRegisterer GeneralModDataRegisterer
     * @see ModDataRegisterer
     * @see ModRegistries
     */
    public static void Initialize(){
    }
}