package net.daplumer.sculk_dimension;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.util.Identifier;

public class TheSculkDimensionClient implements ClientModInitializer {
    private static final Identifier ECHO_DISPLAY = Identifier.of(TheSculkDimension.MOD_ID, "textures/gui/echo_display.png");
    @Override
    public void onInitializeClient() {
        HudRenderCallback.EVENT.register(((drawContext, tickDelta) -> {
            drawContext.drawTexture(ECHO_DISPLAY, 0, 0, 0, 16, 16, 16,16,256);
        }));
    }
}
