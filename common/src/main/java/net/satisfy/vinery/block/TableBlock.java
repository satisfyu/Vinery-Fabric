package net.satisfy.vinery.block;

import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.satisfy.vinery.util.GeneralUtil;
import org.jetbrains.annotations.NotNull;

public class TableBlock extends LineConnectingBlock implements SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED;
    public static final VoxelShape TOP_SHAPE;
    public static final VoxelShape[] LEG_SHAPES;

    public TableBlock(BlockBehaviour.Properties settings) {
        super(settings);
        this.registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue(WATERLOGGED, false));
    }

    public @NotNull VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        Direction direction = (Direction)state.getValue(FACING);

        GeneralUtil.LineConnectingType type = (GeneralUtil.LineConnectingType)state.getValue(TYPE);

        if (type == GeneralUtil.LineConnectingType.MIDDLE) {
            return TOP_SHAPE;
        }
        else if (direction == Direction.NORTH && type == GeneralUtil.LineConnectingType.LEFT || direction == Direction.SOUTH && type == GeneralUtil.LineConnectingType.RIGHT) {
            return Shapes.or(TOP_SHAPE, new VoxelShape[]{LEG_SHAPES[0], LEG_SHAPES[3]});
        }
        else if ((direction != Direction.NORTH || type != GeneralUtil.LineConnectingType.RIGHT) && (direction != Direction.SOUTH || type != GeneralUtil.LineConnectingType.LEFT)) {
            if ((direction != Direction.EAST || type != GeneralUtil.LineConnectingType.LEFT) && (direction != Direction.WEST || type != GeneralUtil.LineConnectingType.RIGHT)) {
                return (direction != Direction.EAST || type != GeneralUtil.LineConnectingType.RIGHT) && (direction != Direction.WEST || type != GeneralUtil.LineConnectingType.LEFT) ? Shapes.or(TOP_SHAPE, LEG_SHAPES) : Shapes.or(TOP_SHAPE, new VoxelShape[]{LEG_SHAPES[2], LEG_SHAPES[3]});
            }
            else {
                return Shapes.or(TOP_SHAPE, new VoxelShape[]{LEG_SHAPES[0], LEG_SHAPES[1]});
            }
        }
        else {
            return Shapes.or(TOP_SHAPE, new VoxelShape[]{LEG_SHAPES[1], LEG_SHAPES[2]});
        }
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level world = context.getLevel();
        BlockPos clickedPos = context.getClickedPos();
        return (BlockState)((BlockState)Objects.requireNonNull(super.getStateForPlacement(context))).setValue(WATERLOGGED, world.getFluidState(clickedPos).getType() == Fluids.WATER);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(new Property[]{WATERLOGGED});
    }

    public @NotNull FluidState getFluidState(BlockState state) {
        return (Boolean)state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    static {
        WATERLOGGED = BlockStateProperties.WATERLOGGED;
        TOP_SHAPE = Block.box(0.0, 13.0, 0.0, 16.0, 16.0, 16.0);
        LEG_SHAPES = new VoxelShape[]{Block.box(1.0, 0.0, 1.0, 4.0, 13.0, 4.0), Block.box(12.0, 0.0, 1.0, 15.0, 13.0, 4.0), Block.box(12.0, 0.0, 12.0, 15.0, 13.0, 15.0), Block.box(1.0, 0.0, 12.0, 4.0, 13.0, 15.0)};
    }
}

