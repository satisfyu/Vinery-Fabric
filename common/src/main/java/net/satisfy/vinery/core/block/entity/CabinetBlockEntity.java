package net.satisfy.vinery.core.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.satisfy.vinery.core.registry.EntityTypeRegistry;
import org.jetbrains.annotations.NotNull;

public class CabinetBlockEntity extends RandomizableContainerBlockEntity {
    private NonNullList<ItemStack> inventory;
    private final ContainerOpenersCounter stateManager;

    public CabinetBlockEntity(BlockPos pos, BlockState state) {
        this(pos, state, SoundEvents.CHEST_OPEN, SoundEvents.CHEST_CLOSE);
    }

    public CabinetBlockEntity(BlockPos pos, BlockState state, final SoundEvent openSound, final SoundEvent closeSound) {
        super(EntityTypeRegistry.CABINET_BLOCK_ENTITY.get(), pos, state);
        this.inventory = NonNullList.withSize(18, ItemStack.EMPTY);
        this.stateManager = new ContainerOpenersCounter() {
            protected void onOpen(Level world, BlockPos pos, BlockState state) {
                world.setBlock(pos, state.setValue(BlockStateProperties.OPEN, true), 3);

                assert CabinetBlockEntity.this.level != null;

                playSound(CabinetBlockEntity.this.level, pos, openSound);
            }

            protected void onClose(Level world, BlockPos pos, BlockState state) {
                world.setBlock(pos, state.setValue(BlockStateProperties.OPEN, false), 3);

                assert CabinetBlockEntity.this.level != null;

                playSound(CabinetBlockEntity.this.level, pos, closeSound);
            }

            static void playSound(Level level, BlockPos blockPos, SoundEvent soundEvent) {
                double d = (double) blockPos.getX() + 0.5;
                double e = (double) blockPos.getY() + 0.5;
                double f = (double) blockPos.getZ() + 0.5;
                level.playSound(null, d, e, f, soundEvent, SoundSource.BLOCKS, 0.7F, level.random.nextFloat() * 0.1F + 0.9F);
            }

            protected void openerCountChanged(Level world, BlockPos pos, BlockState state, int oldViewerCount, int newViewerCount) {
            }

            protected boolean isOwnContainer(Player player) {
                if (player.containerMenu instanceof ChestMenu) {
                    Container inventory = ((ChestMenu) player.containerMenu).getContainer();
                    return inventory == CabinetBlockEntity.this;
                } else {
                    return false;
                }
            }
        };
    }

    protected void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        if (!this.trySaveLootTable(nbt)) {
            ContainerHelper.saveAllItems(nbt, this.inventory);
        }

    }

    public void load(CompoundTag nbt) {
        super.load(nbt);
        this.inventory = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        if (!this.tryLoadLootTable(nbt)) {
            ContainerHelper.loadAllItems(nbt, this.inventory);
        }

    }

    public int getContainerSize() {
        return 18;
    }

    protected @NotNull NonNullList<ItemStack> getItems() {
        return this.inventory;
    }

    protected void setItems(NonNullList<ItemStack> list) {
        this.inventory = list;
    }

    protected @NotNull Component getDefaultName() {
        return Component.translatable(this.getBlockState().getBlock().getDescriptionId());
    }

    protected @NotNull AbstractContainerMenu createMenu(int syncId, Inventory playerInventory) {
        return new ChestMenu(MenuType.GENERIC_9x2, syncId, playerInventory, this, 2);
    }

    public void startOpen(Player player) {
        if (!this.remove && !player.isSpectator()) {
            this.stateManager.incrementOpeners(player, this.getLevel(), this.getBlockPos(), this.getBlockState());
        }

    }

    public void stopOpen(Player player) {
        if (!this.remove && !player.isSpectator()) {
            this.stateManager.decrementOpeners(player, this.getLevel(), this.getBlockPos(), this.getBlockState());
        }

    }

    public void tick() {
        if (!this.remove) {
            this.stateManager.recheckOpeners(this.getLevel(), this.getBlockPos(), this.getBlockState());
        }

    }
}

