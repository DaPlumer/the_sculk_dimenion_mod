package net.daplumer.data_modification_utils.block_set_generation.wood;

import net.daplumer.data_modification_utils.block_set_generation.Shift;
import net.daplumer.data_modification_utils.mod_registries.ModDataRegisterer;
import net.daplumer.sculk_dimension.util.SculkIdentifier;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.WoodType;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.ModelSupplier;
import net.minecraft.client.data.Models;
import net.minecraft.client.data.TextureMap;
import net.minecraft.item.ItemConvertible;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.BiConsumer;

@SuppressWarnings("unused")
public class WoodBlockGroup {
    private static final Logger log = LoggerFactory.getLogger(WoodBlockGroup.class);
    private final String name;
    private final DoorBlocks doors;
    private final LogGroup logs;
    private final PlankBasedBlocks planks;
    private final String namespace;

    private WoodBlockGroup(String name, DoorBlocks doors, LogGroup logs, PlankBasedBlocks planks, String namespace) {
        this.name = name;
        this.doors = doors;
        this.logs = logs;
        this.planks = planks;
        this.namespace = namespace;
    }
    public DoorBlocks getDoors() {return doors;}
    public LogGroup getLogs() {return logs;}
    public PlankBasedBlocks getPlanks() {return planks;}
    public void registerBlockTags(FabricTagProvider.BlockTagProvider blockTagProvider) {
        doors.registerBlockTags(blockTagProvider);
        logs.registerBlockTags(blockTagProvider);
        planks.registerBlockTags(blockTagProvider);
    }
    public void registerItemTags(FabricTagProvider.ItemTagProvider itemTagProvider) {
        doors.registerItemTags(itemTagProvider);
        logs.registerItemTags(itemTagProvider);
        planks.registerItemTags(itemTagProvider);
    }
    public static WoodBlockGroup of(DoorBlocks doors, LogGroup logs, PlankBasedBlocks planks, String name, String namespace) {
        return new WoodBlockGroup(name, doors,logs,planks,namespace);
    }

    public void addToItemGroups(FabricItemGroupEntries entries, Shift shift, ItemConvertible item) {
        logs.addToItemGroups(entries, shift, item);
        planks.addToItemGroups(entries, Shift.AFTER, logs.getStrippedWood());
        planks.addToItemGroups(entries, Shift.BEFORE, planks.pressurePlate());
    }
    public static WoodBlockGroup of(ModDataRegisterer<Block, AbstractBlock.Settings,?> BLOCKS, WoodType type, MapColor lightColor, MapColor mediumColor, MapColor darkColor, Boolean burnable, BlockSoundGroup soundGroup){
        return WoodBlockGroup.of(
                DoorBlocks.of(BLOCKS,lightColor,type,burnable),
                LogGroup.of(BLOCKS,type.name(),lightColor,mediumColor,darkColor,burnable,type.soundType()),
                PlankBasedBlocks.of(BLOCKS,lightColor,burnable,type.soundType(),type),type.name(),
        BLOCKS.getNamespace());
    }
    public static WoodBlockGroup of(ModDataRegisterer<Block ,AbstractBlock.Settings, ?> BLOCKS, String name, Boolean burnable,
                                    MapColor lightColor, MapColor mediumColor, MapColor darkColor, WoodTypeSounds soundType){
        return WoodBlockGroup.of(BLOCKS, WoodTypeCreator.createWoodType(name,soundType), lightColor,darkColor,mediumColor,burnable, soundType.defaultSounds());
    }




