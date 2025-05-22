package net.daplumer.sculk_dimension.item.custom;

import net.daplumer.sculk_dimension.TheSculkDimensionClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class ResonationHelmet extends Item {
    public ResonationHelmet(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        TheSculkDimensionClient.useDepthShader = true;
        return super.use(world, user, hand);
    }
    //TODO: get someone to make a better helmet model
}
