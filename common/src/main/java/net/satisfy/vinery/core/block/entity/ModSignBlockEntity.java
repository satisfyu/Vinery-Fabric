package net.satisfy.vinery.core.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.satisfy.vinery.core.registry.EntityTypeRegistry;

public class ModSignBlockEntity extends SignBlockEntity {

    public ModSignBlockEntity(BlockEntityType<? extends ModSignBlockEntity> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public ModSignBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(EntityTypeRegistry.MOD_SIGN.get(), pPos, pBlockState);
    }

    @Override
    public BlockEntityType<?> getType() {
        return EntityTypeRegistry.MOD_SIGN.get();
    }
}
