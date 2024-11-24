package net.satisfy.vinery.core.util;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.satisfy.vinery.core.registry.ObjectRegistry;

import java.util.HashSet;
import java.util.Set;

public class JuiceUtil {
    public static final Set<Item> RED_JUICES = new HashSet<>();
    public static final Set<Item> WHITE_JUICES = new HashSet<>();

    static {
        RED_JUICES.add(ObjectRegistry.RED_GRAPEJUICE.get());
        RED_JUICES.add(ObjectRegistry.RED_SAVANNA_GRAPEJUICE.get());
        RED_JUICES.add(ObjectRegistry.RED_TAIGA_GRAPEJUICE.get());
        RED_JUICES.add(ObjectRegistry.RED_JUNGLE_GRAPEJUICE.get());
        WHITE_JUICES.add(ObjectRegistry.WHITE_GRAPEJUICE.get());
        WHITE_JUICES.add(ObjectRegistry.WHITE_SAVANNA_GRAPEJUICE.get());
        WHITE_JUICES.add(ObjectRegistry.WHITE_TAIGA_GRAPEJUICE.get());
        WHITE_JUICES.add(ObjectRegistry.WHITE_JUNGLE_GRAPEJUICE.get());
    }

    public static String getJuiceType(ItemStack stack) {
        Item item = stack.getItem();
        if (RED_JUICES.contains(item)) {
            return "red_" + getRegion(item);
        } else if (WHITE_JUICES.contains(item)) {
            return "white_" + getRegion(item);
        }
        return "";
    }

    private static String getRegion(Item item) {
        if (item == ObjectRegistry.RED_SAVANNA_GRAPEJUICE.get() || item == ObjectRegistry.WHITE_SAVANNA_GRAPEJUICE.get()) {
            return "savanna";
        } else if (item == ObjectRegistry.RED_TAIGA_GRAPEJUICE.get() || item == ObjectRegistry.WHITE_TAIGA_GRAPEJUICE.get()) {
            return "taiga";
        } else if (item == ObjectRegistry.RED_JUNGLE_GRAPEJUICE.get() || item == ObjectRegistry.WHITE_JUNGLE_GRAPEJUICE.get()) {
            return "jungle";
        }
        return "general";
    }

    public static boolean isJuice(ItemStack stack) {
        return RED_JUICES.contains(stack.getItem()) || WHITE_JUICES.contains(stack.getItem());
    }
}
