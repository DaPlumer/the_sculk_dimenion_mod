package net.daplumer.sculk_dimension.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.block.WireOrientation;
import org.jetbrains.annotations.Nullable;

public class SculkCaptureNeighbor extends Block {
    public static final MapCodec<SculkCaptureNeighbor> CODEC = createCodec(SculkCaptureNeighbor::new);

    private final static VoxelShape outline = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 32.0, 16.0);
    @Override
    protected com.mojang.serialization.MapCodec<? extends Block> getCodec() {
        return CODEC;
    }

    public SculkCaptureNeighbor(Settings settings) {
        super(settings);
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        world.breakBlock(pos.up(),!player.isCreative());
        return super.onBreak(world, pos, state, player);
    }

    @Override
    protected void onStateReplaced(BlockState state, ServerWorld world, BlockPos pos, boolean moved) {
        world.breakBlock(pos.up(),false);
        super.onStateReplaced(state, world, pos, moved);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return outline;
    }

    @Override
    protected void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, @Nullable WireOrientation wireOrientation, boolean notify) {
        world.updateNeighbor(pos.up(),sourceBlock,wireOrientation);
        super.neighborUpdate(state, world, pos, sourceBlock, wireOrientation, notify);
    }
}
