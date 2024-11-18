package net.satisfy.vinery.fabric;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.satisfy.vinery.core.Vinery;
import net.satisfy.vinery.fabric.config.VineryFabricConfig;
import net.satisfy.vinery.fabric.core.registry.VineryFabricVillagers;
import net.satisfy.vinery.fabric.core.world.VineryBiomeModification;
import net.satisfy.vinery.core.registry.CompostableRegistry;

public class VineryFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        AutoConfig.register(VineryFabricConfig.class, GsonConfigSerializer::new);

        VineryFabricVillagers.registerPOIAndProfession();

        Vinery.init();
        CompostableRegistry.registerCompostable();
        VineryBiomeModification.init();
        Vinery.commonSetup();

        ServerLifecycleEvents.SERVER_STARTED.register(VineryFabricVillagers::init);
    }
}
