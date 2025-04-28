package net.daplumer.modregisterer.ModRegistries;

import net.minecraft.item.Item;
import net.minecraft.item.equipment.*;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public final class ModArmorMaterialRegisterer implements ModDataRegisterer<ArmorMaterial, ModArmorMaterialRegisterer.Settings>{
    public static final Map<RegistryKey<EquipmentAsset>, ArmorMaterial> armorMaterials = new HashMap<>(Map.of(
            ArmorMaterials.LEATHER.assetId(),ArmorMaterials.LEATHER,
            ArmorMaterials.CHAIN.assetId(),ArmorMaterials.CHAIN,
            ArmorMaterials.IRON.assetId(),ArmorMaterials.IRON,
            ArmorMaterials.GOLD.assetId(),ArmorMaterials.GOLD,
            ArmorMaterials.DIAMOND.assetId(),ArmorMaterials.DIAMOND,
            ArmorMaterials.NETHERITE.assetId(),ArmorMaterials.NETHERITE,
            ArmorMaterials.TURTLE_SCUTE.assetId(),ArmorMaterials.TURTLE_SCUTE,
            ArmorMaterials.ARMADILLO_SCUTE.assetId(),ArmorMaterials.ARMADILLO_SCUTE
        )
    );

    private final String namespace;

    ModArmorMaterialRegisterer(String namespace){
        this.namespace = namespace;
    }

    @Override
    public String getNameSpace() {
        return namespace;
    }

    @Override
    public ArmorMaterial register(@NotNull String name, @Nullable Settings instanceSettings, @Nullable Function<Settings, ArmorMaterial> instanceFactory) {
        Function<Settings, ArmorMaterial> factory = Objects.requireNonNullElse(instanceFactory, Settings::build);
        Settings settings = Objects.requireNonNullElse(instanceSettings,Settings.copy(ArmorMaterials.LEATHER));
        settings.assetId(getRegistryKey(name));
        ArmorMaterial instance = factory.apply(settings);
        armorMaterials.put(getRegistryKey(name),instance);
        return instance;
    }

    @Override
    public ArmorMaterial getInstance(Identifier identifier) {
        return armorMaterials.get(getRegistryKey(identifier));
    }

    @Override
    public RegistryKey<EquipmentAsset> getRegistryKey(Identifier identifier) {
        return RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, identifier);
    }

    @SuppressWarnings("unchecked")
    @Override
    public RegistryKey<EquipmentAsset> getRegistryKey(String name) {
        return ModDataRegisterer.super.getRegistryKey(name);
    }

    @SuppressWarnings("unused")
    public static final class Settings implements builder<ArmorMaterial>{
        private Float toughness = null;
        private Float knockbackResistance = null;
        private Integer enchantmentValue = null;
        private RegistryEntry<SoundEvent> equipSound = null;
        private Integer durability = null;
        private TagKey<Item> repairIngredient = null;
        private RegistryKey<EquipmentAsset> assetId = null;
        private Map<EquipmentType, Integer> defenceValues = null;


        private Settings(){}
        public static @NotNull Settings create(){return new Settings();}
        public static @NotNull Settings copy(@NotNull ArmorMaterial instance){
            Settings settings = Settings.create()
                    .durability(instance.durability())
                    .equipSound(instance.equipSound())
                    .knockbackResistance(instance.knockbackResistance())
                    .enchantmentValue(instance.enchantmentValue())
                    .repairIngredient(instance.repairIngredient())
                    .toughness(instance.toughness());
            settings.defenceValues = instance.defense();
            return settings;
        }
        public Settings durability(int durability) {
            this.durability = durability;
            return this;
        }

        public Settings helmetDefence(int defence){
            return setDefenceValue(defence, EquipmentType.HELMET);
        }
        public Settings chestplateDefence(int defence){
            return setDefenceValue(defence, EquipmentType.CHESTPLATE);
        }
        public Settings leggingsDefence(int defence){
            return setDefenceValue(defence, EquipmentType.LEGGINGS);
        }
        public Settings bootsDefence(int defence){
            return setDefenceValue(defence, EquipmentType.BOOTS);
        }
        public Settings bodyDefence(int defence){
            return setDefenceValue(defence, EquipmentType.BODY);
        }

        private Settings setDefenceValue(int defenceValue, EquipmentType type){
            if (defenceValues == null) {
                defenceValues = new EnumMap<>(EquipmentType.class);
            }
            defenceValues.put(type, defenceValue);
            return this;

        }

        public Settings defenceValues(int helmetDefence, int chestplateDefence, int leggingsDefence,int bootsDefence,int bodyDefence){
            return this
                    .helmetDefence(helmetDefence)
                    .chestplateDefence(chestplateDefence)
                    .leggingsDefence(leggingsDefence)
                    .bootsDefence(bootsDefence)
                    .bodyDefence(bodyDefence);
        }
        public Settings equipSound(RegistryEntry<SoundEvent> sound){
            this.equipSound = sound;
            return this;
        }

        public Settings toughness(float toughness){
            this.toughness = toughness;
            return this;
        }

        public Settings knockbackResistance(float knockbackResistance){
            this.knockbackResistance = knockbackResistance;
            return this;
        }

        @SuppressWarnings("UnusedReturnValue")
        public Settings assetId(RegistryKey<EquipmentAsset> assetId){
            this.assetId = assetId;
            return this;
        }
        public Settings enchantmentValue(int enchantmentValue){
            this.enchantmentValue = enchantmentValue;
            return this;
        }

        @Override
        public ArmorMaterial build(){
            return new ArmorMaterial(getDurability(), getDefenceValues(),getEnchantmentValue(),getEquipSound(),getToughness(),getKnockbackResistance(),getRepairIngredient(),getAssetId());
        }

        public Settings repairIngredient(TagKey<Item> ingredient){
            this.repairIngredient = ingredient;
            return this;
        }

        public Float getKnockbackResistance() {
            return Objects.requireNonNullElse(knockbackResistance,0f);
        }

        public Float getToughness() {
            return Objects.requireNonNullElse(toughness,0f);
        }

        public Integer getDurability() {
            return Objects.requireNonNullElse(durability,0);
        }

        public Integer getEnchantmentValue() {
            return Objects.requireNonNullElse( enchantmentValue,0);
        }

        public Map<EquipmentType, Integer> getDefenceValues() {
            return defenceValues;
        }

        public RegistryKey<EquipmentAsset> getAssetId() {
            return assetId;
        }

        public RegistryEntry<SoundEvent> getEquipSound() {
            return equipSound;
        }

        public TagKey<Item> getRepairIngredient() {
            return repairIngredient;
        }
    }
}
