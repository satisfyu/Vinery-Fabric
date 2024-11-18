package net.satisfy.vinery.block;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Plane;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.satisfy.vinery.block.storage.StorageBlock;
import net.satisfy.vinery.registry.StorageTypeRegistry;
import net.satisfy.vinery.util.GeneralUtil;
import org.jetbrains.annotations.NotNull;

public class FlowerBoxBlock extends StorageBlock {

    private static final Supplier<VoxelShape> voxelShapeSupplier = () -> {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0.9375, 0.0, 0.5625, 1.0, 0.375, 1.0), BooleanOp.OR);
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0.0, 0.0, 0.5625, 0.0625, 0.375, 1.0), BooleanOp.OR);
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0.0625, 0.0, 0.5625, 0.9375, 0.375, 0.625), BooleanOp.OR);
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0.0625, 0.0, 0.9375, 0.9375, 0.375, 1.0), BooleanOp.OR);
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0.0625, 0.0, 0.625, 0.9375, 0.3125, 0.9375), BooleanOp.OR);
        return shape;
    };

    @SuppressWarnings("all")
    public static final Map<Direction, VoxelShape> SHAPE = (Map)Util.make(new HashMap(), (map) -> {
        Iterator var1 = Plane.HORIZONTAL.stream().toList().iterator();

        while(var1.hasNext()) {
            Direction direction = (Direction)var1.next();
            map.put(direction, GeneralUtil.rotateShape(Direction.NORTH, direction, (VoxelShape)voxelShapeSupplier.get()));
        }

    });

    public FlowerBoxBlock(BlockBehaviour.Properties settings) {
        super(settings);
    }

    public @NotNull VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return (VoxelShape)SHAPE.get(state.getValue(FACING));
    }

    public @NotNull InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        return player.isShiftKeyDown() ? InteractionResult.PASS : super.use(state, world, pos, player, hand, hit);
    }

    public int size() {
        return 2;
    }

    public ResourceLocation type() {
        return StorageTypeRegistry.FLOWER_BOX;
    }

    public Direction[] unAllowedDirections() {
        return new Direction[]{Direction.DOWN};
    }

    public boolean canInsertStack(ItemStack stack) {
        return stack.is(ItemTags.SMALL_FLOWERS);
    }

    public int getSection(Float x, Float y) {
        return (double)x < 0.5 ? 0 : 1;
    }
}

