package net.satisfy.vinery.fabric;

import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import net.satisfy.vinery.core.util.PreInit;

public class VineryFabricPre implements PreLaunchEntrypoint {
    @Override
    public void onPreLaunch() {
        PreInit.preInit();
    }
}