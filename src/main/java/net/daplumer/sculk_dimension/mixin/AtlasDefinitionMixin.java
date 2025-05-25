package net.daplumer.sculk_dimension.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.daplumer.sculk_dimension.item.ModTrimMaterials;
import net.minecraft.client.data.AtlasDefinitionProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.stream.Stream;

@Mixin (AtlasDefinitionProvider.class)
public abstract class AtlasDefinitionMixin {
    @ModifyExpressionValue(method = "streamTrimAssets",at = @At(value = "INVOKE", target = "Ljava/util/List;stream()Ljava/util/stream/Stream;"))
    private static Stream aVoid(Stream original){
        return Stream.concat(original,Stream.of(ModTrimMaterials.MEMORY_GEM_TRIM_MATERIAL));
    }
}
