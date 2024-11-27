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
}
