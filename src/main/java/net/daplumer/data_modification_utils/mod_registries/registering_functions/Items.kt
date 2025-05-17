package net.daplumer.data_modification_utils.mod_registries.registering_functions

import net.minecraft.block.Block
import net.minecraft.entity.EntityType
import net.minecraft.entity.vehicle.AbstractBoatEntity
import net.minecraft.item.BoatItem
import net.minecraft.item.HangingSignItem
import net.minecraft.item.Item
import net.minecraft.item.SignItem

fun BOAT(type:EntityType<out AbstractBoatEntity>?) = { settings: Item.Settings -> BoatItem(type,settings)}
fun SIGN(normal:Block,wall:Block) = {settings:Item.Settings -> SignItem(normal,wall,settings)}
fun HANGING_SIGN(normal:Block,wall:Block) = {settings:Item.Settings -> HangingSignItem(normal,wall,settings) }