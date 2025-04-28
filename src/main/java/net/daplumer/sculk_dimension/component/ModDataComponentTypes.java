package net.daplumer.sculk_dimension.component;

import net.daplumer.sculk_dimension.TheSculkDimension;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.Codecs;

import java.util.function.UnaryOperator;

public class ModDataComponentTypes {
   public static final ComponentType<Integer> INSANITY = register(builder -> builder.codec(Codecs.NON_NEGATIVE_INT));

    private static <T>ComponentType<T> register(UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of(TheSculkDimension.MOD_ID, "insanity"),
        builderOperator.apply(ComponentType.builder()).build());
    }
    public static void registerDataComponentTypes(){
        TheSculkDimension.LOGGER.info("Registering Data Component Types for " + TheSculkDimension.MOD_ID);

    }
}
