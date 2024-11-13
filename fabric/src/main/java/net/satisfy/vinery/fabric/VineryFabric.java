package net.satisfy.vinery.fabric;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.satisfy.vinery.Vinery;
import net.satisfy.vinery.fabric.config.VineryFabricConfig;
import net.satisfy.vinery.fabric.registry.VineryFabricVillagers;
import net.satisfy.vinery.fabric.world.VineryBiomeModification;
import net.satisfy.vinery.registry.CompostableRegistry;

public class VineryFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        AutoConfig.register(VineryFabricConfig.class, GsonConfigSerializer::new);

        Vinery.init();
        CompostableRegistry.registerCompostable();
        VineryFabricVillagers.init();
        VineryBiomeModification.init();
        Vinery.commonSetup();
    }
}
