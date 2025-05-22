package net.daplumer.sculk_dimension.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.systems.RenderSystem;
import net.daplumer.sculk_dimension.TheSculkDimension;
import net.daplumer.sculk_dimension.TheSculkDimensionClient;
import net.daplumer.sculk_dimension.datagen.ModItemTags;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.PostEffectProcessor;
import net.minecraft.client.render.DefaultFramebufferSet;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.util.Pool;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Shadow @Final private MinecraftClient client;
    @Shadow @Final private Pool pool;

    @Inject(method = "renderWorld", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiler/Profiler;swap(Ljava/lang/String;)V"))
    void aVoid(RenderTickCounter renderTickCounter, CallbackInfo ci, @Local Profiler profiler){
        if (client.player == null) return;
        if(!client.player.getEquippedStack(EquipmentSlot.HEAD).isIn(ModItemTags.DEPTH_MAP_ENABLERS)) return;
        RenderSystem.resetTextureMatrix();
        PostEffectProcessor postEffectProcessor = this.client.getShaderLoader().loadPostEffect(Identifier.of(TheSculkDimension.MOD_ID,"depth_view"), DefaultFramebufferSet.MAIN_ONLY);
        if (postEffectProcessor != null) {
            //noinspection deprecation
            postEffectProcessor.render(this.client.getFramebuffer(), pool, (renderPass -> renderPass.setUniform("mixValue", TheSculkDimensionClient.depthTransition)));
        }

    }
}
