package net.satisfy.vinery.core.util;

import net.satisfy.vinery.core.registry.GrapeTypeRegistry;

public class PreInit {
    public static void preInit() {
        GrapeTypeRegistry.register();
    }
}