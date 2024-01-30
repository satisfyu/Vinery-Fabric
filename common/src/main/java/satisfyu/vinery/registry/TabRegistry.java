package satisfyu.vinery.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.mehvahdjukaar.moonlight.api.set.wood.WoodType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import satisfyu.vinery.Vinery;

import static com.mojang.serialization.codecs.RecordCodecBuilder.build;

public class TabRegistry {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Vinery.MOD_ID, Registries.CREATIVE_MODE_TAB);

    public static final RegistrySupplier<CreativeModeTab> VINERY_TAB = CREATIVE_MODE_TABS.register("vinery", () -> CreativeModeTab.builder(CreativeModeTab.Row.TOP, 1)
            .icon(() -> new ItemStack(ObjectRegistry.JUNGLE_RED_GRAPE.get()))
            .title(Component.translatable("creativetab.vinery.tab"))
            .displayItems((parameters, out) -> {
                out.accept(ObjectRegistry.CHERRY.get());
                out.accept(ObjectRegistry.ROTTEN_CHERRY.get());
                out.accept(ObjectRegistry.RED_GRAPE_SEEDS.get());
                out.accept(ObjectRegistry.RED_GRAPE.get());
                out.accept(ObjectRegistry.WHITE_GRAPE_SEEDS.get());
                out.accept(ObjectRegistry.WHITE_GRAPE.get());
                out.accept(ObjectRegistry.SAVANNA_RED_GRAPE_SEEDS.get());
                out.accept(ObjectRegistry.SAVANNA_RED_GRAPE.get());
                out.accept(ObjectRegistry.SAVANNA_WHITE_GRAPE_SEEDS.get());
                out.accept(ObjectRegistry.SAVANNA_WHITE_GRAPE.get());
                out.accept(ObjectRegistry.TAIGA_RED_GRAPE_SEEDS.get());
                out.accept(ObjectRegistry.TAIGA_RED_GRAPE.get());
                out.accept(ObjectRegistry.TAIGA_WHITE_GRAPE_SEEDS.get());
                out.accept(ObjectRegistry.TAIGA_WHITE_GRAPE.get());
                out.accept(ObjectRegistry.JUNGLE_RED_GRAPE_SEEDS.get());
                out.accept(ObjectRegistry.JUNGLE_RED_GRAPE.get());
                out.accept(ObjectRegistry.JUNGLE_WHITE_GRAPE_SEEDS.get());
                out.accept(ObjectRegistry.JUNGLE_WHITE_GRAPE.get());
                out.accept(ObjectRegistry.CHERRY_SAPLING.get());
                out.accept(ObjectRegistry.APPLE_TREE_SAPLING.get());
                out.accept(ObjectRegistry.CHERRY_LEAVES.get());
                out.accept(ObjectRegistry.APPLE_LEAVES.get());
                out.accept(ObjectRegistry.WHITE_GRAPE_CRATE.get());
                out.accept(ObjectRegistry.RED_GRAPE_CRATE.get());
                out.accept(ObjectRegistry.CHERRY_CRATE.get());
                out.accept(ObjectRegistry.APPLE_CRATE.get());
                out.accept(ObjectRegistry.GRAPEVINE_POT.get());
                out.accept(ObjectRegistry.FERMENTATION_BARREL.get());
                out.accept(ObjectRegistry.APPLE_PRESS.get());
                out.accept(ObjectRegistry.CHAIR.get());
                out.accept(ObjectRegistry.TABLE.get());
                out.accept(ObjectRegistry.CHERRY_WINE_RACK_SMALL.get());
                out.accept(ObjectRegistry.CHERRY_WINE_RACK_BIG.get());
                out.accept(ObjectRegistry.CHERRY_WINE_RACK_MID.get());
                out.accept(ObjectRegistry.CHERRY_DRAWER.get());
                out.accept(ObjectRegistry.CHERRY_CABINET.get());
                out.accept(ObjectRegistry.STRIPPED_CHERRY_LOG.get());
                out.accept(ObjectRegistry.CHERRY_LOG.get());
                out.accept(ObjectRegistry.STRIPPED_CHERRY_WOOD.get());
                out.accept(ObjectRegistry.CHERRY_WOOD.get());
                out.accept(ObjectRegistry.APPLE_LOG.get());
                out.accept(ObjectRegistry.APPLE_WOOD.get());
                out.accept(ObjectRegistry.CHERRY_BEAM.get());
                out.accept(ObjectRegistry.CHERRY_PLANKS.get());
                out.accept(ObjectRegistry.CHERRY_FLOORBOARD.get());
                out.accept(ObjectRegistry.CHERRY_STAIRS.get());
                out.accept(ObjectRegistry.CHERRY_SLAB.get());
                out.accept(ObjectRegistry.CHERRY_FENCE.get());
                out.accept(ObjectRegistry.CHERRY_FENCE_GATE.get());
                out.accept(ObjectRegistry.CHERRY_BUTTON.get());
                out.accept(ObjectRegistry.CHERRY_PRESSURE_PLATE.get());
                out.accept(ObjectRegistry.CHERRY_DOOR.get());
                out.accept(BoatAndSignRegistry.CHERRY_SIGN_ITEM.get());
                out.accept(BoatAndSignRegistry.CHERRY_HANGING_SIGN_ITEM.get());
                out.accept(ObjectRegistry.BARREL.get());
                out.accept(ObjectRegistry.CHERRY_TRAPDOOR.get());
                out.accept(ObjectRegistry.WINDOW.get());
                out.accept(ObjectRegistry.LOAM.get());
                out.accept(ObjectRegistry.LOAM_STAIRS.get());
                out.accept(ObjectRegistry.LOAM_SLAB.get());
                out.accept(ObjectRegistry.COARSE_DIRT_SLAB.get());
                out.accept(ObjectRegistry.DIRT_SLAB.get());
                out.accept(ObjectRegistry.GRASS_SLAB.get());
                out.accept(ObjectRegistry.APPLE_JUICE.get());
                out.accept(ObjectRegistry.RED_GRAPEJUICE_WINE_BOTTLE.get());
                out.accept(ObjectRegistry.WHITE_GRAPEJUICE_WINE_BOTTLE.get());
                out.accept(ObjectRegistry.TAIGA_RED_GRAPEJUICE_BOTTLE.get());
                out.accept(ObjectRegistry.TAIGA_WHITE_GRAPEJUICE_BOTTLE.get());
                out.accept(ObjectRegistry.JUNGLE_RED_GRAPEJUICE_BOTTLE.get());
                out.accept(ObjectRegistry.JUNGLE_WHITE_GRAPEJUICE_BOTTLE.get());
                out.accept(ObjectRegistry.SAVANNA_RED_GRAPEJUICE_BOTTLE.get());
                out.accept(ObjectRegistry.SAVANNA_WHITE_GRAPEJUICE_BOTTLE.get());
                out.accept(ObjectRegistry.CHORUS_WINE.get());
                out.accept(ObjectRegistry.CHERRY_WINE.get());
                out.accept(ObjectRegistry.MAGNETIC_WINE.get());
                out.accept(ObjectRegistry.NOIR_WINE.get());
                out.accept(ObjectRegistry.LILITU_WINE.get());
                out.accept(ObjectRegistry.MELLOHI_WINE.get());
                out.accept(ObjectRegistry.STAL_WINE.get());
                out.accept(ObjectRegistry.STRAD_WINE.get());
                out.accept(ObjectRegistry.SOLARIS_WINE.get());
                out.accept(ObjectRegistry.BOLVAR_WINE.get());
                out.accept(ObjectRegistry.AEGIS_WINE.get());
                out.accept(ObjectRegistry.CLARK_WINE.get());
                out.accept(ObjectRegistry.CHENET_WINE.get());
                out.accept(ObjectRegistry.KELP_CIDER.get());
                out.accept(ObjectRegistry.APPLE_WINE.get());
                out.accept(ObjectRegistry.APPLE_CIDER.get());
                out.accept(ObjectRegistry.JELLIE_WINE.get());
                out.accept(ObjectRegistry.RED_WINE.get());
                out.accept(ObjectRegistry.PRAETORIAN_WINE.get());
                out.accept(ObjectRegistry.JO_SPECIAL_MIXTURE.get());
                out.accept(ObjectRegistry.CRISTEL_WINE.get());
                out.accept(ObjectRegistry.CREEPERS_CRUSH.get());
                out.accept(ObjectRegistry.VILLAGERS_FRIGHT.get());
                out.accept(ObjectRegistry.GLOWING_WINE.get());
                out.accept(ObjectRegistry.MEAD.get());
                out.accept(ObjectRegistry.BOTTLE_MOJANG_NOIR.get());
                out.accept(ObjectRegistry.EISWEIN.get());
                out.accept(ObjectRegistry.WINE_BOTTLE.get());
                out.accept(ObjectRegistry.APPLE_MASH.get());
                out.accept(ObjectRegistry.GRAPEVINE_STEM.get());
                out.accept(ObjectRegistry.STORAGE_POT.get());
                out.accept(ObjectRegistry.FLOWER_BOX.get());
                out.accept(ObjectRegistry.FLOWER_POT.get());
                out.accept(ObjectRegistry.WINE_BOX.get());
                out.accept(ObjectRegistry.SHELF.get());
                out.accept(ObjectRegistry.BIG_TABLE.get());
                out.accept(ObjectRegistry.BASKET.get());
                out.accept(ObjectRegistry.STACKABLE_LOG.get());
                out.accept(ObjectRegistry.STRAW_HAT.get());
                out.accept(ObjectRegistry.WINEMAKER_APRON.get());
                out.accept(ObjectRegistry.WINEMAKER_LEGGINGS.get());
                out.accept(ObjectRegistry.WINEMAKER_BOOTS.get());
                out.accept(ObjectRegistry.CALENDAR.get());
                out.accept(ObjectRegistry.MULE_SPAWN_EGG.get());
                out.accept(ObjectRegistry.WANDERING_WINEMAKER_SPAWN_EGG.get());
                out.accept(ObjectRegistry.OAK_WINE_RACK_SMALL.get());
                out.accept(ObjectRegistry.OAK_WINE_RACK_BIG.get());
                out.accept(ObjectRegistry.OAK_WINE_RACK_MID.get());
                out.accept(ObjectRegistry.SPRUCE_WINE_RACK_SMALL.get());
                out.accept(ObjectRegistry.SPRUCE_WINE_RACK_BIG.get());
                out.accept(ObjectRegistry.SPRUCE_WINE_RACK_MID.get());
                out.accept(ObjectRegistry.BIRCH_WINE_RACK_SMALL.get());
                out.accept(ObjectRegistry.BIRCH_WINE_RACK_BIG.get());
                out.accept(ObjectRegistry.BIRCH_WINE_RACK_MID.get());
                out.accept(ObjectRegistry.JUNGLE_WINE_RACK_SMALL.get());
                out.accept(ObjectRegistry.JUNGLE_WINE_RACK_BIG.get());
                out.accept(ObjectRegistry.JUNGLE_WINE_RACK_MID.get());
                out.accept(ObjectRegistry.ACACIA_WINE_RACK_SMALL.get());
                out.accept(ObjectRegistry.ACACIA_WINE_RACK_BIG.get());
                out.accept(ObjectRegistry.ACACIA_WINE_RACK_MID.get());
                out.accept(ObjectRegistry.DARK_OAK_WINE_RACK_SMALL.get());
                out.accept(ObjectRegistry.DARK_OAK_WINE_RACK_BIG.get());
                out.accept(ObjectRegistry.DARK_OAK_WINE_RACK_MID.get());
                out.accept(ObjectRegistry.MANGROVE_WINE_RACK_SMALL.get());
                out.accept(ObjectRegistry.MANGROVE_WINE_RACK_BIG.get());
                out.accept(ObjectRegistry.MANGROVE_WINE_RACK_MID.get());
                out.accept(ObjectRegistry.BAMBOO_WINE_RACK_SMALL.get());
                out.accept(ObjectRegistry.BAMBOO_WINE_RACK_BIG.get());
                out.accept(ObjectRegistry.BAMBOO_WINE_RACK_MID.get());
                out.accept(ObjectRegistry.MCCHERRY_WINE_RACK_SMALL.get());
                out.accept(ObjectRegistry.MCCHERRY_WINE_RACK_BIG.get());
                out.accept(ObjectRegistry.MCCHERRY_WINE_RACK_MID.get());
                for (RegistrySupplier<Block> latticeRegistrySupplier : ObjectRegistry.LATTICE_BLOCKS) {
                    out.accept(latticeRegistrySupplier.get());
                }
            })
            .build());

    public static void init() {
        CREATIVE_MODE_TABS.register();
    }
}
