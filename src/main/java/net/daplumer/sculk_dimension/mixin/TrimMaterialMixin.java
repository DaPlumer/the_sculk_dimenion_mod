package net.daplumer.sculk_dimension.mixin;

import net.daplumer.sculk_dimension.item.ModTrimMaterials;
import net.minecraft.item.equipment.trim.ArmorTrimAssets;
import net.minecraft.item.equipment.trim.ArmorTrimMaterial;
import net.minecraft.item.equipment.trim.ArmorTrimMaterials;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Style;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArmorTrimMaterials.class)
public abstract class TrimMaterialMixin {
    @Shadow
    private static void register(Registerable<ArmorTrimMaterial> registry, RegistryKey<ArmorTrimMaterial> key, Style style, ArmorTrimAssets assets) {
    }

    @Inject(method = "bootstrap",at = @At("HEAD"))
    private static void aVoid(Registerable<ArmorTrimMaterial> registry, CallbackInfo ci){
        register(registry,ModTrimMaterials.MEMORY_GEM_TRIM_KEY,Style.EMPTY.withColor(16545810), ModTrimMaterials.MEMORY_GEM_ASSET);
    }
}
