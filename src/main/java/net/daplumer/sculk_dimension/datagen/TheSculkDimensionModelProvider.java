package net.daplumer.sculk_dimension.datagen;

import net.daplumer.sculk_dimension.TheSculkDimension;
import net.daplumer.sculk_dimension.component.ModDataComponentTypes;
import net.daplumer.sculk_dimension.item.ModItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.*;
import net.minecraft.client.render.item.model.SelectItemModel;
import net.minecraft.client.render.item.property.select.ComponentSelectProperty;
import net.minecraft.util.Identifier;

import java.util.ArrayList;

public class TheSculkDimensionModelProvider extends FabricModelProvider {
    public TheSculkDimensionModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

    }
    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        ArrayList<Identifier> identifiers = new ArrayList<>(101);
        ArrayList<SelectItemModel.SwitchCase<Integer>> models = new ArrayList<>(101);

        for (int i = 0; i <= 100; i++) {
            identifiers.add(Models.GENERATED.upload(Identifier.of(TheSculkDimension.MOD_ID,"item/insanity/echo_"+i), TextureMap.layer0(Identifier.of(TheSculkDimension.MOD_ID, "item/echoes/echo_"+i)), itemModelGenerator.modelCollector));
            models.add(ItemModels.switchCase(i,ItemModels.basic(identifiers.get(i))));
        }
        itemModelGenerator.output.accept(ModItems.BROKEN_ECHO,ItemModels.select(new ComponentSelectProperty<>(ModDataComponentTypes.INSANITY),ItemModels.basic(identifiers.getFirst()),models));
        itemModelGenerator.register(ModItems.SCULK_CLOTH,Models.GENERATED);
    }
}
