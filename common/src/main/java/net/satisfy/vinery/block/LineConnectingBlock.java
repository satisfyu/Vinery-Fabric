package net.satisfy.vinery.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.satisfy.vinery.util.GeneralUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LineConnectingBlock extends Block {
    public static final DirectionProperty FACING;
    public static final EnumProperty<GeneralUtil.LineConnectingType> TYPE;

    public LineConnectingBlock(BlockBehaviour.Properties settings) {
        super(settings);
        this.registerDefaultState((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(FACING, Direction.NORTH)).setValue(TYPE, GeneralUtil.LineConnectingType.NONE));
    }

    public @NotNull InteractionResult use(BlockState arg, Level arg2, BlockPos arg3, Player arg4, InteractionHand arg5, BlockHitResult arg6) {
        return InteractionResult.PASS;
    }

    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction facing = context.getHorizontalDirection().getOpposite();
        BlockState blockState = (BlockState)this.defaultBlockState().setValue(FACING, facing);
        Level world = context.getLevel();
        BlockPos clickedPos = context.getClickedPos();
        BlockState var10000;
        switch (facing) {
            case EAST -> var10000 = (BlockState)blockState.setValue(TYPE, this.getType(blockState, world.getBlockState(clickedPos.south()), world.getBlockState(clickedPos.north())));
            case SOUTH -> var10000 = (BlockState)blockState.setValue(TYPE, this.getType(blockState, world.getBlockState(clickedPos.west()), world.getBlockState(clickedPos.east())));
            case WEST -> var10000 = (BlockState)blockState.setValue(TYPE, this.getType(blockState, world.getBlockState(clickedPos.north()), world.getBlockState(clickedPos.south())));
            default -> var10000 = (BlockState)blockState.setValue(TYPE, this.getType(blockState, world.getBlockState(clickedPos.east()), world.getBlockState(clickedPos.west())));
        }

        return var10000;
    }

    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        if (!world.isClientSide) {
            Direction facing = (Direction)state.getValue(FACING);
            GeneralUtil.LineConnectingType type;
            switch (facing) {
                case EAST -> type = this.getType(state, world.getBlockState(pos.south()), world.getBlockState(pos.north()));
                case SOUTH -> type = this.getType(state, world.getBlockState(pos.west()), world.getBlockState(pos.east()));
                case WEST -> type = this.getType(state, world.getBlockState(pos.north()), world.getBlockState(pos.south()));
                default -> type = this.getType(state, world.getBlockState(pos.east()), world.getBlockState(pos.west()));
            }

            if (state.getValue(TYPE) != type) {
                state = (BlockState)state.setValue(TYPE, type);
            }

            world.setBlock(pos, state, 3);
        }
    }

    public GeneralUtil.LineConnectingType getType(BlockState state, BlockState left, BlockState right) {
        boolean shape_left_same = left.getBlock() == state.getBlock() && left.getValue(FACING) == state.getValue(FACING);
        boolean shape_right_same = right.getBlock() == state.getBlock() && right.getValue(FACING) == state.getValue(FACING);
        if (shape_left_same && shape_right_same) {
            return GeneralUtil.LineConnectingType.MIDDLE;
        } else if (shape_left_same) {
            return GeneralUtil.LineConnectingType.LEFT;
        } else {
            return shape_right_same ? GeneralUtil.LineConnectingType.RIGHT : GeneralUtil.LineConnectingType.NONE;
        }
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{FACING, TYPE});
    }

    public @NotNull BlockState rotate(BlockState state, Rotation rotation) {
        return (BlockState)state.setValue(FACING, rotation.rotate((Direction)state.getValue(FACING)));
    }

    public @NotNull BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation((Direction)state.getValue(FACING)));
    }

    static {
        FACING = BlockStateProperties.HORIZONTAL_FACING;
        TYPE = GeneralUtil.LINE_CONNECTING_TYPE;
    }
}

