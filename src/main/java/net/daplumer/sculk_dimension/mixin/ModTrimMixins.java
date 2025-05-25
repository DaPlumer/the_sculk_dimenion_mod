package net.daplumer.sculk_dimension.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.daplumer.sculk_dimension.item.ModTrimMaterials;
import net.minecraft.client.data.ItemModelGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.ArrayList;
import java.util.List;

@Mixin(ItemModelGenerator.class)
public class ModTrimMixins {
    @ModifyArg(method = "registerArmor",at = @At(value = "INVOKE", target = "Ljava/util/ArrayList;<init>(I)V"))
    int aVoid(int initialCapacity){
        return initialCapacity + 1;
    }
    @ModifyExpressionValue(method = "registerArmor",at = @At(value = "FIELD", target = "Lnet/minecraft/client/data/ItemModelGenerator;TRIM_MATERIALS:Ljava/util/List;",ordinal = 1))
    List<ItemModelGenerator.TrimMaterial> aVoid(List<ItemModelGenerator.TrimMaterial> original){
        try {
            original.add(ModTrimMaterials.MEMORY_GEM_TRIM_MATERIAL);
            return original;
        } catch (UnsupportedOperationException e){
            ArrayList<ItemModelGenerator.TrimMaterial> _list = new ArrayList<>(original);
            _list.add(ModTrimMaterials.MEMORY_GEM_TRIM_MATERIAL);
            return _list;
        }
    }
}
