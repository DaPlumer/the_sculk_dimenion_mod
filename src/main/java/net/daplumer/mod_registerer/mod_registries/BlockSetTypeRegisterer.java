package net.daplumer.mod_registerer.mod_registries;

import net.daplumer.mod_registerer.mixin.BlockSetTypeMixin;
import net.minecraft.block.BlockSetType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.Objects;
import java.util.function.Function;

public record BlockSetTypeRegisterer(String namespace) implements ModDataRegisterer<BlockSetType, BlockSetTypeRegisterer.Settings>{
    @Override
    public String getNameSpace() {
        return namespace;
    }

    @Override
    public BlockSetType register(@NotNull String name, @Nullable Settings instanceSettings, @Nullable Function<Settings, BlockSetType> instanceFactory) {
        BlockSetType instance = Objects.requireNonNullElseGet(instanceFactory,
                        ()->(settings -> settings.build(name)))
                        .apply(Objects.requireNonNullElse(instanceSettings,Settings.copy(BlockSetType.OAK)));
        BlockSetTypeMixin.getValues().put(name, instance);
        return instance;
    }

    @Override
    public BlockSetType getInstance(String name) {
        return BlockSetTypeMixin.getValues().get(name);
    }

    @Override
    public BlockSetType getInstance(Identifier identifier) {
        throw new UnsupportedOperationException();
    }

    @Override
    public RegistryKey<?> getRegistryKey(Identifier identifier) {
        throw new UnsupportedOperationException();
    }

    public static class Settings{
        private boolean canOpenByHand = true;
        private boolean canOpenByWindCharge = true;
        private boolean canButtonBeActivatedByArrows = true;
        private BlockSetType.ActivationRule pressurePlateSensitivity = BlockSetType.ActivationRule.EVERYTHING;
        private BlockSoundGroup soundType = BlockSoundGroup.WOOD;
        private SoundEvent doorClose = SoundEvents.BLOCK_WOODEN_DOOR_CLOSE;
        private SoundEvent doorOpen = SoundEvents.BLOCK_WOODEN_DOOR_OPEN;
        private SoundEvent trapdoorClose = SoundEvents.BLOCK_WOODEN_DOOR_CLOSE;
        private SoundEvent trapdoorOpen = SoundEvents.BLOCK_WOODEN_TRAPDOOR_OPEN;
        private SoundEvent pressurePlateClickOff = SoundEvents.BLOCK_WOODEN_PRESSURE_PLATE_CLICK_OFF;
        private SoundEvent pressurePlateClickOn = SoundEvents.BLOCK_WOODEN_PRESSURE_PLATE_CLICK_ON;
        private SoundEvent buttonClickOff = SoundEvents.BLOCK_WOODEN_BUTTON_CLICK_OFF;
        private SoundEvent buttonClick = SoundEvents.BLOCK_WOODEN_BUTTON_CLICK_ON;

        public static Settings copy(BlockSetType other){
            return new Settings()
                    .canOpenByHand(other.canOpenByHand())
                    .canOpenByWindCharge(other.canOpenByWindCharge())
                    .canButtonBeActivatedByArrows(other.canButtonBeActivatedByArrows())
                    .pressurePlateSensitivity(other.pressurePlateSensitivity())
                    .soundType(other.soundType())
                    .doorClose(other.doorClose())
                    .doorOpen(other.doorOpen())
                    .trapdoorClose(other.trapdoorClose())
                    .trapdoorOpen(other.trapdoorOpen())
                    .pressurePlateClickOff(other.pressurePlateClickOff())
                    .pressurePlateClickOn(other.pressurePlateClickOn())
                    .buttonClickOff(other.buttonClickOff())
                    .buttonClick(other.buttonClickOn());
        }
        public BlockSetType build(String name){
            return new BlockSetType(name,
                    canOpenByHand,
                    canOpenByWindCharge,
                    canButtonBeActivatedByArrows,
                    pressurePlateSensitivity,
                    soundType,
                    doorClose,
                    doorOpen,
                    trapdoorClose,
                    trapdoorOpen,
                    pressurePlateClickOff,
                    pressurePlateClickOn,
                    buttonClickOff,
                    buttonClick);
        }

        public Settings buttonClick(SoundEvent buttonClick) {
            this.buttonClick = buttonClick;
            return this;
        }

        public Settings buttonClickOff(SoundEvent buttonClickOff) {
            this.buttonClickOff = buttonClickOff;
            return this;
        }

        public Settings pressurePlateClickOn(SoundEvent pressurePlateClickOn) {
            this.pressurePlateClickOn = pressurePlateClickOn;
            return this;
        }

        public Settings pressurePlateClickOff(SoundEvent pressurePlateClickOff) {
            this.pressurePlateClickOff = pressurePlateClickOff;
            return this;
        }
        public Settings trapdoorOpen(SoundEvent trapdoorOpen) {
            this.trapdoorOpen = trapdoorOpen;
            return this;
        }

        public Settings trapdoorClose(SoundEvent trapdoorClose) {
            this.trapdoorClose = trapdoorClose;
            return this;
        }

        public Settings doorOpen(SoundEvent doorOpen) {
            this.doorOpen = doorOpen;
            return this;
        }

        public Settings doorClose(SoundEvent doorClose) {
            this.doorClose = doorClose;
            return this;
        }

        public Settings soundType(BlockSoundGroup soundType) {
            this.soundType = soundType;
            return this;
        }

        public Settings pressurePlateSensitivity(BlockSetType.ActivationRule pressurePlateSensitivity) {
            this.pressurePlateSensitivity = pressurePlateSensitivity;
            return this;
        }

        public Settings canButtonBeActivatedByArrows(boolean canButtonBeActivatedByArrows) {
            this.canButtonBeActivatedByArrows = canButtonBeActivatedByArrows;
            return this;
        }

        public Settings canOpenByHand(boolean canOpenByHand) {
            this.canOpenByHand = canOpenByHand;
            return this;
        }

        public Settings canOpenByWindCharge(boolean canOpenByWindCharge) {
            this.canOpenByWindCharge = canOpenByWindCharge;
            return this;
        }
    }
}
