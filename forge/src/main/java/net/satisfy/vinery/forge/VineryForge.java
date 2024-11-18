package net.satisfy.vinery.forge;

import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import net.satisfy.vinery.core.Vinery;
import net.satisfy.vinery.forge.core.config.VineryForgeConfig;
import net.satisfy.vinery.forge.core.registry.VineryForgeVillagers;
import net.satisfy.vinery.core.registry.CompostableRegistry;
import net.satisfy.vinery.core.util.PreInit;


@Mod(Vinery.MOD_ID)
public class VineryForge {
    public VineryForge() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        EventBuses.registerModEventBus(Vinery.MOD_ID, modEventBus);
        PreInit.preInit();
        Vinery.init();
        VineryForgeConfig.loadConfig(VineryForgeConfig.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve("vinery.toml").toString());
        VineryForgeVillagers.register(modEventBus);

        modEventBus.addListener(this::commonSetup);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(CompostableRegistry::registerCompostable);
        Vinery.commonSetup();
    }
}
