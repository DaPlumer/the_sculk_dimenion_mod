package net.daplumer.sculk_dimension.block.custom;

import com.mojang.serialization.MapCodec;
import net.daplumer.sculk_dimension.TheSculkDimension;
import net.daplumer.sculk_dimension.block.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SculkCaptureBlock extends HorizontalFacingBlock {
    public static final VoxelShape verticalOutline =
            Block.createCuboidShape(0.0, -16.0, 0.0, 16.0, 16.0, 16.0);
    public static final VoxelShape outlineX =
            Block.createCuboidShape(-8.0, -16.0, 0.0, 24.0, 16.0, 16.0);
    public static final VoxelShape outlineZ =
            Block.createCuboidShape(0.0, -16.0, -8.0, 16.0, 16.0, 24.0);
    private int variant = 0;//throws an error when these values are null
    private Direction dir = Direction.DOWN;
    public static final IntProperty VARIANT = IntProperty.of("variant",0,3);
    public static final MapCodec<SculkCaptureBlock> CODEC = createCodec(SculkCaptureBlock::new);
    public SculkCaptureBlock(Settings settings) {
        super(settings);
    }
    public static ArrayList<BlockPos> getContainedNeighbors(World world, BlockPos pos) throws IllegalArgumentException{
        BlockState captureBlockState = world.getBlockState(pos);
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
                blockPositions.add(pos.offset(facing).down());
                blockPositions.add(pos.offset(facing.getOpposite()).down());
            }
        }
        return blockPositions;
    }
    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {

        dir = switch (ctx.getWorld().random.nextInt(4)) {
            case 0 -> Direction.NORTH;
            case 1 -> Direction.SOUTH;
            case 2 -> Direction.EAST;
            default -> Direction.WEST;
        };

        variant = ctx.getWorld().random.nextInt(4);
        TheSculkDimension.LOGGER.info("Direction{}", dir);
        TheSculkDimension.LOGGER.info("Variant{}", variant);
        return this.getDefaultState()
                        .with(FACING,dir)
                        .with(VARIANT,variant);
    }

    @Override
    protected MapCodec<? extends HorizontalFacingBlock> getCodec() {
        return CODEC;
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (variant == 2){
            return verticalOutline;
        } else if (!(dir.equals(Direction.NORTH)||dir.equals(Direction.SOUTH))){
            return outlineX;
        } else {
            return outlineZ;
        }
    }

    @Override
   protected void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (!canPlace(world,pos)){
            world.breakBlock(pos,true);
        }
//        for (BlockPos neighborPos : getContainedNeighbors(world,pos)) {
//            BlockPos relativePos = neighborPos.subtract(pos);
//            world.emitGameEvent(GameEvent.BLOCK_CHANGE, neighborPos, GameEvent.Emitter.of(null,
//                    ModBlocks.SCULK_CAPTURE_NEIGHBOR.getDefaultState().
//                            with(FACING,mapNullToUp(Direction.fromVector(
//                                            relativePos.getX(),
//                                            relativePos.getY(),
//                                            relativePos.getZ())).getOpposite())));
//        }
    }
    private static Direction mapNullToUp(@Nullable Direction direction){
        if (direction == null){
            return Direction.UP;
        }
        return direction;
    }

    @Override
    protected void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
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
        return world.getBlockState(pos).isAir();
    }
    private boolean canPlace( World world, BlockPos pos){
        boolean canPlace = world.getBlockState(pos.up()).isSideSolidFullSquare(world,pos.up(),Direction.DOWN);
        for (BlockPos neighborPos : getContainedNeighbors(world, pos)){
            canPlace &= acceptableNeighbor(world, neighborPos);
        }
        return canPlace;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
        builder.add(VARIANT);
    }
}
