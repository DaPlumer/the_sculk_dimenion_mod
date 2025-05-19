package net.daplumer.data_modification_utils.mod_registries;

import kotlin.jvm.functions.Function1;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.registry.Registries;
import net.minecraft.util.Util;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ModBlockEntityTypeRegisterer extends GeneralDataRegisterer<BlockEntityType<?>, FabricBlockEntityTypeBuilder<?>>{
    public ModBlockEntityTypeRegisterer(String namespace){
        super(Registries.BLOCK_ENTITY_TYPE,namespace, null, FabricBlockEntityTypeBuilder::build,() -> null);
    }

    @Override
    public BlockEntityType<?> register(@NotNull String name, @Nullable FabricBlockEntityTypeBuilder<?> instanceSettings, @Nullable Function1<? super FabricBlockEntityTypeBuilder<?>, ? extends BlockEntityType<?>> instanceFactory) {
        Util.getChoiceType(TypeReferences.BLOCK_ENTITY, name);
        return super.register(name, instanceSettings, instanceFactory);
    }
}
