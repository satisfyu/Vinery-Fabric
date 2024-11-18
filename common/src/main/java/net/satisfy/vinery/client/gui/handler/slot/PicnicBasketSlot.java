package net.satisfy.vinery.client.gui.handler.slot;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.satisfy.vinery.platform.PlatformHelper;

import java.util.List;

public class PicnicBasketSlot extends Slot {
    private static List<String> basketBlacklist;

    public PicnicBasketSlot(Container container, int index, int x, int y) {
        super(container, index, x, y);
        if (basketBlacklist == null) {
            basketBlacklist = PlatformHelper.getBasketBlacklist();
        }
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        ResourceLocation itemId = BuiltInRegistries.ITEM.getKey(stack.getItem());
        return !basketBlacklist.contains(itemId.toString());
    }
}