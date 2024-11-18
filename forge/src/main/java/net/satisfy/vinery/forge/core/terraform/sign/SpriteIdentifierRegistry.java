package net.satisfy.vinery.forge.core.terraform.sign;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;

public class SpriteIdentifierRegistry {
    public static final SpriteIdentifierRegistry INSTANCE = new SpriteIdentifierRegistry();
    private final List<Material> identifiers = new ArrayList();
    private final List<ResourceLocation> locations = new ArrayList();

    private SpriteIdentifierRegistry() {
    }

    public void addIdentifier(ResourceLocation sprite) {
        this.locations.add(sprite);
    }

    public Collection<Material> getIdentifiers() {
        if (!this.locations.isEmpty()) {
            Iterator var1 = this.locations.iterator();

            while(var1.hasNext()) {
                ResourceLocation location = (ResourceLocation)var1.next();
                this.identifiers.add(new Material(Sheets.SIGN_SHEET, location));
            }

            this.locations.clear();
        }

        return Collections.unmodifiableList(this.identifiers);
    }
}
