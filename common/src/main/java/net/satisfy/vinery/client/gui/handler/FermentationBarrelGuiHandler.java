package net.satisfy.vinery.client.gui.handler;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.satisfy.vinery.client.gui.handler.slot.ExtendedSlot;
import net.satisfy.vinery.client.gui.handler.slot.FermentationBarrelOutputSlot;
import net.satisfy.vinery.core.registry.ObjectRegistry;
import net.satisfy.vinery.core.registry.RecipeTypesRegistry;
import net.satisfy.vinery.core.registry.ScreenhandlerTypeRegistry;
import org.jetbrains.annotations.NotNull;

public class FermentationBarrelGuiHandler extends AbstractContainerMenu {

    protected final Level world;
    protected final Container inventory;
    protected final ContainerData propertyDelegate;

    public FermentationBarrelGuiHandler(int syncId, Inventory playerInventory) {
        this(syncId, playerInventory, new SimpleContainer(6), new SimpleContainerData(2));
    }

    public FermentationBarrelGuiHandler(int syncId, Inventory playerInventory, Container inventory, ContainerData propertyDelegate) {
        super(ScreenhandlerTypeRegistry.FERMENTATION_BARREL_GUI_HANDLER.get(), syncId);
        this.inventory = inventory;
        this.propertyDelegate = propertyDelegate;
        this.world = playerInventory.player.level();
        this.addDataSlots(this.propertyDelegate);
        buildBlockEntityContainer(playerInventory, inventory);
        buildPlayerContainer(playerInventory);
    }

    private void buildBlockEntityContainer(Inventory playerInventory, Container inventory) {
        //TODO: Change this to TagRegistry.GRAPEJUICE
        this.addSlot(new ExtendedSlot(inventory, 0, 39, 17, stack -> stack.is(ObjectRegistry.WINE_BOTTLE.get())));
        this.addSlot(new ExtendedSlot(inventory, 1, 67, 58, this::isIngredient));
        this.addSlot(new ExtendedSlot(inventory, 2, 85, 58, this::isIngredient));
        this.addSlot(new ExtendedSlot(inventory, 3, 103, 58, this::isIngredient));
        this.addSlot(new ExtendedSlot(inventory, 4, 121, 58, this::isIngredient));
        this.addSlot(new FermentationBarrelOutputSlot(playerInventory.player, inventory, 5, 103, 17));
    }

    private void buildPlayerContainer(Inventory playerInventory) {
        int i;
        for (i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    private boolean isIngredient(ItemStack stack) {
        return this.world.getRecipeManager()
                .getAllRecipesFor(RecipeTypesRegistry.FERMENTATION_BARREL_RECIPE_TYPE.get())
                .stream()
                .anyMatch(recipe -> recipe.getIngredients().stream().anyMatch(x -> x.test(stack)));
    }

    public int getScaledProgress(int arrowWidth) {
        final int progress = this.propertyDelegate.get(0);
        final int totalProgress = this.propertyDelegate.get(1);
        if (progress == 0 || totalProgress == 0) {
            return 0;
        }
        return progress * arrowWidth / totalProgress + 1;
    }

    @Override
    public @NotNull ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack stackInSlot = slot.getItem();
            itemStack = stackInSlot.copy();
            int containerSize = this.inventory.getContainerSize();
            if (index < containerSize) {
                if (!this.moveItemStackTo(stackInSlot, containerSize, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (!this.moveItemStackTo(stackInSlot, 0, containerSize, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (stackInSlot.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
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
}
