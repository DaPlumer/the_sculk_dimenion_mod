package net.daplumer.sculk_dimension.block.custom;

import com.mojang.serialization.MapCodec;
import net.daplumer.sculk_dimension.block.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;

public class EchoingBloomTip extends AbstractPlantStemBlock {
    public static final MapCodec<net.minecraft.block.WeepingVinesBlock> CODEC = createCodec(net.minecraft.block.WeepingVinesBlock::new);
    private static final VoxelShape SHAPE = Block.createColumnShape(4.0, 14.0, 16.0);

    @Override
    public MapCodec<net.minecraft.block.WeepingVinesBlock> getCodec() {
        return CODEC;
    }

    public EchoingBloomTip(AbstractBlock.Settings settings) {
        super(settings, Direction.DOWN, SHAPE, false, 0.1);
    }

    @Override
    protected int getGrowthLength(Random random) {
        return VineLogic.getGrowthLength(random);
    }

    @Override
    protected Block getPlant() {
        return ModBlocks.ECHOING_BLOOM;
    }

    @Override
    protected boolean chooseStemState(BlockState state) {
        return VineLogic.isValidForWeepingStem(state);
    }
}
