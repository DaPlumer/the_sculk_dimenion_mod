package net.daplumer.sculk_dimension.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class EchoingBloom extends TallPlantBlock {
    public static final DirectionProperty Facing = Properties.HORIZONTAL_FACING;
    public static final MapCodec<EchoingBloom> CODEC = createCodec(EchoingBloom::new);

    @Override
    public MapCodec<? extends TallPlantBlock> getCodec() {
        return CODEC;
    }

    public EchoingBloom(Settings settings) {
        super(settings);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        BlockPos blockPos = pos.up();
        assert placer != null;
        world.setBlockState(blockPos, withWaterloggedState(world, blockPos, this.getDefaultState().with(HALF, DoubleBlockHalf.UPPER).with(Facing, state.get(Facing))), Block.NOTIFY_ALL);
    }

    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(Facing, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return super.canPlaceAt(state, world, pos)
                || world.getBlockState(pos.down()).isOf(Blocks.SCULK)
                ||world.getBlockState(pos.down()).isOf(Blocks.SCULK_CATALYST);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(Facing);
        super.appendProperties(builder);
    }
}
