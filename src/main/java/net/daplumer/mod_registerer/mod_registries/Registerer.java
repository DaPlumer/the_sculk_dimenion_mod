package net.daplumer.mod_registerer.mod_registries;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.stat.Stat;
import net.minecraft.stat.StatFormatter;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.IntProvider;

import java.util.function.Function;

import static net.daplumer.mod_registerer.mod_registries.ModRegistries.*;

public final class Registerer {
    private final String namespace;
    public static Registerer of(String namespace){
        return new Registerer(namespace);
    }
    private Registerer(String namespace){
        this.namespace = namespace;
        ITEMS              = ITEM_REGISTERER_CONSTRUCTOR             .apply(getNamespace());
        BLOCKS             = BLOCK_REGISTERER_CONSTRUCTOR            .apply(getNamespace());
        ITEM_GROUPS        = ITEM_GROUP_REGISTERER_CONSTRUCTOR       .apply(getNamespace());
        BLOCK_ENTITY_TYPES = BLOCK_ENTITY_TYPE_REGISTERER_CONSTRUCTOR.apply(getNamespace());
        STATS              = STAT_REGISTERER_CONSTRUCTOR             .apply(getNamespace());
        ENTITY_TYPES       = ENTITY_TYPE_REGISTERER_CONSTRUCTOR      .apply(getNamespace());
        ARMOR_MATERIALS    = ARMOR_MATERIAL_REGISTERER_CONSTRUCTOR   .apply(getNamespace());
        BLOCK_SET_TYPES    = new BlockSetTypeRegisterer(namespace);
        WOOD_TYPES         = new WoodTypeReigsterer(namespace);
    }
    public final ModDataRegisterer<WoodType,WoodTypeReigsterer.Settings> WOOD_TYPES;
    public final ModDataRegisterer<BlockSetType,BlockSetTypeRegisterer.Settings> BLOCK_SET_TYPES;

    /**
     * The general registerer for item creation. Member of the {@link net.daplumer.mod_registerer.mod_registries.GeneralModDataRegisterer GeneralModDataRegisterer} class.
     * @implNote Documentation on general data registration can be found in the
     * {@link net.daplumer.mod_registerer.mod_registries.GeneralModDataRegisterer#register(String, Object, Function) register} method of the
     * {@link net.daplumer.mod_registerer.mod_registries.GeneralModDataRegisterer GeneralModDataRegisterer} class.
     * @see net.daplumer.mod_registerer.mod_registries.GeneralModDataRegisterer GeneralModDataRegisterer
     * @see net.daplumer.mod_registerer.mod_registries.GeneralModDataRegisterer#register(String, Object, Function) register(name, instanceSettings, instanceFactory)
     * @see net.daplumer.mod_registerer.mod_registries.GeneralModDataRegisterer#register(String, Object) register(name, instanceSettings)
     * @see net.daplumer.mod_registerer.mod_registries.GeneralModDataRegisterer#register(String) register(name)
     * @see ModDataRegisterer
     * @see ModRegistries
     * @see ModRegistries#ITEM_REGISTERER_CONSTRUCTOR
     */
    public final ModDataRegisterer<Item, Item.Settings> ITEMS;
    /**
     * The general registerer for block creation. Member of the {@link net.daplumer.mod_registerer.mod_registries.GeneralModDataRegisterer GeneralModDataRegisterer} class.
     * @implNote Documentation on general data registration can be found in the
     * {@link net.daplumer.mod_registerer.mod_registries.GeneralModDataRegisterer#register(String, Object, Function) register} method of the
     * {@link net.daplumer.mod_registerer.mod_registries.GeneralModDataRegisterer GeneralModDataRegisterer} class.
     * @see net.daplumer.mod_registerer.mod_registries.GeneralModDataRegisterer GeneralModDataRegisterer
     * @see net.daplumer.mod_registerer.mod_registries.GeneralModDataRegisterer#register(String, Object, Function) register(name, instanceSettings, instanceFactory)
     * @see net.daplumer.mod_registerer.mod_registries.GeneralModDataRegisterer#register(String, Object) register(name, instanceSettings)
     * @see net.daplumer.mod_registerer.mod_registries.GeneralModDataRegisterer#register(String) register(name)
     * @see ModDataRegisterer
     * @see ModRegistries
     * @see ModRegistries#BLOCK_REGISTERER_CONSTRUCTOR
     */
    public final ModDataRegisterer<Block, AbstractBlock.Settings> BLOCKS;
    /**
     * The general registerer for item group creation. Member of the {@link net.daplumer.mod_registerer.mod_registries.GeneralModDataRegisterer GeneralModDataRegisterer} class.
     * @implNote Documentation on general data registration can be found in the
     * {@link net.daplumer.mod_registerer.mod_registries.GeneralModDataRegisterer#register(String, Object, Function) register} method of the
     * {@link net.daplumer.mod_registerer.mod_registries.GeneralModDataRegisterer GeneralModDataRegisterer} class.
     * @see net.daplumer.mod_registerer.mod_registries.GeneralModDataRegisterer GeneralModDataRegisterer
     * @see net.daplumer.mod_registerer.mod_registries.GeneralModDataRegisterer#register(String, Object, Function) register(name, instanceSettings, instanceFactory)
     * @see net.daplumer.mod_registerer.mod_registries.GeneralModDataRegisterer#register(String, Object) register(name, instanceSettings)
     * @see net.daplumer.mod_registerer.mod_registries.GeneralModDataRegisterer#register(String) register(name)
     * @see ModDataRegisterer
     * @see ModRegistries
     * @see ModRegistries#ITEM_GROUP_REGISTERER_CONSTRUCTOR
     */
    public final ModDataRegisterer<ItemGroup, ItemGroup.Builder> ITEM_GROUPS;
    /**
     * The general registerer for block entity type creation. Member of the {@link GeneralModDataRegisterer GeneralModDataRegisterer} class.
     * @implNote Documentation on general data registration can be found in the
     * {@link GeneralModDataRegisterer#register(String, Object, Function) register} method of the
     * {@link GeneralModDataRegisterer GeneralModDataRegisterer} class.
     * @see GeneralModDataRegisterer GeneralModDataRegisterer
     * @see GeneralModDataRegisterer#register(String, Object, Function) register(name, instanceSettings, instanceFactory)
     * @see GeneralModDataRegisterer#register(String, Object) register(name, instanceSettings)
     * @see ModDataRegisterer
     * @see ModRegistries
     * @see ModRegistries#BLOCK_ENTITY_TYPE_REGISTERER_CONSTRUCTOR
     */
    public final ModDataRegisterer<BlockEntityType<?>, FabricBlockEntityTypeBuilder<?>> BLOCK_ENTITY_TYPES;
    /**
     * Mod registerer for statistic registration
     * @implNote see {@link net.daplumer.mod_registerer.mod_registries.ModStatTypeRegisterer ModStatTypeRegisterer} for implementation information.
     * @see net.daplumer.mod_registerer.mod_registries.ModStatTypeRegisterer ModStatTypeRegisterer
     * @see net.daplumer.mod_registerer.mod_registries.ModStatTypeRegisterer#register(String, StatFormatter, Function) register(name, instanceSettings, instanceFactory)
     * @see ModDataRegisterer
     * @see ModRegistries
     * @see ModRegistries#STAT_REGISTERER_CONSTRUCTOR
     *
     */
    public final ModDataRegisterer<Stat<Identifier>, StatFormatter> STATS;
    /**
     * Mod registerer for entity types
     * @implNote see {@link ModEntityTypeRegisterer ModEntityTypeRegisterer} for implementation details.
     * @see ModEntityTypeRegisterer ModEntityTypeRegisterer
     * @see ModEntityTypeRegisterer#register(String, EntityType.Builder, Function) register(String, EntityType.Builder, Function)
     * @see ModDataRegisterer
     * @see ModRegistries
     * @see ModRegistries#ENTITY_TYPE_REGISTERER_CONSTRUCTOR
     */
    public final ModDataRegisterer<EntityType<?>, EntityType.Builder<?>> ENTITY_TYPES;

