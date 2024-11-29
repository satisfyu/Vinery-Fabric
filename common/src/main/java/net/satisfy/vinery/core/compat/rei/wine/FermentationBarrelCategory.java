package net.satisfy.vinery.core.compat.rei.wine;

import com.google.common.collect.Lists;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.satisfy.vinery.core.recipe.FermentationBarrelRecipe;
import net.satisfy.vinery.core.registry.ObjectRegistry;
import net.satisfy.vinery.core.util.JuiceUtil;

import java.util.List;

public class FermentationBarrelCategory implements DisplayCategory<FermentationBarrelDisplay> {

    @Override
    public CategoryIdentifier<FermentationBarrelDisplay> getCategoryIdentifier() {
        return FermentationBarrelDisplay.FERMENTATION_BARREL_DISPLAY;
    }

    @Override
    public Component getTitle() {
        return ObjectRegistry.FERMENTATION_BARREL.get().getName();
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ObjectRegistry.FERMENTATION_BARREL.get());
    }

    @Override @SuppressWarnings("all")
    public List<Widget> setupDisplay(FermentationBarrelDisplay display, Rectangle bounds) {

        List<Widget> widgets = Lists.newArrayList();

        // SET ORIGIN
        Point origin = new Point(bounds.getMinX() + 10, bounds.getMinY() + 10);

        // DRAW OUTLINE
        widgets.add(Widgets.createRecipeBase(bounds));

        // INGREDIENT SLOTS
        final int SLOT_SPACING = 18;
        final int ingredientCount = display.getInputEntries().size();
        if (ingredientCount >= 1) widgets.add(Widgets.createSlot(new Point(origin.x + (SLOT_SPACING * 0), origin.y + SLOT_SPACING + 4)).entries(display.getInputEntries().get(0)).markInput());
        if (ingredientCount >= 2) widgets.add(Widgets.createSlot(new Point(origin.x + (SLOT_SPACING * 1), origin.y + SLOT_SPACING + 4)).entries(display.getInputEntries().get(1)).markInput());
        if (ingredientCount >= 3) widgets.add(Widgets.createSlot(new Point(origin.x + (SLOT_SPACING * 2), origin.y + SLOT_SPACING + 4)).entries(display.getInputEntries().get(2)).markInput());
        if (ingredientCount >= 4) widgets.add(Widgets.createSlot(new Point(origin.x + (SLOT_SPACING * 3), origin.y + SLOT_SPACING + 4)).entries(display.getInputEntries().get(3)).markInput());

        // REQUIRED JUICE AMOUNT
        widgets.add(Widgets.createSlot(new Point(origin.x, origin.y))
                .entry(EntryStacks.of(getJuiceItemForType(display.getJuiceType())))
                .disableBackground()
                .markInput());
        widgets.add(Widgets.createLabel(new Point(origin.x + (SLOT_SPACING * 3) - 8, origin.y + 5), Component.literal("Amount: " + display.getJuiceAmount())));

        // DRAW ANIMATED ARROW
        widgets.add(Widgets.createArrow(new Point(origin.x + (SLOT_SPACING * 4), origin.y + 8)).animationDurationTicks(50));

        // OUTPUT SLOT
        widgets.add(Widgets.createResultSlotBackground(
                new Point(bounds.getMaxX() - 26 - 10, origin.y + 8))
        );
        widgets.add(Widgets.createSlot(
                new Point(bounds.getMaxX() - 26 - 10, origin.y + 8))
                .entries(display.getOutputEntries().get(0)).disableBackground().markOutput()
        );

        return widgets;
    }

    private ItemStack getJuiceItemForType(String juiceType) {
        return JuiceUtil.RED_JUICES.stream()
                .filter(item -> juiceType.equals("red_" + JuiceUtil.ITEM_REGION_MAP.get(item)))
                .findFirst()
                .map(ItemStack::new)
                .or(() -> JuiceUtil.WHITE_JUICES.stream()
                        .filter(item -> juiceType.equals("white_" + JuiceUtil.ITEM_REGION_MAP.get(item)))
                        .findFirst()
                        .map(ItemStack::new))
                .or(() -> JuiceUtil.APPLE_JUICES.stream()
                        .filter(item -> juiceType.equals("apple"))
                        .findFirst()
                        .map(ItemStack::new))
                .orElse(ItemStack.EMPTY);
    }
}
