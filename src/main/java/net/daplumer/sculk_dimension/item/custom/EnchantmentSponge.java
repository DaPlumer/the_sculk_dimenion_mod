package net.daplumer.sculk_dimension.item.custom;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.ClickType;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class EnchantmentSponge extends Item {
    public EnchantmentSponge(Settings settings) {
        super(settings);
    }

    @Override
    public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player) {
        if(clickType == ClickType.LEFT) return false;
        if(stack.get(DataComponentTypes.STORED_ENCHANTMENTS) != null) {
            //noinspection DataFlowIssue
            if (!(stack.getComponents().get(DataComponentTypes.STORED_ENCHANTMENTS)).isEmpty()) return false;
        }
        ItemStack otherStack = slot.getStack();
        if(otherStack.isEmpty()) return false;

        Optional<RegistryEntry<Enchantment>> OptionalEnchantmentToBeRemoved = getRandomEnchantment(otherStack);
        if(OptionalEnchantmentToBeRemoved.isEmpty()) return false;
        RegistryEntry<Enchantment> enchantmentToBeRemoved = OptionalEnchantmentToBeRemoved.get();




        ItemEnchantmentsComponent otherEnchants = Objects.requireNonNullElse(otherStack.getEnchantments(), ItemEnchantmentsComponent.DEFAULT);
        ItemEnchantmentsComponent otherStoredEnchants = Objects.requireNonNullElse(otherStack.get(DataComponentTypes.STORED_ENCHANTMENTS), ItemEnchantmentsComponent.DEFAULT);

        int removedEnchantmentLevel = Math.max(otherEnchants.getLevel(enchantmentToBeRemoved), otherStoredEnchants.getLevel(enchantmentToBeRemoved));
        ItemEnchantmentsComponent.Builder newEnchantmentBuilder = new ItemEnchantmentsComponent.Builder(ItemEnchantmentsComponent.DEFAULT);
        newEnchantmentBuilder.add(enchantmentToBeRemoved,removedEnchantmentLevel);


        ItemEnchantmentsComponent.Builder otherEnchantsBuilder = new ItemEnchantmentsComponent.Builder(otherEnchants);
        ItemEnchantmentsComponent.Builder otherStoredEnchantsBuilder = new ItemEnchantmentsComponent.Builder(otherStoredEnchants);
        otherEnchantsBuilder.remove((enchantment) -> enchantment == enchantmentToBeRemoved);
        otherStoredEnchantsBuilder.remove((enchantment) -> enchantment == enchantmentToBeRemoved);
        otherStack.set(DataComponentTypes.ENCHANTMENTS, otherEnchantsBuilder.build());
        otherStack.set(DataComponentTypes.STORED_ENCHANTMENTS, otherStoredEnchantsBuilder.build());
        otherStack.getItem().postProcessComponents(otherStack);
        stack.set(DataComponentTypes.STORED_ENCHANTMENTS, newEnchantmentBuilder.build());


        if (otherStack.isOf(Items.ENCHANTED_BOOK) && otherEnchants.getSize() == 0) slot.setStack(otherStack.withItem(Items.BOOK));

        return true;
    }

    private Optional<RegistryEntry<Enchantment>> getRandomEnchantment(ItemStack itemStack){
        ItemEnchantmentsComponent itemEnchants = itemStack.getEnchantments();
        ItemEnchantmentsComponent storedEnchants = itemStack.get(DataComponentTypes.STORED_ENCHANTMENTS);
        itemEnchants = Objects.requireNonNullElse(itemEnchants, ItemEnchantmentsComponent.DEFAULT);
        storedEnchants = Objects.requireNonNullElse(storedEnchants, ItemEnchantmentsComponent.DEFAULT);
        Stream<RegistryEntry<Enchantment>> enchantmentsSet = Stream.concat(itemEnchants.getEnchantments().stream(), storedEnchants.getEnchantments().stream());
        return enchantmentsSet.findAny();
    }


}
