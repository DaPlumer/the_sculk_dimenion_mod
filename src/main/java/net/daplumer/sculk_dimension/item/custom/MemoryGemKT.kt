package net.daplumer.sculk_dimension.item.custom
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
class MemoryGemKT(settings: Settings): Item(settings) {

    override fun hasGlint(stack: ItemStack?): Boolean {
        return true
    }
}