package net.daplumer.sculk_dimension.block.custom;

import com.mojang.serialization.MapCodec;
import net.daplumer.sculk_dimension.TheSculkDimension;
import net.daplumer.sculk_dimension.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.block.WireOrientation;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SculkCaptureBlock extends HorizontalFacingBlock {
    public static final VoxelShape verticalOutline =
            Block.createCuboidShape(0.0, -16.0, 0.0, 16.0, 16.0, 16.0);
    public static final IntProperty VARIANT = IntProperty.of("variant",0,3);
    public static final MapCodec<SculkCaptureBlock> CODEC = createCodec(SculkCaptureBlock::new);
    public SculkCaptureBlock(Settings settings) {
        super(settings);
    }
    public static ArrayList<BlockPos> getContainedNeighbors(BlockPos pos,AbstractBlockState captureBlockState) throws IllegalArgumentException{
        if (!captureBlockState.getBlock().getClass().equals(SculkCaptureBlock.class)){
            TheSculkDimension.LOGGER.warn("Block Class of the block in the position of X:{}, Y:{}, Z:{} is not equal to SculkCaptureBlock.class!", pos.getX(), pos.getY(), pos.getZ());
        }
        ArrayList<BlockPos> blockPositions = new java.util.ArrayList<>(List.of(pos.down()));
        if (captureBlockState.getOrEmpty(VARIANT).isPresent()){
            if (captureBlockState.get(VARIANT) != 2) {
                Direction facing = captureBlockState.get(FACING);
                blockPositions.add(pos.down());
                blockPositions.add(pos.offset(facing));
                blockPositions.add(pos.offset(facing.getOpposite()));
                if (captureBlockState.get(VARIANT) == 3) {
                    blockPositions.add(pos.offset(facing).down());
                    blockPositions.add(pos.offset(facing.getOpposite()).down());
                }
            }
        }
        return blockPositions;
    }
    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction dir = switch (ctx.getWorld().random.nextInt(4)) {
            case 0 -> Direction.NORTH;
            case 1 -> Direction.SOUTH;
            case 2 -> Direction.EAST;
            default -> Direction.WEST;
        };

        //throws an error when these values are null
        int variant = ctx.getWorld().random.nextInt(4);
        if (canPlace(ctx.getWorld(), ctx.getBlockPos(), this.getDefaultState().with(FACING, dir).with(VARIANT, variant))){
            return this.getDefaultState()
                    .with(FACING, dir)
                    .with(VARIANT, variant);

        }else{
            dir.rotateClockwise(Direction.Axis.Y);
            if(canPlace(ctx.getWorld(),ctx.getBlockPos(), this.getDefaultState().with(FACING, dir).with(VARIANT, variant))){
                return this.getDefaultState()
                        .with(FACING, dir)
                        .with(VARIANT, variant);

            }else {
                return this.getDefaultState().with(FACING, dir).with(VARIANT,2);
            }
        }
    }

    @Override
    protected MapCodec<? extends HorizontalFacingBlock> getCodec() {
        return CODEC;
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return verticalOutline;
    }

    @Override
   protected void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (!canPlace(world,pos)){
            world.breakBlock(pos,true);
        }else {
            world.setBlockState(pos.down(), ModBlocks.SCULK_NEIGHBOR.getDefaultState());
        }
    }

    @Override
    protected void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, @Nullable WireOrientation wireOrientation, boolean notify) {

        if (!canPlace(world,pos)){
            world.breakBlock(pos,false);
        }
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
    }
    @Override
    protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return canPlace(((World) world),pos);
    }
    private boolean acceptableNeighbor( World world, BlockPos pos){
        return world.getBlockState(pos).isAir() || world.getBlockState(pos).getBlock().getClass().equals(SculkCaptureNeighbor.class);
    }
    private boolean canPlace(World world, BlockPos pos){
        return canPlace(world, pos, world.getBlockState(pos));
    }
    private boolean canPlace( World world, BlockPos pos, AbstractBlockState captureBlockState){
        boolean can_place = world.getBlockState(pos.up()).isSideSolidFullSquare(world,pos.up(),Direction.DOWN);
        for (BlockPos neighborPos : getContainedNeighbors(pos, captureBlockState)){
            can_place &= acceptableNeighbor(world, neighborPos);
        }
        return can_place;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
        builder.add(VARIANT);
    }

    @Override
    public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {
        world.breakBlock(pos.down(),false);
        super.onBroken(world, pos, state);
    }

    @Override
    protected void onStateReplaced(BlockState state, ServerWorld world, BlockPos pos, boolean moved) {

        world.breakBlock(pos.down(),false);
        super.onStateReplaced(state, world, pos, moved);
    }
}
