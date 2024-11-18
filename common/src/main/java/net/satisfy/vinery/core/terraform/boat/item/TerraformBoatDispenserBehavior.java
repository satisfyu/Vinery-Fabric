package net.satisfy.vinery.core.terraform.boat.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.satisfy.vinery.platform.PlatformHelper;
import org.jetbrains.annotations.NotNull;

public class TerraformBoatDispenserBehavior extends DefaultDispenseItemBehavior {
    private static final DispenseItemBehavior FALLBACK_BEHAVIOR = new DefaultDispenseItemBehavior();
    private static final float OFFSET_MULTIPLIER = 1.125F;
    private final ResourceLocation boatTypeName;
    private final boolean chest;

    public TerraformBoatDispenserBehavior(ResourceLocation boatTypeName, boolean chest) {
        this.boatTypeName = boatTypeName;
        this.chest = chest;
    }

    public @NotNull ItemStack execute(BlockSource pointer, ItemStack stack) {
        Direction facing = (Direction)pointer.getBlockState().getValue(DispenserBlock.FACING);
        double x = pointer.x() + (double)((float)facing.getStepX() * 1.125F);
        double y = pointer.y() + (double)((float)facing.getStepY() * 1.125F);
        double z = pointer.z() + (double)((float)facing.getStepZ() * 1.125F);
        Level world = pointer.getLevel();
        BlockPos pos = pointer.getPos().relative(facing);
        if (((Level)world).getFluidState(pos).is(FluidTags.WATER)) {
            ++y;
        } else if (!((Level)world).getBlockState(pos).isAir() || !((Level)world).getFluidState(pos.below()).is(FluidTags.WATER)) {
            return FALLBACK_BEHAVIOR.dispense(pointer, stack);
        }

        Boat boatEntity = PlatformHelper.createBoat(this.boatTypeName, world, x, y, z, this.chest);
        boatEntity.setYRot(facing.toYRot());
        ((Level)world).addFreshEntity(boatEntity);
        stack.shrink(1);
        return stack;
    }
}

