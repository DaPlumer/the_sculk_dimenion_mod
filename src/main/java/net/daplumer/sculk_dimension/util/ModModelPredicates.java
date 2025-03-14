package net.daplumer.sculk_dimension.util;

import net.daplumer.sculk_dimension.TheSculkDimension;
import net.daplumer.sculk_dimension.component.ModDataComponentTypes;
import net.daplumer.sculk_dimension.item.ModItems;
import net.daplumer.sculk_dimension.util.statistics.Insanity;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

public class ModModelPredicates {
    public static void registerModelPredicates(){
        TheSculkDimension.LOGGER.info("Registering Model Predicates for " + TheSculkDimension.MOD_ID);
        ModelPredicateProviderRegistry.register(ModItems.BROKEN_ECHO, Identifier.of(TheSculkDimension.MOD_ID, "insanity_texture"),
                (stack, world, entity, seed) -> stack.getOrDefault(ModDataComponentTypes.INSANITY,0)/100f);
    }
}
