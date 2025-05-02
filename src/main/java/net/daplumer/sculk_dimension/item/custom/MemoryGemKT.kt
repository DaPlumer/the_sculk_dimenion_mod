package net.daplumer.sculk_dimension.item.custom

import net.daplumer.sculk_dimension.TheSculkDimension
import net.minecraft.component.ComponentMap
import net.minecraft.component.DataComponentTypes
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.Identifier
import kotlin.random.Random

class MemoryGemKT(settings: Settings): Item(settings) {
    private val rand:Random = Random(Identifier.of(TheSculkDimension.MOD_ID,"memory_gem").hashCode())
    override fun getComponents(): ComponentMap {
        return ComponentMap.builder().addAll(super.getComponents()).add(DataComponentTypes.DAMAGE,rand.nextInt(128,192)).build()
    }

    override fun hasGlint(stack: ItemStack?): Boolean {
        return true
    }
}