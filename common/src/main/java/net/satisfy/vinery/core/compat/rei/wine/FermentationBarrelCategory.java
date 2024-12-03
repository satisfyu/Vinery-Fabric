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
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.satisfy.vinery.core.registry.ObjectRegistry;
import net.satisfy.vinery.core.util.JuiceUtil;

import java.util.List;
import java.util.Optional;

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

    @Override
    @SuppressWarnings("all")
    public List<Widget> setupDisplay(FermentationBarrelDisplay display, Rectangle bounds) {

        List<Widget> widgets = Lists.newArrayList();

        Point origin = new Point(bounds.getMinX() + 10, bounds.getMinY() + 10);

        widgets.add(Widgets.createRecipeBase(bounds));

        final int SLOT_SPACING = 18;
        final int ingredientCount = display.getInputEntries().size();
        if (ingredientCount >= 1)
            widgets.add(Widgets.createSlot(new Point(origin.x + (SLOT_SPACING * 0), origin.y + SLOT_SPACING + 4))
                    .entries(display.getInputEntries().get(0)).markInput());
        if (ingredientCount >= 2)
            widgets.add(Widgets.createSlot(new Point(origin.x + (SLOT_SPACING * 1), origin.y + SLOT_SPACING + 4))
                    .entries(display.getInputEntries().get(1)).markInput());
        if (ingredientCount >= 3)
            widgets.add(Widgets.createSlot(new Point(origin.x + (SLOT_SPACING * 2), origin.y + SLOT_SPACING + 4))
                    .entries(display.getInputEntries().get(2)).markInput());
        if (ingredientCount >= 4)
            widgets.add(Widgets.createSlot(new Point(origin.x + (SLOT_SPACING * 3), origin.y + SLOT_SPACING + 4))
                    .entries(display.getInputEntries().get(3)).markInput());

        widgets.add(Widgets.createSlot(new Point(origin.x, origin.y))
                .entry(EntryStacks.of(getJuiceItemForType(display.getJuiceType())))
                .disableBackground()
                .markInput());
        widgets.add(Widgets.createLabel(new Point(origin.x + (SLOT_SPACING * 3) - 8, origin.y + 5),
                Component.literal("Amount: " + display.getJuiceAmount())));

        widgets.add(Widgets.createArrow(new Point(origin.x + (SLOT_SPACING * 4), origin.y + 8))
                .animationDurationTicks(50));

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
        Optional<ItemStack> juiceItem = JuiceUtil.RED_JUICE_TAGS.entrySet().stream()
                .filter(entry -> juiceType.equals("red_" + entry.getValue()))
                .flatMap(entry -> BuiltInRegistries.ITEM.getTag(entry.getKey()).stream())
                .flatMap(HolderSet.ListBacked::stream)
                .findFirst()
                .map(ItemStack::new);

        if (juiceItem.isPresent()) {
            return juiceItem.get();
        }

        juiceItem = JuiceUtil.WHITE_JUICE_TAGS.entrySet().stream()
                .filter(entry -> juiceType.equals("white_" + entry.getValue()))
                .flatMap(entry -> BuiltInRegistries.ITEM.getTag(entry.getKey()).stream())
                .flatMap(HolderSet.ListBacked::stream)
                .findFirst()
                .map(ItemStack::new);

        if (juiceItem.isPresent()) {
            return juiceItem.get();
        }

        juiceItem = JuiceUtil.APPLE_JUICES.entrySet().stream()
                .filter(entry -> juiceType.equals(entry.getValue()))
                .map(entry -> new ItemStack(entry.getKey()))
                .findFirst();

        return juiceItem.orElse(ItemStack.EMPTY);
    }
}
