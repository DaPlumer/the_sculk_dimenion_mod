package net.daplumer.sculk_dimension;

import net.daplumer.sculk_dimension.block.ModBlocks;
import net.daplumer.sculk_dimension.entity.ModEntityTypes;
import net.daplumer.sculk_dimension.util.SculkIdentifier;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.BoatEntityRenderer;
import net.minecraft.client.render.entity.model.BoatEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;

public class TheSculkDimensionClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(),
                ModBlocks.ECHOING_BLOOM,
                ModBlocks.ECHOING_BLOOM_TIP,
                ModBlocks.ENCHANTMENT_DUPLICATOR,
                ModBlocks.infected.getDoors().trapdoor()
        );
        final EntityModelLayer INFECTED_BOAT = new EntityModelLayer(SculkIdentifier.of("boat/infected_planks"),"main");
        final EntityModelLayer INFECTED_CHEST_BOAT = new EntityModelLayer(SculkIdentifier.of("chest_boat/infected_planks"),"main");
        EntityModelLayerRegistry.registerModelLayer(INFECTED_BOAT, BoatEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(INFECTED_CHEST_BOAT, BoatEntityModel::getChestTexturedModelData);
        EntityRendererRegistry.register(ModEntityTypes.INFECTED_BOAT,context -> new BoatEntityRenderer(context, INFECTED_BOAT));
        EntityRendererRegistry.register(ModEntityTypes.INFECTED_CHEST_BOAT,context -> new BoatEntityRenderer(context, INFECTED_CHEST_BOAT));
    }
}
