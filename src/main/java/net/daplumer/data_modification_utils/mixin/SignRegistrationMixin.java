package net.daplumer.data_modification_utils.mixin;

import com.google.common.collect.ImmutableMap;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.daplumer.sculk_dimension.TheSculkDimension;
import net.daplumer.sculk_dimension.block.BlockSets;
import net.minecraft.block.Block;
import net.minecraft.client.render.entity.model.LoadedEntityModels;
import net.minecraft.client.render.item.model.special.HangingSignModelRenderer;
import net.minecraft.client.render.item.model.special.SignModelRenderer;
import net.minecraft.client.render.item.model.special.SpecialModelRenderer;
import net.minecraft.client.render.item.model.special.SpecialModelTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Map;

import static net.daplumer.sculk_dimension.block.ModBlocks.*;

@Mixin(SpecialModelTypes.class)
public class SignRegistrationMixin {
    @ModifyReturnValue(method = "buildBlockToModelTypeMap",at = @At(value = "RETURN"))
    private static Map<Block, SpecialModelRenderer<?>> aVoid(Map<Block, SpecialModelRenderer<?>> original, @Local(argsOnly = true) LoadedEntityModels entityModels){
        TheSculkDimension.LOGGER.info("Added Sign Models");
        ImmutableMap.Builder<Block, SpecialModelRenderer<?>> builder = ImmutableMap.<Block, SpecialModelRenderer<?>>builder().putAll(original);
        builder.put(INFECTED_SIGN,new SignModelRenderer.Unbaked(BlockSets.INFECTED_WOOD).bake(entityModels));
        builder.put(INFECTED_WALL_SIGN,new SignModelRenderer.Unbaked(BlockSets.INFECTED_WOOD).bake(entityModels));
        builder.put(INFECTED_HANGING_SIGN,new HangingSignModelRenderer.Unbaked(BlockSets.INFECTED_WOOD).bake(entityModels));
        builder.put(INFECTED_WALL_HANGING_SIGN,new HangingSignModelRenderer.Unbaked(BlockSets.INFECTED_WOOD).bake(entityModels));
        return builder.build();
    }
}
