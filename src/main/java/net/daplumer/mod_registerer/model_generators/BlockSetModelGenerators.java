package net.daplumer.mod_registerer.model_generators;

import net.daplumer.mod_registerer.utils.BlockSetCreator;
import net.minecraft.block.Block;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.Models;
import net.minecraft.client.data.TextureMap;
import net.minecraft.client.data.TexturedModel;
import net.minecraft.util.Identifier;

public class BlockSetModelGenerators {
    public static void registerStairsModel(Block stairs, TextureMap texture, BlockStateModelGenerator blockStateModelGenerator){
        final Identifier stairsModelID = Models.STAIRS.upload(stairs,texture, blockStateModelGenerator.modelCollector);
        final Identifier innerStairsModelID = Models.INNER_STAIRS.upload(stairs,texture, blockStateModelGenerator.modelCollector);
        final Identifier outerStairsModelID = Models.OUTER_STAIRS.upload(stairs,texture, blockStateModelGenerator.modelCollector);
        blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createStairsBlockState(stairs,
                BlockStateModelGenerator.createWeightedVariant(innerStairsModelID),
                BlockStateModelGenerator.createWeightedVariant(stairsModelID),
                BlockStateModelGenerator.createWeightedVariant(outerStairsModelID)));
        blockStateModelGenerator.registerParentedItemModel(stairs,stairsModelID);
    }
    public static void registerSlabModel(Block slab,Identifier fullBlockIdentifier , TextureMap texture, BlockStateModelGenerator modelGenerator){
        final Identifier bottomId = Models.SLAB.upload(slab,texture,modelGenerator.modelCollector);
        final Identifier topId = Models.SLAB_TOP.upload(slab,texture,modelGenerator.modelCollector);

        modelGenerator.blockStateCollector.accept(
                BlockStateModelGenerator.createSlabBlockState(slab,
                        BlockStateModelGenerator.createWeightedVariant(bottomId),
                        BlockStateModelGenerator.createWeightedVariant(topId),
                        BlockStateModelGenerator.createWeightedVariant(fullBlockIdentifier)
                )
        );
        modelGenerator.registerParentedItemModel(slab,bottomId);
    }
    public static void registerButtonModel(Block button, TextureMap texture, BlockStateModelGenerator modelGenerator){
        final Identifier unPressed = Models.BUTTON.upload(button,texture,modelGenerator.modelCollector);
        final Identifier pressed = Models.BUTTON_PRESSED.upload(button,texture,modelGenerator.modelCollector);
        modelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createButtonBlockState(
                button,BlockStateModelGenerator.createWeightedVariant(unPressed),
                BlockStateModelGenerator.createWeightedVariant(pressed)
        ));
        modelGenerator.registerParentedItemModel(button,unPressed);
    }

    public static void registerPressurePlateModel(Block plate, TextureMap texture, BlockStateModelGenerator modelGenerator){
        final Identifier unPressed = Models.PRESSURE_PLATE_UP.upload(plate,texture,modelGenerator.modelCollector);
        final Identifier pressed = Models.PRESSURE_PLATE_DOWN.upload(plate,texture,modelGenerator.modelCollector);
        modelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createPressurePlateBlockState(
                plate,
                BlockStateModelGenerator.createWeightedVariant(unPressed),
                BlockStateModelGenerator.createWeightedVariant(pressed)
        ));
        modelGenerator.registerParentedItemModel(plate,unPressed);

    }
    public static void registerFenceModel(Block fence,TextureMap texture, BlockStateModelGenerator modelGenerator){
        final Identifier post = Models.FENCE_POST.upload(fence,texture,modelGenerator.modelCollector);
        final Identifier side = Models.FENCE_SIDE.upload(fence,texture,modelGenerator.modelCollector);
        modelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createFenceBlockState(
                fence,
                BlockStateModelGenerator.createWeightedVariant(post),
                BlockStateModelGenerator.createWeightedVariant(side)

        ));
        modelGenerator.registerParentedItemModel(fence,Models.FENCE_INVENTORY.upload(fence,texture,modelGenerator.modelCollector));
    }
    public static void registerFenceGateModel(Block gate, TextureMap texture, BlockStateModelGenerator modelGenerator){
        final Identifier open = Models.TEMPLATE_FENCE_GATE_OPEN.upload(gate,texture,modelGenerator.modelCollector);
        final Identifier closed = Models.TEMPLATE_FENCE_GATE.upload(gate,texture,modelGenerator.modelCollector);
        final Identifier openWall = Models.TEMPLATE_FENCE_GATE_WALL_OPEN.upload(gate,texture,modelGenerator.modelCollector);
        final Identifier closedWall = Models.TEMPLATE_FENCE_GATE_WALL.upload(gate,texture,modelGenerator.modelCollector);
        modelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createFenceGateBlockState(
                gate,
                BlockStateModelGenerator.createWeightedVariant(open),
                BlockStateModelGenerator.createWeightedVariant(closed),
                BlockStateModelGenerator.createWeightedVariant(openWall),
                BlockStateModelGenerator.createWeightedVariant(closedWall),
                true
        ));
    }
    public static void registerPlankTextureModels(BlockSetCreator.FullBlockSet set, TextureMap texture, BlockStateModelGenerator modelGenerator){
        Identifier planks = Models.CUBE_ALL.upload(set.planks(),texture, modelGenerator.modelCollector);
        modelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(
                set.planks(),
                BlockStateModelGenerator.createWeightedVariant(planks)));
        registerPressurePlateModel(set.pressurePlate(),texture,modelGenerator);
        registerButtonModel(set.button(),texture,modelGenerator);
        registerSlabModel(set.slab(),planks,texture,modelGenerator);
        registerStairsModel(set.stairs(),texture,modelGenerator);
        registerFenceGateModel(set.fenceGate(),texture,modelGenerator);
        registerFenceModel(set.fence(),texture,modelGenerator);
    }

    public static record woodTextures(TextureMap planks,
                                      TextureMap doorTop,TextureMap doorBottom,TextureMap trapDoor,
                                      TextureMap logSide,TextureMap logTop,
                                      TextureMap logSideStripped, TextureMap logTopStripped,
                                      TextureMap hangingSignGUI){}

    public static void registerNonPlankTextureModels(BlockSetCreator.FullBlockSet set, woodTextures Textures, BlockStateModelGenerator modelGenerator){

    }
    public static void registerLog(Block log, Identifier topTexture, Identifier sideTexture,BlockStateModelGenerator modelGenerator){
        final Identifier top = Models.CUBE_COLUMN.upload(log,TextureMap.sideEnd(sideTexture,topTexture),modelGenerator.modelCollector);
        modelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createAxisRotatedBlockState(log,BlockStateModelGenerator.createWeightedVariant(top)));
        modelGenerator.registerParentedItemModel(log,top);
    }
}
