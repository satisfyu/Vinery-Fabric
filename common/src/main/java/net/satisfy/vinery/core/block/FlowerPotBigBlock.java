package net.satisfy.vinery.core.block;

import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.satisfy.vinery.core.block.storage.StorageBlock;
import net.satisfy.vinery.core.registry.StorageTypeRegistry;
import org.jetbrains.annotations.NotNull;

public class FlowerPotBigBlock extends StorageBlock {
    private static final Supplier<VoxelShape> voxelShapeSupplier = () -> {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0.78125, 0.4375, 0.21875, 0.90625, 0.625, 0.78125), BooleanOp.OR);
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0.09375, 0.4375, 0.21875, 0.21875, 0.625, 0.78125), BooleanOp.OR);
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0.21875, 0.0, 0.21875, 0.78125, 0.4375, 0.78125), BooleanOp.OR);
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0.09375, 0.4375, 0.09375, 0.90625, 0.625, 0.21875), BooleanOp.OR);
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0.09375, 0.4375, 0.78125, 0.90625, 0.625, 0.90625), BooleanOp.OR);
        return shape;
    };
    private static final VoxelShape SHAPE;

    public FlowerPotBigBlock(BlockBehaviour.Properties settings) {
        super(settings);
    }

    public int size() {
        return 1;
    }

    public ResourceLocation type() {
        return StorageTypeRegistry.FLOWER_POT_BIG;
    }

    public Direction[] unAllowedDirections() {
        return new Direction[0];
    }

    public boolean canInsertStack(ItemStack stack) {
        return stack.is(ItemTags.TALL_FLOWERS);
    }

    public int getSection(Float x, Float y) {
        return 0;
    }

    @SuppressWarnings("deprecation")
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    public SoundEvent getAddSound(Level level, BlockPos blockPos, Player player, int i) {
        return SoundEvents.GRASS_PLACE;
    }

    public SoundEvent getRemoveSound(Level level, BlockPos blockPos, Player player, int i) {
        return SoundEvents.GRASS_BREAK;
    }

    static {
        SHAPE = voxelShapeSupplier.get();
    }
}