    public final ModDataRegisterer<ArmorMaterial, ModArmorMaterialRegisterer.Settings> ARMOR_MATERIALS;
    public String getNamespace(){
        return this.namespace;
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
     * @see net.daplumer.mod_registerer.mod_registries.GeneralModDataRegisterer GeneralModDataRegisterer
     */
    public static Function<Item.Settings, Item> BLOCK_ITEM(Block block){
        return (settings -> new BlockItem(block, settings));
    }
    public static Function<AbstractBlock.Settings, Block> EXP_DROPPER(IntProvider provider){
        return (settings -> new ExperienceDroppingBlock(provider,settings));
    }
    public static Function<AbstractBlock.Settings,Block> STAIRS(Block base){
        return settings -> new StairsBlock(base.getDefaultState(), settings);
    }
    public static Function<AbstractBlock.Settings,Block> TRAPDOOR_BLOCK(BlockSetType type){
        return settings -> new TrapdoorBlock(type,settings);
    }
    public static Function<AbstractBlock.Settings,Block> DOOR_BLOCK(BlockSetType type){
        return settings -> new DoorBlock(type,settings);
    }
    public static Function<AbstractBlock.Settings,Block> SIGN_BLOCK(WoodType type){
        return settings -> new SignBlock(type,settings);
    }
    public static Function<AbstractBlock.Settings,Block> WALL_SIGN_BLOCK(WoodType type){
        return settings -> new WallSignBlock(type,settings);
    }
    public static Function<AbstractBlock.Settings,Block> HANGING_SIGN_BLOCK(WoodType type){
        return settings -> new HangingSignBlock(type,settings);
    }
    public static Function<AbstractBlock.Settings,Block> WALL_HANGING_SIGN_BLOCK(WoodType type){
        return settings -> new WallHangingSignBlock(type,settings);
    }
    public static Function<AbstractBlock.Settings,Block> PRESSURE_PLATE_BLOCK(BlockSetType type){
        return settings -> new PressurePlateBlock(type,settings);
    }
    public static Function<AbstractBlock.Settings,Block> FENCE_GATE_BLOCK(WoodType type){
        return settings -> new FenceGateBlock(type, settings);
    }
    public static Function<AbstractBlock.Settings,Block> BUTTON_BLOCK(BlockSetType type, int pressTicks){
        return settings -> new ButtonBlock(type,pressTicks,settings);
    }
    public static BlockItem registerBlockItem(Block block){
        return Registry.register(Registries.ITEM,Registries.BLOCK.getId(block),
                new BlockItem(block,new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM, Registries.BLOCK.getId(block)))));
    }
}
