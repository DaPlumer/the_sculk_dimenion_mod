package net.daplumer.mod_registerer.mod_registries;

import net.daplumer.mod_registerer.mixin.WoodTypeMixin;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.WoodType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Function;

public class WoodTypeReigsterer implements ModDataRegisterer<WoodType, WoodTypeReigsterer.Settings>{
    private final String namespace;

    public WoodTypeReigsterer(String namespace) {
        this.namespace = namespace;
    }
    @Override
    public String getNameSpace() {
        return namespace;
    }

    @Override
    public WoodType register(@NotNull String name, @Nullable Settings instanceSettings, @Nullable Function<Settings, WoodType> instanceFactory) {
        Settings settings = Objects.requireNonNull(instanceSettings);
        instanceFactory = Objects.requireNonNullElseGet(instanceFactory, ()->((settings1 -> settings1.build(name))));
        WoodType type = instanceFactory.apply(settings);
        WoodTypeMixin.getValues().put(name, type);
        return type;
    }

    @Override
    public WoodType getInstance(Identifier identifier) {
        throw new UnsupportedOperationException();
    }

    @Override
    public WoodType getInstance(String name) {
        return WoodTypeMixin.getValues().get(name);
    }

    @Override
    public RegistryKey<?> getRegistryKey(Identifier identifier) {
        throw new UnsupportedOperationException();
    }

    public static class Settings{
        private final BlockSetType setType;
        private BlockSoundGroup hangingSign = BlockSoundGroup.HANGING_SIGN;
        private SoundEvent fenceGateClose = SoundEvents.BLOCK_FENCE_GATE_CLOSE;
        private SoundEvent fenceGateOpen = SoundEvents.BLOCK_FENCE_GATE_OPEN;
        private Settings(BlockSetType type){
            setType = type;
        }
        public static Settings of(BlockSetType type){
            return new Settings(type);
        }
        public Settings hangingSignSound(BlockSoundGroup hangingSign){
            this.hangingSign = hangingSign;
            return this;
        }
        public Settings fenceGateSounds(SoundEvent close, SoundEvent open){
            this.fenceGateClose = close;
            this.fenceGateOpen = open;
            return this;
        }
        public WoodType build(String name){
            return new WoodType(name,setType ,setType.soundType(), hangingSign,fenceGateClose,fenceGateOpen);
        }
    }
}
