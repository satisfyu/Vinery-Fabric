package net.satisfy.vinery;

import de.cristelknight.doapi.DoApiEP;
import dev.architectury.hooks.item.tool.AxeItemHooks;
import dev.architectury.hooks.item.tool.ShovelItemHooks;
import dev.architectury.registry.fuel.FuelRegistry;
import net.minecraft.world.level.block.Blocks;
import net.satisfy.vinery.event.EventHandler;
import net.satisfy.vinery.registry.*;
import net.satisfy.vinery.util.VineryIdentifier;
import net.satisfy.vinery.world.VineryFeatures;

public class Vinery {
    public static final String MOD_ID = "vinery";

    public static void init() {
        ObjectRegistry.init();
        EntityTypeRegistry.init();
        BoatAndSignRegistry.init();
        ScreenhandlerTypeRegistry.init();
        RecipeTypesRegistry.init();
        VineryFeatures.init();
        SoundEventRegistry.init();
        EventHandler.register();
        TabRegistry.init();

        DoApiEP.registerBuiltInPack(Vinery.MOD_ID, new VineryIdentifier("bushy_leaves"), false);
    }

    public static void commonSetup() {
        FlammableBlockRegistry.init();
        GrapeTypeRegistry.addGrapeAttributes();

        FuelRegistry.register(300, ObjectRegistry.DARK_CHERRY_FENCE.get(), ObjectRegistry.DARK_CHERRY_FENCE_GATE.get(), ObjectRegistry.STACKABLE_LOG.get(), ObjectRegistry.FERMENTATION_BARREL.get());

        AxeItemHooks.addStrippable(ObjectRegistry.DARK_CHERRY_LOG.get(), ObjectRegistry.STRIPPED_DARK_CHERRY_LOG.get());
        AxeItemHooks.addStrippable(ObjectRegistry.DARK_CHERRY_WOOD.get(), ObjectRegistry.STRIPPED_DARK_CHERRY_WOOD.get());
        AxeItemHooks.addStrippable(ObjectRegistry.APPLE_LOG.get(), Blocks.STRIPPED_OAK_LOG);
        AxeItemHooks.addStrippable(ObjectRegistry.APPLE_WOOD.get(), Blocks.STRIPPED_OAK_WOOD);
        
        ShovelItemHooks.addFlattenable(ObjectRegistry.GRASS_SLAB.get(), Blocks.DIRT_PATH.defaultBlockState());
    }
}
