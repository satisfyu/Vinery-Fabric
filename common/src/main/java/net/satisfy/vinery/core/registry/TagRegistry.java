package net.satisfy.vinery.core.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.satisfy.vinery.core.util.VineryIdentifier;

public class TagRegistry {
    public static final TagKey<Block> CAN_NOT_CONNECT = TagKey.create(Registries.BLOCK, new VineryIdentifier("can_not_connect"));
    public static final TagKey<Item> IGNORE_BLOCK_ITEM = TagKey.create(Registries.ITEM, new VineryIdentifier("ignore_block_item"));
    public static final TagKey<Item> SMALL_BOTTLE = TagKey.create(Registries.ITEM, new VineryIdentifier("small_bottle"));
    public static final TagKey<Item> LARGE_BOTTLE = TagKey.create(Registries.ITEM, new VineryIdentifier("large_bottle"));
    public static final TagKey<Item> WHITE_GRAPEJUICE = TagKey.create(Registries.ITEM, new VineryIdentifier("white_grapejuice"));
    public static final TagKey<Item> WHITE_SAVANNA_GRAPEJUICE = TagKey.create(Registries.ITEM, new VineryIdentifier("white_savanna_grapejuice"));
    public static final TagKey<Item> WHITE_TAIGA_GRAPEJUICE = TagKey.create(Registries.ITEM, new VineryIdentifier("white_taiga_grapejuice"));
    public static final TagKey<Item> WHITE_JUNGLE_GRAPEJUICE = TagKey.create(Registries.ITEM, new VineryIdentifier("white_jungle_grapejuice"));
    public static final TagKey<Item> WARPED_GRAPEJUICE = TagKey.create(Registries.ITEM, new VineryIdentifier("warped_grapejuice"));
    public static final TagKey<Item> RED_GRAPEJUICE = TagKey.create(Registries.ITEM, new VineryIdentifier("red_grapejuice"));
    public static final TagKey<Item> RED_SAVANNA_GRAPEJUICE = TagKey.create(Registries.ITEM, new VineryIdentifier("red_savanna_grapejuice"));
    public static final TagKey<Item> RED_TAIGA_GRAPEJUICE = TagKey.create(Registries.ITEM, new VineryIdentifier("red_taiga_grapejuice"));
    public static final TagKey<Item> RED_JUNGLE_GRAPEJUICE = TagKey.create(Registries.ITEM, new VineryIdentifier("red_jungle_grapejuice"));
    public static final TagKey<Item> CRIMSON_GRAPEJUICE = TagKey.create(Registries.ITEM, new VineryIdentifier("crimson_grapejuice"));
}
