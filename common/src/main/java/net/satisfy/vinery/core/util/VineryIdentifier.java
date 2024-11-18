package net.satisfy.vinery.core.util;

import net.minecraft.resources.ResourceLocation;
import net.satisfy.vinery.core.Vinery;

public class VineryIdentifier extends ResourceLocation {
    public VineryIdentifier(String path) {
        super(Vinery.MOD_ID, path);
    }
}
