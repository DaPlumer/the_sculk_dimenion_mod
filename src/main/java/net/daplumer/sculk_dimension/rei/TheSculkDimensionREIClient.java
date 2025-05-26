package net.daplumer.sculk_dimension.rei;

import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import me.shedaniel.rei.plugin.common.displays.DefaultInformationDisplay;
import net.daplumer.sculk_dimension.TheSculkDimension;
import net.daplumer.sculk_dimension.block.ModBlocks;
import net.daplumer.sculk_dimension.item.ModItems;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.component.type.ItemEnchantmentsComponent.Builder;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.text.Text;
import net.minecraft.util.math.random.Random;

import java.util.List;

public class TheSculkDimensionREIClient implements REIClientPlugin {

    public static final CategoryIdentifier<DuplicateEnchantDisplay> DUPLICATE_ENCHANTS = CategoryIdentifier.of(TheSculkDimension.MOD_ID,"duplicate_enchants");
    @Override
    public void registerCategories(CategoryRegistry registry) {
        REIClientPlugin.super.registerCategories(registry);
        registry.add(new DuplicateEnchantCategory());
        registry.addWorkstations(DUPLICATE_ENCHANTS, EntryStacks.of(ModBlocks.ENCHANTMENT_DUPLICATOR));
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        info("soul_obtaining", registry, ModItems.CRYSTALIZED_SOUL);
        info("obtaining_pollen", registry, ModItems.RESONANT_POLLEN);
        info("sponge_enchanting",registry, ModItems.ENCHANTMENT_SPONGE);
        info("memory_gem",registry, ModItems.MEMORY_GEM);
        REIClientPlugin.super.registerDisplays(registry);
        ItemStack brokenMemGem = new ItemStack(ModItems.MEMORY_GEM);
        brokenMemGem.setDamage(brokenMemGem.getMaxDamage() / 2);
        @SuppressWarnings("UnstableApiUsage") Registry<Enchantment> enchantments = BasicDisplay.registryAccess().getOrThrow(RegistryKeys.ENCHANTMENT);
        Registries.ITEM.getOrThrow(ItemTags.DURABILITY_ENCHANTABLE).stream().forEach((itemRegistryEntry -> {
            ItemStack item = itemRegistryEntry.value().getDefaultStack();
            RegistryEntry<Enchantment> Mending = enchantments.getEntry( enchantments.get(Enchantments.MENDING));
            item.addEnchantment(Mending,1);
            ItemStack damaged = item.copy();
            damaged.setDamage(damaged.getMaxDamage() / 2);
        }));
        Random random = Random.create(TheSculkDimension.MOD_ID.hashCode());
        for (int i = 0; i < 1000; i++) registerDisplaysFor(randomComponent(enchantments,random),registry);



    }

    public static ItemEnchantmentsComponent randomComponent(Registry<Enchantment> registry, Random random){
        ItemEnchantmentsComponent.Builder builder = new Builder(ItemEnchantmentsComponent.DEFAULT);
        for (int i = 0; i < random.nextBetween(1, 5); i++) {
            @SuppressWarnings("OptionalGetWithoutIsPresent") RegistryEntry<Enchantment> entry=registry.getEntry( registry.getRandom(random).get().value());
            builder.add(entry, random.nextBetween(1, entry.value().getMaxLevel()));

        }
        return builder.build();
    }

    public static void registerDisplaysFor(ItemEnchantmentsComponent component, DisplayRegistry registry){
        if(component.getSize() == 1){
            ItemStack enchantment = ModItems.ENCHANTMENT_SPONGE.getDefaultStack();
            enchantment.set(DataComponentTypes.STORED_ENCHANTMENTS, component);
            registerAllEnchantmentDuplicationDisplaysFor(enchantment,registry);
        }
        ItemStack itemStack = ModItems.RESINANT_WAX.getDefaultStack();
        itemStack.set(DataComponentTypes.STORED_ENCHANTMENTS, component);
        registerAllEnchantmentDuplicationDisplaysFor(itemStack,registry);
    }
    private static final List<Item> HOLDERS = List.of(ModItems.CRYSTALIZED_SOUL,ModItems.SOUL_BAG,ModItems.SCULKEN_SOUL_BAG);
    public static void registerAllEnchantmentDuplicationDisplaysFor(ItemStack itemStack, DisplayRegistry registry){
        for (Item item:HOLDERS){
            DuplicateEnchantDisplay display = DuplicateEnchantDisplay.of(item, itemStack);
            if(display != null) registry.add(display);
        }

    }

    public static void info(String name, DisplayRegistry registry, Item item){
        registry.add(DefaultInformationDisplay.createFromEntries(EntryIngredients.of(item), Text.translatable("info.sculk_dimension."+name)).line(
                Text.translatable("info.lines.sculk_dimension."+name)
        ));
    }
}
