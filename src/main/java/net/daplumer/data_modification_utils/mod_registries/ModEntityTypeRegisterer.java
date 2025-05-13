package net.daplumer.data_modification_utils.mod_registries;

import kotlin.jvm.functions.Function1;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ModEntityTypeRegisterer extends ModDataRegisterer<EntityType<?>, EntityType.Builder<?>, EntityType<?>> {
    public ModEntityTypeRegisterer(@NotNull String namespace) {
        super(namespace);
    }

    @Override
    public EntityType<?> register(@NotNull String name, EntityType.@Nullable Builder<?> instanceSettings, @Nullable Function1<? super EntityType.Builder<?>,? extends EntityType<?>> instanceFactory) {
        assert instanceSettings != null;
        return Registry.register(Registries.ENTITY_TYPE, this.getRegistryKey(name), instanceSettings.build(this.getRegistryKey(name)));
    }

    @Override
    public EntityType<?> getInstance(@NotNull Identifier identifier) {
        return Registries.ENTITY_TYPE.get(identifier);
    }

    @Override
    public @NotNull RegistryKey<EntityType<?>> getRegistryKey(@NotNull Identifier identifier) {
        return RegistryKey.of(RegistryKeys.ENTITY_TYPE, identifier);
    }
}