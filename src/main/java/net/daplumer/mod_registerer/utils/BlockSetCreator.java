package net.daplumer.mod_registerer.utils;

import net.daplumer.mod_registerer.mod_registries.GeneralModDataRegisterer;
import net.daplumer.mod_registerer.mod_registries.ModDataRegisterer;
import net.daplumer.mod_registerer.mod_registries.ModRegistries;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.SignItem;
import net.minecraft.registry.Registries;
import net.minecraft.sound.BlockSoundGroup;

import java.util.List;

import static net.daplumer.mod_registerer.mod_registries.Registerer.*;
import static net.minecraft.block.enums.NoteBlockInstrument.BASS;

public class BlockSetCreator {

    private static AbstractBlock.Settings copyLootTable(Block block, boolean copyTranslationKey) {
        AbstractBlock.Settings settings = block.getSettings();
        AbstractBlock.Settings settings2 = AbstractBlock.Settings.create().lootTable(block.getLootTableKey());
        if (copyTranslationKey) {
            settings2 = settings2.overrideTranslationKey(block.getTranslationKey());
        }

        return settings2;
    }
    public static FullBlockSet registerFullWoodSet(
            String namespace,
            BlockSetType setType,
            WoodType woodType,
            MapColor lightShade,
            MapColor mediumShade,
            MapColor darkShade,
            boolean burnable
    ){
        GeneralModDataRegisterer<Block, AbstractBlock.Settings> BLOCKS =
                new GeneralModDataRegisterer<>(Registries.BLOCK, namespace, (block, key) ->
                        burnable?block.registryKey(key).burnable():block.registryKey(key), Block::new, AbstractBlock.Settings::create);
        ModDataRegisterer<Item,Item.Settings> ITEMS = ModRegistries.ITEM_REGISTERER_CONSTRUCTOR.apply(namespace);
        BlockSoundGroup soundGroup = setType.soundType();
        String name = setType.name();
        Block planks = BLOCKS.register(name + "_planks", AbstractBlock.Settings.create()
                .mapColor(lightShade)
                .instrument(BASS)
                .strength(2.0F,3.0F)
                .sounds(setType.soundType())
        );
        Block log = BLOCKS.register(name + "_log", Blocks.createLogSettings(lightShade,darkShade,soundGroup), PillarBlock::new);
        Block strippedLog = BLOCKS.register(name + "_stripped_log", Blocks.createLogSettings(lightShade,mediumShade,soundGroup), PillarBlock::new);
        Block wood = BLOCKS.register(name + "_wood", AbstractBlock.Settings.create().mapColor(darkShade).instrument(BASS)
                .strength(2.0F)
                .sounds(soundGroup), PillarBlock::new);
        Block strippedWood = BLOCKS.register("stripped_"+name+"_wood",
                AbstractBlock.Settings.create().mapColor(mediumShade)
                        .instrument(BASS)
                        .strength(2.0F)
                        .sounds(soundGroup),
                PillarBlock::new);
        Block sign = BLOCKS.register(name + "_sign",
                AbstractBlock.Settings.create()
                    .mapColor(lightShade)
                    .solid()
                    .instrument(BASS)
                    .noCollision()
                    .strength(1.0F)
                ,SIGN_BLOCK(woodType)
        );
        Block wallSign = BLOCKS.register(name + "_wall_sign",
                copyLootTable(sign, true)
                        .mapColor(lightShade)
                        .solid()
                        .instrument(BASS)
                        .noCollision()
                        .strength(1.0F),
                WALL_SIGN_BLOCK(woodType)
        );
        Block hangingSign = BLOCKS.register(name + "_hanging_sign",
                AbstractBlock.Settings.create()
                        .mapColor(mediumShade)
                        .strength(1.0F)
                        .solid()
                        .instrument(BASS),
                HANGING_SIGN_BLOCK(woodType)
        );
        Block wallHangingSign = BLOCKS.register(name + "_wall_hanging_sign",
                copyLootTable(hangingSign, true)
                        .mapColor(mediumShade)
                        .solid()
                        .instrument(BASS)
                        .noCollision()
                        .strength(1.0F),
                WALL_HANGING_SIGN_BLOCK(woodType)
        );
        Block pressurePlate = BLOCKS.register(name + "_pressure_plate",
                AbstractBlock.Settings.create()
                        .mapColor(lightShade)
                        .solid()
                        .instrument(BASS)
                        .noCollision()
                        .strength(0.5F)
                        .pistonBehavior(PistonBehavior.DESTROY),
                PRESSURE_PLATE_BLOCK(setType)
        );
        Block trapDoor = BLOCKS.register(name + "_trapdoor",
                AbstractBlock.Settings.create()
                        .mapColor(lightShade)
                        .instrument(BASS)
                        .strength(3.0F)
                        .nonOpaque()
                        .allowsSpawning(Blocks::never),
                TRAPDOOR_BLOCK(setType)
        );
        Block stairs = BLOCKS.register(name + "_stairs",AbstractBlock.Settings.copy(planks),  STAIRS(planks));
        Block slab = BLOCKS.register(name + "_slab",
                AbstractBlock.Settings.create()
                        .mapColor(lightShade)
                        .instrument(BASS)
                        .strength(2.0F,3.0F)
                        .sounds(soundGroup),
                SlabBlock::new
        );
        Block fenceGate = BLOCKS.register(name + "_fence_gate",
                AbstractBlock.Settings.create()
                        .mapColor(lightShade)
                        .solid()
                        .instrument(BASS)
                        .solid()
                        .strength(2.0F,3.0F),
                FENCE_GATE_BLOCK(woodType)
        );
        Block fence = BLOCKS.register(name + "_fence",
                AbstractBlock.Settings.create()
                        .mapColor(lightShade)
                        .instrument(BASS)
                        .strength(2.0F,3.0F)
                        .sounds(soundGroup),
                FenceBlock::new
        );
        Block door = BLOCKS.register( name  +"_door",
                AbstractBlock.Settings.create()
                        .mapColor(lightShade)
                        .instrument(BASS)
                        .strength(3.0F)
                        .nonOpaque()
                        .pistonBehavior(PistonBehavior.DESTROY),
                DOOR_BLOCK(setType)
        );
        Block button = BLOCKS.register(name + "_button",Blocks.createButtonSettings(),BUTTON_BLOCK(setType,30));
        return new FullBlockSet(planks,stairs,slab,fence,fenceGate,trapDoor,door,button,pressurePlate, log,strippedLog,wood,strippedWood,sign,wallSign,hangingSign,wallHangingSign,
                registerBlockItem(planks),
                registerBlockItem(stairs),
                registerBlockItem(slab),
                registerBlockItem(fence),
                registerBlockItem(fenceGate),
                registerBlockItem(trapDoor),
                registerBlockItem(door),
                registerBlockItem(button),
                registerBlockItem(pressurePlate),
                registerBlockItem(log),
                registerBlockItem(strippedLog),
                registerBlockItem(wood),
                registerBlockItem(strippedWood),
                (BlockItem) ITEMS.register(name + "_sign", new Item.Settings().maxCount(16),(settings) -> new SignItem(sign,wallSign,settings)),
                (BlockItem) ITEMS.register(name + "_hanging_sign", new Item.Settings().maxCount(16),(settings) -> new SignItem(hangingSign,wallHangingSign,settings))
                );
    }
    public record FullBlockSet(
            Block planks,
            Block stairs,
            Block slab,
            Block fence,
            Block fenceGate,
            Block trapdoor,
            Block door,
            Block button,
            Block pressurePlate,
            Block log,
            Block strippedLog,
            Block wood,
            Block strippedWood,
            Block sign,
            Block wallSign,
            Block hangingSign,
            Block wallHangingSign,
            BlockItem planksItem,
            BlockItem stairsItem,
            BlockItem slabItem,
            BlockItem fenceItem,
            BlockItem fenceGateItem,
            BlockItem trapdoorItem,
            BlockItem doorItem,
            BlockItem buttonItem,
            BlockItem pressurePlateItem,
            BlockItem logItem,
            BlockItem strippedLogItem,
            BlockItem woodItem,
            BlockItem strippedWoodItem,
            BlockItem signItem,
            BlockItem hangingSignItem
    ){
        public List<Block> blockList(){
            return List.of(
                    planks,stairs,slab,fence,fenceGate,trapdoor,door,button,pressurePlate ,log,strippedLog,wood,strippedWood,sign,wallSign,wallHangingSign
            );
        }
        public List<BlockItem> itemList(){
            return List.of(planksItem,stairsItem,slabItem,fenceItem,fenceGateItem,trapdoorItem,doorItem,
                    buttonItem,pressurePlateItem ,logItem,strippedLogItem,woodItem,strippedWoodItem,signItem,hangingSignItem);
        }
    }
}
