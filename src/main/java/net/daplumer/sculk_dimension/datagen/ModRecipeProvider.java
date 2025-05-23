package net.daplumer.sculk_dimension.datagen;

import net.daplumer.sculk_dimension.block.InfectedBlocks;
import net.daplumer.sculk_dimension.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.recipe.v1.ingredient.DefaultCustomIngredients;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.advancement.criterion.TickCriterion;
import net.minecraft.component.ComponentChanges;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.data.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.recipe.SmithingTransformRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
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
                Ingredient MEMORY_GEM_INGREDIENT = DefaultCustomIngredients.components(Ingredient.ofItem(ModItems.MEMORY_GEM),ComponentChanges.builder().add(DataComponentTypes.DAMAGE,0).build());
                RegistryWrapper.Impl<Item> itemLookup = registries.getOrThrow(RegistryKeys.ITEM);
                createShaped(RecipeCategory.BUILDING_BLOCKS, itemLookup.getOrThrow(REGISTERER.ITEMS.getRegistryKey("memory_gem_block")).value())
                        .pattern("GGG")
                        .pattern("GGG")
                        .pattern("GGG")
                        .input('G', MEMORY_GEM_INGREDIENT)
                        .criterion("get_mem_gem", InventoryChangedCriterion.Conditions.items(ModItems.MEMORY_GEM))
                        .offerTo(exporter,"sculk_dimension:mem_gem_block");
                InfectedBlocks.infected.registerCustomRecipes(this);
                offerBoatRecipe(ModItems.INFECTED_BOAT, InfectedBlocks.infected.getPlanks().planks());
                offerChestBoatRecipe(ModItems.INFECTED_CHEST_BOAT, InfectedBlocks.infected.getPlanks().planks());
                //The resonation helmet recipe is bound to change after a new model is created
                createShaped(RecipeCategory.COMBAT, ModItems.RESONATION_HELMET)
                        .pattern("CSC")
                        .pattern("LGP")
                        .input('C', Items.ECHO_SHARD)
                        .input('S', ModItems.CRYSTALIZED_SOUL)
                        .input('G', Items.GOLDEN_HELMET)
                        .input('L', ConventionalItemTags.LEATHERS)
                        .input('P', ConventionalItemTags.GLASS_PANES_COLORLESS)
                        .criterion("avoid_vibration", Criteria.AVOID_VIBRATION.create(TickCriterion.Conditions.createTick().conditions()))
                        .offerTo(exporter);

                SmithingTransformRecipeJsonBuilder.create(
                        Ingredient.ofItem(ModItems.SCULK_CLOTH),
                        Ingredient.ofItem(Items.DIAMOND_BOOTS),
                        MEMORY_GEM_INGREDIENT,
                        RecipeCategory.COMBAT,
                        ModItems.SCULKEN_BOOTS
                    )
                    .criterion("get_mossy_boots", InventoryChangedCriterion.Conditions.items(ModItems.MOSSY_BOOTS))
                    .criterion("avoid_vibration", Criteria.AVOID_VIBRATION.create(TickCriterion.Conditions.createTick().conditions()))
                    .criterion("get_diamond_boots", InventoryChangedCriterion.Conditions.items(Items.DIAMOND_BOOTS))
                    .offerTo(this.exporter, "sculken_boots_smithing");

                createBagRecipe(ModItems.SCULKEN_SOUL_BAG, ModItems.SCULK_CLOTH)
                        .group("soul_bags")
                        .criterion("get_soul_bag", InventoryChangedCriterion.Conditions.items(ModItems.SOUL_BAG))
                        .offerTo(exporter, "sculken_soul_bag");

            }
            @SuppressWarnings("SameParameterValue")
            private ShapedRecipeJsonBuilder createBagRecipe(ItemConvertible bag, ItemConvertible material){
                return createBagRecipe(bag, Ingredient.ofItem(material));
            }
            private ShapedRecipeJsonBuilder createBagRecipe(ItemConvertible bag, Ingredient material){
                return createShaped(RecipeCategory.TOOLS, bag)
                        .pattern("  S")
                        .pattern("M M")
                        .pattern(" M ")
                        .input('S', ingredientFromTag(ConventionalItemTags.STRINGS))
                        .input('M', material);
            }
        };
    }

    @Override
    public String getName() {
        return "TheSculkDimensionRecipeProvider";
    }
}
