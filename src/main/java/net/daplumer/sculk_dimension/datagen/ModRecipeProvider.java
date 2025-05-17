package net.daplumer.sculk_dimension.datagen;

import net.daplumer.sculk_dimension.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.recipe.v1.ingredient.DefaultCustomIngredients;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.component.ComponentChanges;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.item.Item;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

import static net.daplumer.sculk_dimension.TheSculkDimension.REGISTERER;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup registryLookup, RecipeExporter exporter) {
        return new RecipeGenerator(registryLookup,exporter){
            @Override
            public void generate() {
                RegistryWrapper.Impl<Item> itemLookup = registries.getOrThrow(RegistryKeys.ITEM);
                createShaped(RecipeCategory.BUILDING_BLOCKS, itemLookup.getOrThrow(REGISTERER.ITEMS.getRegistryKey("memory_gem_block")).value())
                        .pattern("GGG")
                        .pattern("GGG")
                        .pattern("GGG")
                        .input('G', DefaultCustomIngredients.components(Ingredient.ofItem(ModItems.MEMORY_GEM),ComponentChanges.builder().add(DataComponentTypes.DAMAGE,0).build()))
                        .criterion("get_mem_gem", InventoryChangedCriterion.Conditions.items(ModItems.MEMORY_GEM))
                        .offerTo(exporter,"sculk_dimension:mem_gem_block");
            }
        };
    }

    @Override
    public String getName() {
        return "TheSculkDimensionRecipeProvider";
    }
}
