package net.daplumer.sculk_dimension.rei;

import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.display.DisplaySerializer;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.daplumer.sculk_dimension.block.custom.EnchantmentDuplicationScreenHandler;
import net.daplumer.sculk_dimension.util.statistics.SoulHolder;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.codec.PacketCodec;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DuplicateEnchantDisplay extends BasicDisplay {
    public static final DisplaySerializer<DuplicateEnchantDisplay> SERIALIZER = DisplaySerializer.of(
            RecordCodecBuilder.mapCodec((RecordCodecBuilder.Instance<DuplicateEnchantDisplay> instance)->instance.group(
                    EntryIngredient.codec().fieldOf("souls").forGetter(DuplicateEnchantDisplay::getSouls),
                    EntryIngredient.codec().fieldOf("enchants").forGetter(DuplicateEnchantDisplay::getEnchantHolder),
                    EntryIngredient.codec().fieldOf("output").forGetter(DuplicateEnchantDisplay::getOut)
            ).apply(instance, DuplicateEnchantDisplay::new)),
            PacketCodec.tuple(
                    EntryIngredient.streamCodec(),
                    DuplicateEnchantDisplay::getSouls,
                    EntryIngredient.streamCodec(),
                    DuplicateEnchantDisplay::getEnchantHolder,
                    EntryIngredient.streamCodec(),
                    DuplicateEnchantDisplay::getOut,
                    DuplicateEnchantDisplay::new
            ));
    public DuplicateEnchantDisplay(EntryIngredient soulHolder, EntryIngredient enchantHolder, EntryIngredient output){
        super(List.of(EntryIngredients.of(Items.BOOK), soulHolder, enchantHolder), List.of(output));
    }
    public static @Nullable DuplicateEnchantDisplay of(Item soulsItem, ItemStack enchantment){
        ItemStack souls = soulsItem.getDefaultStack();
        ItemStack result = Items.ENCHANTED_BOOK.getDefaultStack();
        result.set(DataComponentTypes.STORED_ENCHANTMENTS, enchantment.get(DataComponentTypes.STORED_ENCHANTMENTS));
        SoulHolder.setSouls(souls, EnchantmentDuplicationScreenHandler.requiredSouls(enchantment));
        if (SoulHolder.getSouls(souls) > SoulHolder.maxSouls(souls)) return null;
        return new DuplicateEnchantDisplay(EntryIngredients.of(souls), EntryIngredients.of(enchantment), EntryIngredients.of(result));
    }



    public final EntryIngredient getBook(){return getInputEntries().getFirst();}
    public final EntryIngredient getSouls(){return getInputEntries().get(1);}
    public final EntryIngredient getEnchantHolder(){return getInputEntries().get(2);}
    public final EntryIngredient getOut(){return getOutputEntries().getFirst();}

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return TheSculkDimensionREIClient.DUPLICATE_ENCHANTS;
    }
    @Override
    public @Nullable DisplaySerializer<? extends Display> getSerializer() {
        return SERIALIZER;
    }
}
