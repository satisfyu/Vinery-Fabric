package net.satisfy.vinery.client.gui.handler;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.satisfy.vinery.client.gui.handler.slot.ExtendedSlot;
import net.satisfy.vinery.client.gui.handler.slot.StoveOutputSlot;
import net.satisfy.vinery.recipe.FermentationBarrelRecipe;
import net.satisfy.vinery.registry.ObjectRegistry;
import net.satisfy.vinery.registry.RecipeTypesRegistry;
import net.satisfy.vinery.registry.ScreenhandlerTypeRegistry;

public class FermentationBarrelGuiHandler extends AbstractContainerGUIScreenHandler {

    public FermentationBarrelGuiHandler(int syncId, Inventory playerInventory) {
        this(syncId, playerInventory, new SimpleContainer(6), new SimpleContainerData(2));
    }

    public FermentationBarrelGuiHandler(int syncId, Inventory playerInventory, Container inventory, ContainerData propertyDelegate) {
        super(ScreenhandlerTypeRegistry.FERMENTATION_BARREL_GUI_HANDLER.get(), syncId, 5, playerInventory, inventory, propertyDelegate);
        buildBlockEntityContainer(playerInventory, inventory);
        buildPlayerContainer(playerInventory);
    }
    
    private void buildBlockEntityContainer(Inventory playerInventory, Container inventory) {
        this.addSlot(new ExtendedSlot(inventory, 0, 79, 51, stack -> stack.is(ObjectRegistry.WINE_BOTTLE.get())));
        this.addSlot(new ExtendedSlot(inventory, 1, 33, 26, this::isIngredient));
        this.addSlot(new ExtendedSlot(inventory, 2, 51, 26, this::isIngredient));
        this.addSlot(new ExtendedSlot(inventory, 3, 33, 44, this::isIngredient));
        this.addSlot(new ExtendedSlot(inventory, 4, 51, 44, this::isIngredient));
        this.addSlot(new StoveOutputSlot(playerInventory.player, inventory, 5, 128,  35));
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
        return this.world.getRecipeManager().getAllRecipesFor(RecipeTypesRegistry.FERMENTATION_BARREL_RECIPE_TYPE.get()).stream().anyMatch(recipe -> recipe.value().getIngredients().stream().anyMatch(x -> x.test(stack)));
    }

    public int getScaledProgress(int arrowWidth) {
        final int progress = this.propertyDelegate.get(0);
        final int totalProgress = this.propertyDelegate.get(1);
        if (progress == 0) {
            return 0;
        }
        return progress * arrowWidth/ totalProgress + 1;
    }

//    @Override
//    public List<IRecipeBookGroup> getGroups() {
//        return FermentationBarrelRecipeBookGroup.FERMENTATION_GROUPS;
//    }

    @Override
    public boolean hasIngredient(Recipe<?> recipe) {
        if (recipe instanceof FermentationBarrelRecipe fermentationBarrelRecipe) {
            for (Ingredient ingredient : fermentationBarrelRecipe.getIngredients()) {
                boolean found = false;
                for (Slot slot : this.slots) {
                    if (ingredient.test(slot.getItem())) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    return false;
                }
            }
            for (Slot slot : this.slots) {
                if (slot.getItem().getItem() == ObjectRegistry.WINE_BOTTLE.get().asItem()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int getCraftingSlotCount() {
        return 5;
    }
}
