package net.satisfy.vinery.core.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.satisfy.vinery.client.gui.handler.FermentationBarrelGuiHandler;
import net.satisfy.vinery.core.recipe.FermentationBarrelRecipe;
import net.satisfy.vinery.core.registry.EntityTypeRegistry;
import net.satisfy.vinery.core.registry.ObjectRegistry;
import net.satisfy.vinery.core.registry.RecipeTypesRegistry;
import net.satisfy.vinery.core.util.WineYears;
import net.satisfy.vinery.core.world.ImplementedInventory;
import net.satisfy.vinery.platform.PlatformHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FermentationBarrelBlockEntity extends BlockEntity implements ImplementedInventory, MenuProvider {
    private static final int INVENTORY_SIZE = 6;
    private static final int GRAPEJUICE_INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 5;

    private NonNullList<ItemStack> inventory;
    private int fermentationTime = 0;
    private int totalFermentationTime;
    private int fluidLevel = 0;
    private String juiceType = "";

    private final ContainerData propertyDelegate = new ContainerData() {

        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> FermentationBarrelBlockEntity.this.fermentationTime;
                case 1 -> FermentationBarrelBlockEntity.this.totalFermentationTime;
                case 2 -> FermentationBarrelBlockEntity.this.fluidLevel;
                case 3 -> "red".equals(FermentationBarrelBlockEntity.this.juiceType) ? 1 : ("white".equals(FermentationBarrelBlockEntity.this.juiceType) ? 0 : -1);
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> FermentationBarrelBlockEntity.this.fermentationTime = value;
                case 1 -> FermentationBarrelBlockEntity.this.updateTotalFermentationTime();
                case 2 -> FermentationBarrelBlockEntity.this.setFluidLevel(value);
                case 3 -> FermentationBarrelBlockEntity.this.juiceType = value == 1 ? "red" : (value == 0 ? "white" : "");
                default -> {}
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    };

    public FermentationBarrelBlockEntity(BlockPos pos, BlockState state) {
        super(EntityTypeRegistry.FERMENTATION_BARREL_ENTITY.get(), pos, state);
        this.inventory = NonNullList.withSize(INVENTORY_SIZE, ItemStack.EMPTY);
        this.totalFermentationTime = PlatformHelper.getTotalFermentationTime();
    }

    public void updateTotalFermentationTime() {
        this.totalFermentationTime = PlatformHelper.getTotalFermentationTime();
        setChanged();
    }

    public int getFluidLevel() {
        return fluidLevel;
    }

    public void setFluidLevel(int fluidLevel) {
        this.fluidLevel = fluidLevel;
        setChanged();
        if (level != null && !level.isClientSide) {
            level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 3);
        }
    }

    public String getJuiceType() {
        return juiceType;
    }

    public void setJuiceType(String juiceType) {
        this.juiceType = juiceType;
        setChanged();
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        this.inventory = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(nbt, this.inventory);
        this.fermentationTime = nbt.getInt("FermentationTime");
        this.totalFermentationTime = nbt.getInt("TotalFermentationTime");
        this.fluidLevel = nbt.getInt("FluidLevel");
        this.juiceType = nbt.getString("JuiceType");
    }

    @Override
    public void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        ContainerHelper.saveAllItems(nbt, this.inventory);
        nbt.putInt("FermentationTime", this.fermentationTime);
        nbt.putInt("TotalFermentationTime", this.totalFermentationTime);
        nbt.putInt("FluidLevel", this.fluidLevel);
        nbt.putString("JuiceType", this.juiceType);
    }

    @SuppressWarnings("unused")
    public static void tick(Level world, BlockPos pos, BlockState state, FermentationBarrelBlockEntity blockEntity) {
        if (world.isClientSide) return;

        if (blockEntity.fluidLevel == 0) {
            blockEntity.setJuiceType("");
        }

        boolean dirty = false;
        RegistryAccess access = world.registryAccess();

        FermentationBarrelRecipe recipe = world.getRecipeManager()
                .getRecipeFor(RecipeTypesRegistry.FERMENTATION_BARREL_RECIPE_TYPE.get(), blockEntity, world)
                .orElse(null);

        if (blockEntity.canCraft(recipe, access)) {
            blockEntity.fermentationTime++;

            if (blockEntity.fermentationTime >= blockEntity.totalFermentationTime) {
                blockEntity.fermentationTime = 0;
                blockEntity.craft(recipe, access);
                dirty = true;
            }
        } else {
            blockEntity.fermentationTime = 0;
        }

        ItemStack stack = blockEntity.getItem(GRAPEJUICE_INPUT_SLOT);
        if (stack.is(ObjectRegistry.WHITE_GRAPEJUICE.get()) || stack.is(ObjectRegistry.RED_GRAPEJUICE.get())) {
            String newJuiceType = stack.is(ObjectRegistry.WHITE_GRAPEJUICE.get()) ? "white" : "red";

            if (blockEntity.fluidLevel == 0 || blockEntity.juiceType.equals(newJuiceType)) {
                blockEntity.setJuiceType(newJuiceType);
                int currentLevel = blockEntity.getFluidLevel();
                int maxFluidLevel = 100;
                int grapeJuiceCount = stack.getCount();

                int grapesToConsume = Math.min(grapeJuiceCount, 4);
                int fluidIncrease = grapesToConsume * 25;

                int newFluidLevel = Math.min(currentLevel + fluidIncrease, maxFluidLevel);
                int actualFluidIncrease = newFluidLevel - currentLevel;

                int actualGrapesConsumed = actualFluidIncrease / 25;

                if (actualGrapesConsumed > 0) {
                    blockEntity.setFluidLevel(newFluidLevel);

                    stack.shrink(actualGrapesConsumed);
                    if (stack.isEmpty()) {
                        blockEntity.setItem(GRAPEJUICE_INPUT_SLOT, ItemStack.EMPTY);
                    } else {
                        blockEntity.setItem(GRAPEJUICE_INPUT_SLOT, stack);
                    }

                    ItemStack wineBottleStack;
                    wineBottleStack = new ItemStack(ObjectRegistry.WINE_BOTTLE.get(), actualGrapesConsumed);

                    ItemStack existingOutput = blockEntity.getItem(OUTPUT_SLOT);
                    if (existingOutput.isEmpty()) {
                        blockEntity.setItem(OUTPUT_SLOT, wineBottleStack);
                    } else if (existingOutput.is(wineBottleStack.getItem()) && existingOutput.getCount() + wineBottleStack.getCount() <= existingOutput.getMaxStackSize()) {
                        existingOutput.grow(wineBottleStack.getCount());
                        blockEntity.setItem(OUTPUT_SLOT, existingOutput);
                    } else {
                        Containers.dropItemStack(world, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, wineBottleStack);
                    }

                    dirty = true;
                }
            }
        }

        if (dirty) {
            blockEntity.setChanged();
        }
    }


    private boolean canCraft(FermentationBarrelRecipe recipe, RegistryAccess access) {
        if (recipe == null || recipe.getResultItem(access).isEmpty()) {
            return false;
        } else if (areIngredientsEmpty()) {
            return false;
        } else if (this.fluidLevel < recipe.getJuiceAmount()) {
            return false;
        } else if (!this.juiceType.equals(recipe.getJuiceType())) {
            return false;
        } else {
            ItemStack outputSlotStack = this.getItem(OUTPUT_SLOT);
            ItemStack recipeOutput = recipe.getResultItem(access);

            if (outputSlotStack.isEmpty()) {
                return true;
            } else return outputSlotStack.is(recipeOutput.getItem()) && outputSlotStack.getCount() + recipeOutput.getCount() <= outputSlotStack.getMaxStackSize();
        }
    }

    private boolean areIngredientsEmpty() {
        for (int i = 1; i < 5; i++) {
            if (!this.getItem(i).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private void craft(FermentationBarrelRecipe recipe, RegistryAccess access) {
        if (!canCraft(recipe, access)) {
            return;
        }

        ItemStack recipeOutput = recipe.getResultItem(access).copy();

        ItemStack existingOutput = this.getItem(OUTPUT_SLOT);
        if (existingOutput.isEmpty()) {
            this.setItem(OUTPUT_SLOT, recipeOutput);
        } else if (existingOutput.is(recipeOutput.getItem()) && existingOutput.getCount() + recipeOutput.getCount() <= existingOutput.getMaxStackSize()) {
            existingOutput.grow(recipeOutput.getCount());
            this.setItem(OUTPUT_SLOT, existingOutput);
        } else {
            assert this.level != null;
            Containers.dropItemStack(this.level, this.worldPosition.getX() + 0.5, this.worldPosition.getY() + 1, this.worldPosition.getZ() + 0.5, recipeOutput);
        }

        int newFluidLevel = this.fluidLevel - recipe.getJuiceAmount();
        this.setFluidLevel(newFluidLevel);

        for (Ingredient ingredient : recipe.getIngredients()) {
            for (int i = 1; i < 5; i++) {
                ItemStack slotStack = this.getItem(i);
                if (ingredient.test(slotStack)) {
                    slotStack.shrink(1);
                    if (slotStack.isEmpty()) {
                        this.setItem(i, ItemStack.EMPTY);
                    } else {
                        this.setItem(i, slotStack);
                    }
                    break;
                }
            }
        }

        WineYears.setWineYear(recipeOutput, this.level);
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        final ItemStack stackInSlot = this.inventory.get(slot);
        boolean sameItem = !stack.isEmpty() && ItemStack.matches(stack, stackInSlot);

        this.inventory.set(slot, stack);

        if (stack.getCount() > this.getMaxStackSize()) {
            stack.setCount(this.getMaxStackSize());
        }

        if (!sameItem && isIngredientSlot(slot)) {
            if (areIngredientsEmpty()) {
                this.fermentationTime = 0;
                setChanged();
            }
        }
    }

    private boolean isIngredientSlot(int slot) {
        return slot >= 1 && slot <= 4;
    }

    @Override
    public boolean stillValid(Player player) {
        assert this.level != null;
        return this.level.getBlockEntity(this.worldPosition) == this &&
                player.distanceToSqr(this.worldPosition.getX() + 0.5, this.worldPosition.getY() + 0.5, this.worldPosition.getZ() + 0.5) <= 64.0;
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        CompoundTag tag = new CompoundTag();
        this.saveAdditional(tag);
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        this.saveAdditional(tag);
        return tag;
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable(this.getBlockState().getBlock().getDescriptionId());
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int syncId, Inventory inv, Player player) {
        return new FermentationBarrelGuiHandler(syncId, inv, this, this.propertyDelegate);
    }

    @Override
    public int getContainerSize() {
        return inventory.size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack itemstack : this.inventory) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public @NotNull ItemStack getItem(int index) {
        return this.inventory.get(index);
    }

    @Override
    public @NotNull ItemStack removeItem(int index, int count) {
        return ContainerHelper.removeItem(this.inventory, index, count);
    }

    @Override
    public @NotNull ItemStack removeItemNoUpdate(int index) {
        return ContainerHelper.takeItem(this.inventory, index);
    }

    @Override
    public void clearContent() {
        this.inventory.clear();
    }
}
