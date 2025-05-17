@file:Suppress("unused", "FunctionName")

package net.daplumer.data_modification_utils.mod_registries.registering_functions

import net.daplumer.data_modification_utils.mixin.BlockSetTypeMixin
import net.daplumer.data_modification_utils.mixin.WoodTypeMixin
import net.minecraft.block.*

fun BUTTON(type: BlockSetType, ticks: Int = 30): (AbstractBlock.Settings) -> Block = {settings -> ButtonBlock(type,ticks,settings) }
fun STAIRS(baseBlockState:BlockState): (AbstractBlock.Settings) -> Block = {settings -> StairsBlock(baseBlockState,settings) }
fun DOOR(type:BlockSetType): (AbstractBlock.Settings) -> Block = {settings -> DoorBlock(type, settings)}
fun TRAPDOOR(type:BlockSetType): (AbstractBlock.Settings) -> Block = {settings -> TrapdoorBlock(type, settings)}
fun FENCE_GATE(type:WoodType): (AbstractBlock.Settings) -> Block = {settings -> FenceGateBlock(type, settings)}
fun PRESSURE_PLATE(type: BlockSetType):(AbstractBlock.Settings) -> Block = {settings -> PressurePlateBlock(type,settings)}
fun SIGN(type:WoodType):(AbstractBlock.Settings) -> Block = {settings ->  SignBlock(type,settings)}
fun WALL_SIGN(type:WoodType):(AbstractBlock.Settings) -> Block = {settings ->  WallSignBlock(type,settings)}
fun HANGING_SIGN(type:WoodType):(AbstractBlock.Settings) -> Block = {settings ->  HangingSignBlock(type,settings)}
fun WALL_HANGING_SIGN(type:WoodType):(AbstractBlock.Settings) -> Block = {settings ->  WallHangingSignBlock(type,settings) }

fun registerBlockSetType(type: BlockSetType, key:String):BlockSetType {
    BlockSetTypeMixin.getValues()[key] = type;
    return type
}
fun registerWoodType(type: WoodType, key:String):WoodType {
    WoodTypeMixin.getValues()[key] = type;
    return type;
}

fun copyLootTable(block: Block, copyTranslationKey: Boolean): AbstractBlock.Settings {
    var settings2 = AbstractBlock.Settings.create().lootTable(block.lootTableKey)
    if (copyTranslationKey) {
        settings2 = settings2.overrideTranslationKey(block.translationKey)
    }

    return settings2
}