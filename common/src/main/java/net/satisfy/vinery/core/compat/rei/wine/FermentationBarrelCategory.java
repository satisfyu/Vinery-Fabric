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
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
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
        return Component.translatable("rei.vinery.fermentation_barrel_category");
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ObjectRegistry.FERMENTATION_BARREL.get());
    }

    @Override
    public List<Widget> setupDisplay(FermentationBarrelDisplay display, Rectangle bounds) {
        Point startPoint = new Point(bounds.getCenterX() - 55, bounds.getCenterY() - 13);
        List<Widget> widgets = Lists.newArrayList();
        widgets.add(Widgets.createRecipeBase(bounds));
        widgets.add(Widgets.createArrow(new Point(startPoint.x + 54, startPoint.y - 1)).animationDurationTicks(50));
        widgets.add(Widgets.createResultSlotBackground(new Point(startPoint.x + 90, startPoint.y)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 90, startPoint.y)).entries(display.getOutputEntries().get(0)).disableBackground().markOutput());
        for (int i = 0; i < 4; i++) {
            int x = i * 18;
            int y = -4;
            if (i > 1) {
                x = (i - 2) * 18;
                y += 18;
            }
            x -= 8;
            if (i >= display.getInputEntries().size() - 1)
                widgets.add(Widgets.createSlotBackground(new Point(startPoint.x + x, startPoint.y + y)));
            else
                widgets.add(Widgets.createSlot(new Point(startPoint.x + x, startPoint.y + y)).entries(display.getInputEntries().get(i + 1)).markInput());
        }
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 36, startPoint.y + 18))
                .entry(EntryStacks.of(getJuiceItemForType(display.getJuiceType())))
                .disableBackground()
                .markInput());
        widgets.add(Widgets.createLabel(new Point(startPoint.x + 36, startPoint.y + 40), Component.literal("Amount: " + display.getJuiceAmount())));
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
