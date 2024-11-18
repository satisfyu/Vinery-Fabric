package net.satisfy.vinery.terraform.boat;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;

public class TerraformBoatTypeRegistry {
    private static final Map<ResourceLocation, TerraformBoatType> INSTANCE = new HashMap();

    public TerraformBoatTypeRegistry() {
    }

    public static void register(ResourceLocation location, TerraformBoatType type) {
        INSTANCE.put(location, type);
    }

    public static TerraformBoatType get(ResourceLocation location) {
        return (TerraformBoatType)INSTANCE.get(location);
    }

    public static Set<Map.Entry<ResourceLocation, TerraformBoatType>> entrySet() {
        return INSTANCE.entrySet();
    }

    public static Set<ResourceLocation> getIds() {
        return INSTANCE.keySet();
    }

    public static ResourceLocation getId(TerraformBoatType type) {
        Iterator var1 = getIds().iterator();

        ResourceLocation location;
        do {
            if (!var1.hasNext()) {
                throw new NullPointerException("Couldn't find BoatType");
            }

            location = (ResourceLocation)var1.next();
        } while(!get(location).equals(type));

        return location;
    }
}

