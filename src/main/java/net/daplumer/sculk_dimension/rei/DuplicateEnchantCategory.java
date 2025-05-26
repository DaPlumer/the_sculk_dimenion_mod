package net.daplumer.sculk_dimension.rei;

import com.google.common.collect.Lists;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.daplumer.sculk_dimension.block.ModBlocks;
import net.minecraft.text.Text;

import java.util.List;

public class DuplicateEnchantCategory implements DisplayCategory<DuplicateEnchantDisplay> {
    @Override
    public CategoryIdentifier<? extends DuplicateEnchantDisplay> getCategoryIdentifier() {
        return TheSculkDimensionREIClient.DUPLICATE_ENCHANTS;
    }

    @Override
    public Text getTitle() {
        return Text.translatable("category.rei.duplicate_enchants");
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModBlocks.ENCHANTMENT_DUPLICATOR);
    }

    @Override
    public int getDisplayHeight() {
        return 56;
    }

    @Override
    public List<Widget> setupDisplay(DuplicateEnchantDisplay display, Rectangle bounds) {
        Point startPoint = new Point(bounds.getCenterX() - 41, bounds.getCenterY() - 13);
        List<Widget> widgets = Lists.newArrayList();
        widgets.add(Widgets.createRecipeBase(bounds));
        widgets.add(Widgets.createArrow(new Point(startPoint.x + 27, startPoint.y + 4)));
        widgets.add(Widgets.createResultSlotBackground(new Point(startPoint.x + 61, startPoint.y + 5)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x - 17, startPoint.y - 6)).entries(display.getBook()).markInput());
        widgets.add(Widgets.createSlot(new Point(startPoint.x +  3, startPoint.y - 6)).entries(display.getSouls()).markInput());
        widgets.add(Widgets.createSlot(new Point(startPoint.x -  7, startPoint.y + 14)).entries(display.getEnchantHolder()).markInput());
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 61, startPoint.y + 5)).entries(display.getOut()).disableBackground().markOutput());
        return widgets;
    }
}
