package net.satisfy.vinery.client.gui.handler;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.satisfy.vinery.client.gui.handler.slot.ExtendedSlot;
import net.satisfy.vinery.client.gui.handler.slot.FermentationBarrelOutputSlot;
import net.satisfy.vinery.core.registry.ObjectRegistry;
import net.satisfy.vinery.core.registry.RecipeTypesRegistry;
import net.satisfy.vinery.core.registry.ScreenhandlerTypeRegistry;
import org.jetbrains.annotations.NotNull;

public class FermentationBarrelGuiHandler extends AbstractContainerMenu {

    private final Container inventory;
    private final Level level;
    public final ContainerData data;

    private static final int WINE_BOTTLE_SLOT = 4;
    private static final int OUTPUT_SLOT_GENERAL = 5;

    public FermentationBarrelGuiHandler(int syncId, Inventory playerInventory) {
        this(syncId, playerInventory, new SimpleContainer(6), new SimpleContainerData(4));
    }

    public FermentationBarrelGuiHandler(int syncId, Inventory playerInventory, Container inventory, ContainerData data) {
        super(ScreenhandlerTypeRegistry.FERMENTATION_BARREL_GUI_HANDLER.get(), syncId);
        this.inventory = inventory;
        this.level = playerInventory.player.level();
        this.data = data;

        this.addDataSlots(data);
        this.addBlockEntitySlots(playerInventory);
        this.addPlayerInventory(playerInventory);
    }

    private void addBlockEntitySlots(Inventory playerInventory) {
        this.addSlot(new ExtendedSlot(inventory, 0, 39, 17, stack -> {
            if (stack.is(ObjectRegistry.WHITE_GRAPEJUICE.get())) {
                return this.data.get(3) != 1 || this.data.get(2) == 0;
            } else if (stack.is(ObjectRegistry.RED_GRAPEJUICE.get())) {
                return this.data.get(3) != 0 || this.data.get(2) == 0;
            }
            return false;
        }));

        this.addSlot(new ExtendedSlot(inventory, 1, 67, 58, this::isIngredient));
        this.addSlot(new ExtendedSlot(inventory, 2, 85, 58, this::isIngredient));
        this.addSlot(new ExtendedSlot(inventory, 3, 103, 58, this::isIngredient));

        this.addSlot(new ExtendedSlot(inventory, WINE_BOTTLE_SLOT, 121, 58, stack -> stack.is(ObjectRegistry.WINE_BOTTLE.get())));

        this.addSlot(new FermentationBarrelOutputSlot(playerInventory.player, inventory, OUTPUT_SLOT_GENERAL, 103, 17));
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }
        for (int col = 0; col < 9; ++col) {
            this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 142));
        }
    }

    private boolean isIngredient(ItemStack stack) {
        return this.level.getRecipeManager()
                .getAllRecipesFor(RecipeTypesRegistry.FERMENTATION_BARREL_RECIPE_TYPE.get())
                .stream()
                .anyMatch(recipe -> recipe.getIngredients().stream().anyMatch(ingredient -> ingredient.test(stack)));
    }

    @Override
    public @NotNull ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack stackInSlot = slot.getItem();
            itemStack = stackInSlot.copy();
            int containerSlots = 6;
            if (index < containerSlots) {
                if (!this.moveItemStackTo(stackInSlot, containerSlots, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (stackInSlot.is(ObjectRegistry.WHITE_GRAPEJUICE.get()) || stackInSlot.is(ObjectRegistry.RED_GRAPEJUICE.get())) {
                    if (!this.moveItemStackTo(stackInSlot, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (stackInSlot.is(ObjectRegistry.WINE_BOTTLE.get())) {
                    if (!this.moveItemStackTo(stackInSlot, WINE_BOTTLE_SLOT, WINE_BOTTLE_SLOT + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (isIngredient(stackInSlot)) {
                    if (!this.moveItemStackTo(stackInSlot, 1, 4, false)) { 
                        return ItemStack.EMPTY;
                    }
                } else if (index < this.slots.size() - 9) {
                    if (!this.moveItemStackTo(stackInSlot, this.slots.size() - 9, this.slots.size(), false)) {
                        return ItemStack.EMPTY;
                    }
                } else {
                    if (!this.moveItemStackTo(stackInSlot, containerSlots, this.slots.size() - 9, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            }

            if (stackInSlot.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (stackInSlot.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, stackInSlot);
        }
        return itemStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return this.inventory.stillValid(player);
    }

    public String getJuiceType() {
        int juiceTypeValue = this.data.get(3);
        if (juiceTypeValue == 1) {
            return "red";
        } else if (juiceTypeValue == 0) {
            return "white";
        }
        return "";
    }

    public int getFluidLevel() {
        return this.data.get(2);
    }

    public int getScaledProgress(int maxProgress) {
        int progress = this.data.get(0);
        int totalProgress = this.data.get(1);
        if (progress == 0 || totalProgress == 0) {
            return 0;
        }
        return (int) ((double) progress / totalProgress * maxProgress);
    }
}