    public void generateModels(BlockStateModelGenerator modelGenerator){
        //door blocks first
        modelGenerator.registerDoor(doors.door());
        modelGenerator.registerOrientableTrapdoor(doors.trapdoor());
        // now log models
        makeLogModel(TextureMap.sideEnd(SFID("_log"), SFID("_log_top")),modelGenerator,logs.getLog());//log
        makeLogModel(TextureMap.sideEnd(prefix(SFID("_log"), "stripped_"),prefix(SFID("_log_top"), "stripped_")),modelGenerator,logs.getStrippedLog());
        makeLogModel(TextureMap.all(SFID("_log")),modelGenerator,logs.getWood());
        makeLogModel(TextureMap.all(prefix(SFID("_log"),"stripped_")),modelGenerator,logs.getStrippedWood());
        // plank based models

        final TextureMap planksTexture = TextureMap.all(SFID("_planks"));
        modelGenerator.registerSimpleCubeAll(planks.planks());
        final Identifier stairsModelId = Models.STAIRS.upload(planks.stairs(), planksTexture, modelGenerator.modelCollector);
        final Identifier innerStairsModelId = Models.INNER_STAIRS.upload(planks.stairs(), planksTexture, modelGenerator.modelCollector);
        final Identifier outerStairsModelId = Models.OUTER_STAIRS.upload(planks.stairs(), planksTexture, modelGenerator.modelCollector);
        modelGenerator.blockStateCollector.accept(
                BlockStateModelGenerator.createStairsBlockState(planks.stairs(),
                        BlockStateModelGenerator.createWeightedVariant(innerStairsModelId),
                        BlockStateModelGenerator.createWeightedVariant(stairsModelId),
                        BlockStateModelGenerator.createWeightedVariant(outerStairsModelId)));
        modelGenerator.registerParentedItemModel(planks.stairs(), stairsModelId);

        final Identifier slabBottomModelId = Models.SLAB.upload(planks.slab(), planksTexture, modelGenerator.modelCollector);
        final Identifier slabTopModelId = Models.SLAB_TOP.upload(planks.slab(), planksTexture, modelGenerator.modelCollector);
        modelGenerator.blockStateCollector.accept(
                BlockStateModelGenerator.createSlabBlockState(planks.slab(),
                        BlockStateModelGenerator.createWeightedVariant(slabBottomModelId),
                        BlockStateModelGenerator.createWeightedVariant(slabTopModelId),
                        BlockStateModelGenerator.createWeightedVariant(SculkIdentifier.of("block/infected_planks")))
        );
        modelGenerator.registerParentedItemModel(planks.slab(), slabBottomModelId);
        final BiConsumer<Identifier, ModelSupplier> col = modelGenerator.modelCollector;
        final Identifier openFenceGate = Models.TEMPLATE_FENCE_GATE_OPEN.upload(planks.fenceGate(), planksTexture, col);
        final Identifier openFenceGateWall = Models.TEMPLATE_FENCE_GATE_WALL_OPEN.upload(planks.fenceGate(), planksTexture, col);
        final Identifier FenceGate = Models.TEMPLATE_FENCE_GATE.upload(planks.fenceGate(), planksTexture, col);
        final Identifier FenceGateWall = Models.TEMPLATE_FENCE_GATE_WALL.upload(planks.fenceGate(), planksTexture, col);
        modelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createFenceGateBlockState(planks.fenceGate(),
                BlockStateModelGenerator.createWeightedVariant(openFenceGate),
                BlockStateModelGenerator.createWeightedVariant(FenceGate),
                BlockStateModelGenerator.createWeightedVariant(openFenceGateWall),
                BlockStateModelGenerator.createWeightedVariant(FenceGateWall),
                true)
        );
        modelGenerator.registerParentedItemModel(planks.fenceGate(),FenceGate);
        final Identifier fencePost = Models.FENCE_POST.upload(planks.fence(), planksTexture, col);
        final Identifier fenceSide = Models.FENCE_SIDE.upload(planks.fence(), planksTexture, col);
        final Identifier fenceInventory = Models.FENCE_INVENTORY.upload(planks.fence(), planksTexture, col);
        modelGenerator.blockStateCollector.accept(
                BlockStateModelGenerator.createFenceBlockState(planks.fence(),
                        BlockStateModelGenerator.createWeightedVariant(fencePost),
                        BlockStateModelGenerator.createWeightedVariant(fenceSide))
        );
        modelGenerator.registerParentedItemModel(planks.fence(),fenceInventory);
        final Identifier plate = Models.PRESSURE_PLATE_UP.upload(planks.pressurePlate(), planksTexture, col);
        final Identifier plateDown = Models.PRESSURE_PLATE_DOWN.upload(planks.pressurePlate(), planksTexture, col);
        modelGenerator.blockStateCollector.accept(
                BlockStateModelGenerator.createPressurePlateBlockState(planks.pressurePlate(),
                        BlockStateModelGenerator.createWeightedVariant(plate),
                        BlockStateModelGenerator.createWeightedVariant(plateDown))
        );
        modelGenerator.registerParentedItemModel(planks.pressurePlate(),plate);
        final Identifier button = Models.BUTTON.upload(planks.button(), planksTexture, col);
        final Identifier buttonPressed = Models.BUTTON_PRESSED.upload(planks.button(), planksTexture, col);
        final Identifier buttonInventory = Models.BUTTON_INVENTORY.upload(planks.button(), planksTexture, col);
        modelGenerator.blockStateCollector.accept(
                BlockStateModelGenerator.createButtonBlockState(planks.button(),
                        BlockStateModelGenerator.createWeightedVariant(button),
                        BlockStateModelGenerator.createWeightedVariant(buttonPressed))
        );
        modelGenerator.registerParentedItemModel(planks.button(),buttonInventory);

    }
    //suffixed identifier
    private Identifier SFID(String suffix){
        return Identifier.of(this.namespace,"block/"+this.name+suffix);
    }
    private static Identifier prefix(Identifier id,String prefix){
        return Identifier.of(id.getNamespace(),prefix+id.getPath());
    }

    private static void makeLogModel(TextureMap map, BlockStateModelGenerator modelGenerator, Block log){
        final Identifier logModel = Models.CUBE_COLUMN.upload(log, map,modelGenerator.modelCollector);
        modelGenerator.blockStateCollector.accept(
                BlockStateModelGenerator.createAxisRotatedBlockState(log,
                        BlockStateModelGenerator.createWeightedVariant(logModel))
        );
        modelGenerator.registerParentedItemModel(log, logModel);
    }


}
