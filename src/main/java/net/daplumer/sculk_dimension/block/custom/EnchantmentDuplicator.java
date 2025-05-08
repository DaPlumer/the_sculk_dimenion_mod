package net.daplumer.sculk_dimension.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class EnchantmentDuplicator extends Block {
    public static final VoxelShape outline = Block.createColumnShape(16,0,8);
    public static final MapCodec<EnchantmentDuplicator> CODEC = createCodec(EnchantmentDuplicator::new);

    @Override
    public MapCodec<? extends Block> getCodec() {
        return CODEC;
    }

    public static final Property<Direction> FACING = Properties.HORIZONTAL_FACING;

    public EnchantmentDuplicator(Settings settings) {
        super(settings);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return outline;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(FACING);
    }

    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        //noinspection DataFlowIssue
        return super.getPlacementState(ctx).with(FACING,ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if(!world.isClient){
            player.openHandledScreen(state.createScreenHandlerFactory(world,pos));
        }
        return ActionResult.SUCCESS;
    }

    @Nullable
    @Override
    protected NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
        return new SimpleNamedScreenHandlerFactory(
            (syncId, inventory, player) -> new EnchantmentDuplicationScreenHandler(syncId, inventory, ScreenHandlerContext.create(world, pos)),Text.translatable("containers.sculk_dimension.enchantment_duplicator")
        );
    }
    //TODO: make better model for enchantment duplication
}
