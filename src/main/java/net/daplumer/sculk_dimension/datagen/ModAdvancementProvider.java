package net.daplumer.sculk_dimension.datagen;

import net.daplumer.sculk_dimension.TheSculkDimension;
import net.daplumer.sculk_dimension.block.ModBlocks;
import net.daplumer.sculk_dimension.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancement.*;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.advancement.criterion.OnKilledCriterion;
import net.minecraft.advancement.criterion.TickCriterion;
import net.minecraft.block.Blocks;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.entity.DamageSourcePredicate;
import net.minecraft.predicate.entity.EntityEquipmentPredicate;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.entity.LocationPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.recipe.Recipe;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public class ModAdvancementProvider extends FabricAdvancementProvider {
    public ModAdvancementProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(output, registryLookup);
    }

    @Override
    public void generateAdvancement(RegistryWrapper.WrapperLookup registryLookup, Consumer<AdvancementEntry> consumer) {
        AdvancementEntry avoid_vibration = Advancement.Builder.create().build(Identifier.ofVanilla("adventure/avoid_vibration"));
        AdvancementEntry it_spreads = Advancement.Builder.create().build(Identifier.ofVanilla("adventure/kill_mob_near_sculk_catalyst"));
        AdvancementEntry natures_ninja = Advancement.Builder.create()
                .display(ModItems.MOSSY_BOOTS.asItem(),
                        Text.translatable("advancement.sculk_dimension.natures_ninja_title"),
                        Text.translatable("advancement.sculk_dimension.natures_ninja_description"),
                        null,AdvancementFrame.TASK,true,true,false)
                .parent(avoid_vibration)
                .criterion("has_boots", Criteria.AVOID_VIBRATION.create(new TickCriterion.Conditions(Optional.of(
                        EntityPredicate.contextPredicateFromEntityPredicate(EntityPredicate.Builder.create()
                                .equipment(EntityEquipmentPredicate.Builder.create().feet(ItemPredicate.Builder.create()
                                        .items(null, ModItems.MOSSY_BOOTS))))))))
                .build(consumer,of("natures_ninja"));
        AdvancementEntry get_scythe = Advancement.Builder.create()
                .display(ModItems.SCYTHE,
                        Text.translatable("advancement.sculk_dimension.get_scythe_title"),
                        Text.translatable("advancement.sculk_dimension.get_scythe_description"),
                        null,AdvancementFrame.GOAL,true,true,false)
                .criterion("kill_with_scythe", Criteria.PLAYER_KILLED_ENTITY.create(new OnKilledCriterion.Conditions(Optional.empty(),Optional.empty(),
                        Optional.of(DamageSourcePredicate.Builder.create().sourceEntity(EntityPredicate.Builder.create()
                                .equipment(EntityEquipmentPredicate.Builder.create().mainhand(
                                        ItemPredicate.Builder.create().items(null, ModItems.SCYTHE)))).build()))))
                .parent(it_spreads)
                .rewards(AdvancementRewards.Builder
                        .recipe(recipie("soul_bag_shaped")).build())
                .rewards(AdvancementRewards.Builder.recipe(recipie("soul_bag_shapeless")))
                .rewards(AdvancementRewards.Builder.recipe(recipie("enchantment_duplicator")))
                .rewards(AdvancementRewards.Builder.recipe(recipie("mossy_bag")))
                .rewards(AdvancementRewards.Builder.recipe(recipie("resinant_brick")))
                .build(consumer,of("get_scythe"));
        AdvancementEntry duplicate_enchant = Advancement.Builder.create()
                .display(ModBlocks.ENCHANTMENT_DUPLICATOR,
                        Text.translatable("advancement.sculk_dimension.duplicate_enchant_title"),
                        Text.translatable("advancement.sculk_dimension.duplicate_enchant_description"),
                        null,AdvancementFrame.TASK,true,true,false
                        )
                .parent(get_scythe)
                .criterion("use_duplicator", Criteria.TICK.create(TickCriterion.Conditions.createLocation(
                        LocationPredicate.Builder.createY(NumberRange.DoubleRange.between(-999D,-998D))).conditions())
                )
                .build(consumer,of("duplicate_enchant"));
        Advancement.Builder.create().criterion(
                "get_moss",
                InventoryChangedCriterion.Conditions.items(Blocks.MOSS_BLOCK)).rewards(
                                AdvancementRewards.Builder.recipe(recipie("mossy_bag")).addRecipe(recipie("mossy_boots")))
                .build(consumer,of("get_moss"));
        Advancement.Builder.create().criterion(
                        "get_catalyst",
                        InventoryChangedCriterion.Conditions.items(Blocks.SCULK_CATALYST)).rewards(
                        AdvancementRewards.Builder.recipe(recipie("scythe")))
                .build(consumer,of("catalyst"));
        Advancement.Builder.create().criterion(
                        "get_stone",
                        InventoryChangedCriterion.Conditions.items(Blocks.END_STONE)).rewards(
                        AdvancementRewards.Builder.recipe(recipie("enchantment_duplicator")))
                .build(consumer,of("catylst"));

    }

    public static AdvancementRewards advancementRewards(String... recipies){
        AdvancementRewards.Builder builder = new AdvancementRewards.Builder();
        Arrays.stream(recipies).forEach(s -> builder.addRecipe(recipie(s)));
        return builder.build();
    }
    public static void createRecipeAdvancement(
            Consumer<AdvancementEntry> consumer,
            String name,
            AdvancementCriterion<?> criterion,
            String... recipies){
        Advancement.Builder.create()
                .criterion(name + "_criteria", criterion)
                .rewards(advancementRewards(recipies))
                .build(consumer, of(name));
    }

    public static RegistryKey<Recipe<?>> recipie(String id){
        return RegistryKey.of(RegistryKeys.RECIPE,Identifier.of(TheSculkDimension.MOD_ID,id));
    }


    public static String of(String id) {
        return TheSculkDimension.MOD_ID + ":" + id;

    }
}
