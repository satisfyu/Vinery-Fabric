package net.satisfy.vinery.core.util;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.satisfy.vinery.core.registry.ObjectRegistry;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class JuiceUtil {
    public static final Set<Item> RED_JUICES = new HashSet<>();
    public static final Set<Item> WHITE_JUICES = new HashSet<>();
    public static final Set<Item> APPLE_JUICES = new HashSet<>();
    public static final Map<Item, String> ITEM_REGION_MAP = new HashMap<>();

    static {
        addRedJuice(ObjectRegistry.RED_GRAPEJUICE.get(), "general");
        addRedJuice(ObjectRegistry.RED_SAVANNA_GRAPEJUICE.get(), "savanna");
        addRedJuice(ObjectRegistry.RED_TAIGA_GRAPEJUICE.get(), "taiga");
        addRedJuice(ObjectRegistry.RED_JUNGLE_GRAPEJUICE.get(), "jungle");

        addWhiteJuice(ObjectRegistry.WHITE_GRAPEJUICE.get(), "general");
        addWhiteJuice(ObjectRegistry.WHITE_SAVANNA_GRAPEJUICE.get(), "savanna");
        addWhiteJuice(ObjectRegistry.WHITE_TAIGA_GRAPEJUICE.get(), "taiga");
        addWhiteJuice(ObjectRegistry.WHITE_JUNGLE_GRAPEJUICE.get(), "jungle");

        addAppleJuice(ObjectRegistry.APPLE_JUICE.get());
    }

    private static void addRedJuice(Item item, String region) {
        RED_JUICES.add(item);
        ITEM_REGION_MAP.put(item, region);
    }

    private static void addWhiteJuice(Item item, String region) {
        WHITE_JUICES.add(item);
        ITEM_REGION_MAP.put(item, region);
    }

    private static void addAppleJuice(Item item) {
        APPLE_JUICES.add(item);
        ITEM_REGION_MAP.put(item, "apple");
    }

    public static boolean isJuice(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }
        Item item = stack.getItem();
        return RED_JUICES.contains(item) || WHITE_JUICES.contains(item) || APPLE_JUICES.contains(item);
    }

    public static String getJuiceType(ItemStack stack) {
        if (!isJuice(stack)) {
            return "";
        }
        Item item = stack.getItem();
        return ITEM_REGION_MAP.getOrDefault(item, "");
    }
}
