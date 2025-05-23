package net.daplumer.sculk_dimension;

import net.daplumer.sculk_dimension.block.ModBlocks;
import net.daplumer.sculk_dimension.datagen.ModItemTags;
import net.daplumer.sculk_dimension.entity.ModEntityTypes;
import net.daplumer.sculk_dimension.util.SculkIdentifier;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.BoatEntityRenderer;
import net.minecraft.client.render.entity.model.BoatEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.EquipmentSlot;
import org.lwjgl.glfw.GLFW;

public class TheSculkDimensionClient implements ClientModInitializer {
    public static KeyBinding depthViewMode;
    public static float depthTransition = 0;
    public static boolean useDepthShader = true;

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(),
                ModBlocks.ECHOING_BLOOM,
                ModBlocks.ECHOING_BLOOM_TIP,
                ModBlocks.ENCHANTMENT_DUPLICATOR,
                ModBlocks.infected.getDoors().trapdoor()
        );
        final EntityModelLayer INFECTED_BOAT = new EntityModelLayer(SculkIdentifier.of("boat/infected"),"main");
        final EntityModelLayer INFECTED_CHEST_BOAT = new EntityModelLayer(SculkIdentifier.of("chest_boat/infected"),"main");
        EntityModelLayerRegistry.registerModelLayer(INFECTED_BOAT, BoatEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(INFECTED_CHEST_BOAT, BoatEntityModel::getChestTexturedModelData);
        EntityRendererRegistry.register(ModEntityTypes.INFECTED_BOAT,context -> new BoatEntityRenderer(context, INFECTED_BOAT));
        EntityRendererRegistry.register(ModEntityTypes.INFECTED_CHEST_BOAT,context -> new BoatEntityRenderer(context, INFECTED_CHEST_BOAT));
        depthViewMode = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.sculk_dimension.depth_veiw",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_V,
                "category.sculk_dimension.keybinds"
        ));
        ClientTickEvents.END_CLIENT_TICK.register((client -> {
            while (depthViewMode.wasPressed()){
                useDepthShader = !useDepthShader;
            }
            if(useDepthShader){
                depthTransition = (float) Math.min(1.0, depthTransition + 10.0 /client.getCurrentFps());
            } else {
                depthTransition = (float) Math.max(0.0, depthTransition - 10.0 /client.getCurrentFps());
            }
            if(client.player != null) {
                if (!client.player.getEquippedStack(EquipmentSlot.HEAD).isIn(ModItemTags.DEPTH_MAP_ENABLERS)) {
                    useDepthShader = false;
                    depthTransition = 1;
                }
            }
        }));
    }
}
