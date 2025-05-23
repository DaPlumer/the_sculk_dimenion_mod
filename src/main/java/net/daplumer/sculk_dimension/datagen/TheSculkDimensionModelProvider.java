package net.daplumer.sculk_dimension.datagen;

import net.daplumer.sculk_dimension.TheSculkDimension;
import net.daplumer.sculk_dimension.block.ModBlocks;
import net.daplumer.sculk_dimension.component.ModDataComponentTypes;
import net.daplumer.sculk_dimension.item.ModItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.*;
import net.minecraft.client.render.item.model.SelectItemModel;
import net.minecraft.client.render.item.property.select.ComponentSelectProperty;
import net.minecraft.util.Identifier;

import java.util.ArrayList;

import static net.daplumer.sculk_dimension.block.InfectedBlocks.infected;
import static net.daplumer.sculk_dimension.block.ModBlocks.MEMORY_GEM_BLOCK;

public class TheSculkDimensionModelProvider extends FabricModelProvider {
    public TheSculkDimensionModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleCubeAll(MEMORY_GEM_BLOCK);
        infected.generateModels(blockStateModelGenerator);
    }
    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        ArrayList<Identifier> identifiers = new ArrayList<>(100);
        ArrayList<SelectItemModel.SwitchCase<Integer>> models = new ArrayList<>(1001);
        for (int i = 0; i <= 100; i++) {
            identifiers.add(Models.GENERATED.upload(Identifier.of(TheSculkDimension.MOD_ID,"item/insanity/echo_"+i), TextureMap.layer0(Identifier.of(TheSculkDimension.MOD_ID, "item/echoes/echo_"+i)), itemModelGenerator.modelCollector));
        }
        for (int i = 0; i <= 1000; i++) {
            models.add(ItemModels.switchCase(i,ItemModels.basic(identifiers.get(Math.floorDiv(i,10)))));
        }
        itemModelGenerator.output.accept(ModItems.BROKEN_ECHO,ItemModels.select(new ComponentSelectProperty<>(ModDataComponentTypes.INSANITY),ItemModels.basic(identifiers.getFirst()),models));
        itemModelGenerator.register(ModItems.SCULK_CLOTH,Models.GENERATED);
        itemModelGenerator.register(ModBlocks.ECHOING_BLOOM_ITEM,Models.GENERATED);
        itemModelGenerator.register(ModItems.INFECTED_BOAT,Models.GENERATED);
        itemModelGenerator.register(ModItems.INFECTED_CHEST_BOAT,Models.GENERATED);
        itemModelGenerator.register(ModItems.RESONATION_HELMET,Models.GENERATED);
    }
}
