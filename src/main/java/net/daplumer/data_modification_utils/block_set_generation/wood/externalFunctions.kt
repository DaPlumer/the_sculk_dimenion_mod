package net.daplumer.data_modification_utils.block_set_generation.wood

import net.minecraft.data.recipe.RecipeGenerator

fun RecipeGenerator.addLogRecipes(group:LogGroup){
    this.offerBarkBlockRecipe(group.strippedLog,group.log)
    this.offerBarkBlockRecipe(group.strippedWood,group.wood)
}

