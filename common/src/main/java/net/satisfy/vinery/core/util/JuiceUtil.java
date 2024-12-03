package net.satisfy.vinery.core.util;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.satisfy.vinery.core.registry.ObjectRegistry;
import net.satisfy.vinery.core.registry.TagRegistry;

import java.util.HashMap;
import java.util.Map;

public class JuiceUtil {
    public static final Map<TagKey<Item>, String> RED_JUICE_TAGS = new HashMap<>();
    public static final Map<TagKey<Item>, String> WHITE_JUICE_TAGS = new HashMap<>();
    public static final Map<Item, String> APPLE_JUICES = new HashMap<>();

    static {
        addRedJuice(TagRegistry.RED_GRAPEJUICE, "general");
        addRedJuice(TagRegistry.RED_SAVANNA_GRAPEJUICE, "savanna");
        addRedJuice(TagRegistry.RED_TAIGA_GRAPEJUICE, "taiga");
        addRedJuice(TagRegistry.RED_JUNGLE_GRAPEJUICE, "jungle");
        addRedJuice(TagRegistry.CRIMSON_GRAPEJUICE, "crimson");

        addWhiteJuice(TagRegistry.WHITE_GRAPEJUICE, "general");
        addWhiteJuice(TagRegistry.WHITE_SAVANNA_GRAPEJUICE, "savanna");
        addWhiteJuice(TagRegistry.WHITE_TAIGA_GRAPEJUICE, "taiga");
        addWhiteJuice(TagRegistry.WHITE_JUNGLE_GRAPEJUICE, "jungle");
        addWhiteJuice(TagRegistry.WARPED_GRAPEJUICE, "warped");

        addAppleJuice(ObjectRegistry.APPLE_JUICE.get());
    }

    private static void addRedJuice(TagKey<Item> tag, String region) {
        RED_JUICE_TAGS.put(tag, region);
    }

    private static void addWhiteJuice(TagKey<Item> tag, String region) {
        WHITE_JUICE_TAGS.put(tag, region);
    }

    private static void addAppleJuice(Item item) {
        APPLE_JUICES.put(item, "apple");
    }

    public static boolean isJuice(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }
        return isRedJuice(stack) || isWhiteJuice(stack) || isAppleJuice(stack);
    }

    private static boolean isRedJuice(ItemStack stack) {
        for (TagKey<Item> tag : RED_JUICE_TAGS.keySet()) {
            if (stack.is(tag)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isWhiteJuice(ItemStack stack) {
        for (TagKey<Item> tag : WHITE_JUICE_TAGS.keySet()) {
            if (stack.is(tag)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isAppleJuice(ItemStack stack) {
        return APPLE_JUICES.containsKey(stack.getItem());
    }

    public static String getJuiceType(ItemStack stack) {
        if (!isJuice(stack)) {
            return "";
        }

        for (Map.Entry<TagKey<Item>, String> entry : RED_JUICE_TAGS.entrySet()) {
            if (stack.is(entry.getKey())) {
                return "red_" + entry.getValue();
            }
        }

        for (Map.Entry<TagKey<Item>, String> entry : WHITE_JUICE_TAGS.entrySet()) {
            if (stack.is(entry.getKey())) {
                return "white_" + entry.getValue();
            }
        }

        String region = APPLE_JUICES.get(stack.getItem());
        if (region != null) {
            return region;
        }

        return "";
    }
}
