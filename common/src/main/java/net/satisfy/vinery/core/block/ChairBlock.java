package net.satisfy.vinery.core.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.satisfy.vinery.core.util.ChairUtil;
import org.jetbrains.annotations.NotNull;

public class ChairBlock extends Block {
    public static final DirectionProperty FACING;
    private static final VoxelShape SINGLE_SHAPE;
    private static final VoxelShape[] SHAPE;

    public ChairBlock(BlockBehaviour.Properties settings) {
        super(settings);
    }

    protected static VoxelShape makeSingleShape() {
        VoxelShape top = Block.box(3.0, 9.0, 3.0, 13.0, 10.0, 13.0);
        VoxelShape leg1 = Block.box(3.0, 0.0, 3.0, 5.0, 9.0, 5.0);
        VoxelShape leg2 = Block.box(3.0, 0.0, 11.0, 5.0, 9.0, 13.0);
        VoxelShape leg3 = Block.box(11.0, 0.0, 11.0, 13.0, 9.0, 13.0);
        VoxelShape leg4 = Block.box(11.0, 0.0, 3.0, 13.0, 9.0, 5.0);
        return Shapes.or(top, leg1, leg2, leg3, leg4);
    }

    public @NotNull VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        switch (state.getValue(FACING)) {
            case WEST -> {
                return Shapes.or(SINGLE_SHAPE, SHAPE[1]);
            }
            case SOUTH -> {
                return Shapes.or(SINGLE_SHAPE, SHAPE[2]);
            }
            case EAST -> {
                return Shapes.or(SINGLE_SHAPE, SHAPE[3]);
            }
            default -> {
                return Shapes.or(SINGLE_SHAPE, SHAPE[0]);
            }
        }
    }

    public @NotNull InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        return ChairUtil.onUse(world, player, hand, hit, 0.1);
    }

    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean moved) {
        super.onRemove(state, world, pos, newState, moved);
        ChairUtil.onStateReplaced(world, pos);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{FACING});
    }

    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return (BlockState)this.defaultBlockState().setValue(FACING, ctx.getHorizontalDirection().getOpposite());
    }

    public BlockState rotate(BlockState state, Rotation rotation) {
        return (BlockState)state.setValue(FACING, rotation.rotate((Direction)state.getValue(FACING)));
    }

    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation((Direction)state.getValue(FACING)));
    }

    static {
        FACING = HorizontalDirectionalBlock.FACING;
        SINGLE_SHAPE = makeSingleShape();
        SHAPE = new VoxelShape[]{Block.box(3.0, 10.0, 11.0, 13.0, 22.0, 13.0), Block.box(11.0, 10.0, 3.0, 13.0, 22.0, 13.0), Block.box(3.0, 10.0, 3.0, 13.0, 22.0, 5.0), Block.box(3.0, 10.0, 3.0, 5.0, 22.0, 13.0)};
    }
}

