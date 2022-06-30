package daniking.vinery.registry;

import daniking.vinery.GrapeBushSeedItem;
import daniking.vinery.Vinery;
import daniking.vinery.VineryIdentifier;
import daniking.vinery.block.*;
import daniking.vinery.util.GrapevineType;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.FoodComponents;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class ObjectRegistry {

    private static final Map<Identifier, Item> ITEMS = new LinkedHashMap<>();
    private static final Map<Identifier, Block> BLOCKS = new LinkedHashMap<>();

    // Red Grapes
    public static final Block RED_GRAPE_BUSH = register("red_grape_bush", new GrapeBush(getBushSettings(), GrapevineType.RED), false);
    public static final Item RED_GRAPE = register("red_grape", new GrapeItem(getSettings().food(FoodComponents.SWEET_BERRIES), GrapevineType.RED));
    public static final Item RED_GRAPE_SEEDS = register("red_grape_seeds", new GrapeBushSeedItem(RED_GRAPE_BUSH, getSettings(), GrapevineType.RED));

    // White Grapes
    public static final Block WHITE_GRAPE_BUSH = register("white_grape_bush", new GrapeBush(getBushSettings(), GrapevineType.WHITE), false);
    public static final Item WHITE_GRAPE = register("white_grape", new GrapeItem(getSettings().food(FoodComponents.SWEET_BERRIES), GrapevineType.WHITE));
    public static final Item WHITE_GRAPE_SEEDS = register("white_grape_seeds", new GrapeBushSeedItem(WHITE_GRAPE_BUSH, getSettings(), GrapevineType.WHITE));

    // Vines
//    public static final Block EMPTY_RED_VINE = register("empty_red_vine", new RedVineBlock(FabricBlockSettings.copyOf(Blocks.VINE), RedVineBlock.Variant.Empty));
//    public static final Block RED_VINE = register("red_vine", new RedVineBlock(FabricBlockSettings.copyOf(Blocks.VINE), RedVineBlock.Variant.A));
//    public static final Block RED_VINE_VARIANT_B = register("red_vine_variant_b", new RedVineBlock(FabricBlockSettings.copyOf(Blocks.VINE), RedVineBlock.Variant.B), false);
//    public static final Block RED_VINE_VARIANT_C = register("red_vine_variant_c", new RedVineBlock(FabricBlockSettings.copyOf(Blocks.VINE), RedVineBlock.Variant.C), false);
    // Rocks
    public static final Block ROCKS = register("rocks", new RockBlock(getRockSettings()));
    public static final Block ROCKS_VARIANT_B = register("rocks_variant_b", new RockBlock(getRockSettings()), false);
    public static final Block ROCKS_VARIANT_C = register("rocks_variant_c", new RockBlock(getRockSettings()), false);

    // Grass Flowers
    public static final Block RED_GRASS_FLOWER = register("red_grass_flower", new GrassFlowerBlock(getGrassSettings(), GrassFlowerBlock.Type.RED));
    public static final Block RED_GRASS_FLOWER_VARIANT_B = register("red_grass_flower_variant_b", new GrassFlowerBlock(getGrassSettings(), GrassFlowerBlock.Type.RED), false);
    public static final Block PINK_GRASS_FLOWER = register("pink_grass_flower", new GrassFlowerBlock(getGrassSettings(), GrassFlowerBlock.Type.PINK));
    public static final Block PINK_GRASS_FLOWER_VARIANT_B = register("pink_grass_flower_variant_b", new GrassFlowerBlock(getGrassSettings(), GrassFlowerBlock.Type.PINK), false);
    public static final Block WHITE_GRASS_FLOWER = register("white_grass_flower", new GrassFlowerBlock(getGrassSettings(), GrassFlowerBlock.Type.WHITE));

    public static final Block GRAPEVINE_STEM = register("grapevine_stem", new GrapevineStemBlock(getGrapevineSettings()));

    public static final Block GRAPEVINE_LEAVES = register("grapevine_leaves", new GrapevineLeaves(FabricBlockSettings.copyOf(Blocks.OAK_LEAVES)));

    public static final Block GRAPEVINE_POT =  register("grapevine_pot", new GrapevinePotBlock(FabricBlockSettings.copyOf(Blocks.BARREL)));
    public static final Block STOVE = register("stove", new StoveBlock(FabricBlockSettings.copyOf(Blocks.BRICKS).luminance(state -> state.get(StoveBlock.LIT) ? 13 : 0)));
    public static final Item CRUSTY_BREAD = register("crusty_bread", new Item(getSettings()));

    // Cherry
    public static final Block CHERRY_PLANKS = register("cherry_planks", new Block(FabricBlockSettings.of(Material.WOOD).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD)));

    private static <T extends Block> T register(String path, T block) {
        return register(path, block, true);
    }

    private static <T extends Block> T register(String path, T block, boolean registerItem) {
        final Identifier id = new VineryIdentifier(path);
        BLOCKS.put(id, block);
        if (registerItem) {
            ITEMS.put(id, new BlockItem(block, getSettings()));
        }
        return block;
    }

    private static <T extends Item> T register(String path, T item) {
        final Identifier id = new VineryIdentifier(path);
        ITEMS.put(id, item);
        return item;
    }

    public static void init() {
        for (Map.Entry<Identifier, Block> entry : BLOCKS.entrySet()) {
            Registry.register(Registry.BLOCK, entry.getKey(), entry.getValue());
        }
        for (Map.Entry<Identifier, Item> entry : ITEMS.entrySet()) {
            Registry.register(Registry.ITEM, entry.getKey(), entry.getValue());
        }
        FuelRegistry fuelRegistry = FuelRegistry.INSTANCE;
        FlammableBlockRegistry flammableRegistry = FlammableBlockRegistry.getDefaultInstance();
        flammableRegistry.add(CHERRY_PLANKS, 5, 20);
    }

    private static Item.Settings getSettings() {
        return new Item.Settings().group(Vinery.CREATIVE_TAB);
    }

    private static Block.Settings getVineSettings() {
        return FabricBlockSettings.copyOf(Blocks.VINE);
    }

    private static Block.Settings getBushSettings() {
        return FabricBlockSettings.copyOf(Blocks.SWEET_BERRY_BUSH);
    }

    private static AbstractBlock.Settings getRockSettings() {
        return FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY).requiresTool().strength(1.0F, 3.0F).nonOpaque();
    }

    private static AbstractBlock.Settings getGrassSettings() {
        return FabricBlockSettings.copyOf(Blocks.GRASS_BLOCK).nonOpaque();
    }

    private static AbstractBlock.Settings getGrapevineSettings() {
        return FabricBlockSettings.of(Material.WOOD).strength(2.0F).ticksRandomly().sounds(BlockSoundGroup.WOOD);
    }

    public static Map<Identifier, Block> getBlocks() {
        return Collections.unmodifiableMap(BLOCKS);
    }

    public static Map<Identifier, Item> getItems() {
        return Collections.unmodifiableMap(ITEMS);
    }

}
