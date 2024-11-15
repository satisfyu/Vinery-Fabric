package net.satisfy.vinery.client.gui.handler;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public abstract class AbstractRecipeBookGUIScreenHandler extends AbstractPrivateRecipeScreenHandler {
    protected final Container inventory;
    protected final ContainerData propertyDelegate;
    protected final Level world;
    private final int inputSlots;

    protected AbstractRecipeBookGUIScreenHandler(MenuType<?> screenHandler, int syncId, int inputSlots, Inventory playerInventory, Container inventory, ContainerData propertyDelegate) {
        super(screenHandler, syncId);
        this.world = playerInventory.player.level();
        this.inventory = inventory;
        this.inputSlots = inputSlots;
        this.propertyDelegate = propertyDelegate;
        this.addDataSlots(this.propertyDelegate);
    }

    public boolean stillValid(Player player) {
        return this.inventory.stillValid(player);
    }

    public ItemStack quickMoveStack(Player player, int invSlot) {
        int entityInputStart = 0;
        int entityOutputSlot = this.inputSlots;
        int inventoryStart = entityOutputSlot + 1;
        int hotbarStart = inventoryStart + 27;
        int hotbarEnd = hotbarStart + 9;
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = (Slot)this.slots.get(invSlot);
        if (slot.hasItem()) {
            ItemStack itemStack2 = slot.getItem();
            Item item = itemStack2.getItem();
            itemStack = itemStack2.copy();
            if (invSlot == entityOutputSlot) {
                item.onCraftedBy(itemStack2, player.level(), player);
                if (!this.moveItemStackTo(itemStack2, inventoryStart, hotbarEnd, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(itemStack2, itemStack);
            } else if (invSlot >= 0 && invSlot < entityOutputSlot) {
                if (!this.moveItemStackTo(itemStack2, inventoryStart, hotbarEnd, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemStack2, 0, entityOutputSlot, false)) {
                if (invSlot >= inventoryStart && invSlot < hotbarStart) {
                    if (!this.moveItemStackTo(itemStack2, hotbarStart, hotbarEnd, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (invSlot >= hotbarStart && invSlot < hotbarEnd && !this.moveItemStackTo(itemStack2, inventoryStart, hotbarStart, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (itemStack2.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            }

            slot.setChanged();
            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemStack2);
            this.broadcastChanges();
        }

        return itemStack;
    }
}

