package net.daplumer.sculk_dimension.item.custom

import net.daplumer.sculk_dimension.TheSculkDimension
import net.minecraft.component.ComponentMap
import net.minecraft.component.DataComponentTypes
import net.minecraft.enchantment.Enchantments
import net.minecraft.entity.Entity
import net.minecraft.entity.EquipmentSlot
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.registry.RegistryKeys
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.Identifier
import kotlin.random.Random

class MemoryGemKT(settings: Settings): Item(settings) {
    private val rand:Random = Random(Identifier.of(TheSculkDimension.MOD_ID,"memory_gem").hashCode())
    override fun getComponents(): ComponentMap {
        return ComponentMap.builder().addAll(super.getComponents()).add(DataComponentTypes.DAMAGE,rand.nextInt(128,192)).build()
    }

    override fun inventoryTick(stack: ItemStack?, world: ServerWorld?, entity: Entity?, slot: EquipmentSlot?) {
        super.inventoryTick(stack, world, entity, slot)
        stack!!.addEnchantment(
            world!!.registryManager.getOrThrow(RegistryKeys.ENCHANTMENT).getEntry(
                world.registryManager.getOrThrow(RegistryKeys.ENCHANTMENT)[Enchantments.MENDING]
            ), 1)
    }
}