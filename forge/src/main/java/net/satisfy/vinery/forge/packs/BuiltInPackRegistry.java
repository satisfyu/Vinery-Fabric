package net.satisfy.vinery.forge.packs;

import com.mojang.datafixers.util.Pair;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Consumer;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.Pack.Position;

public class BuiltInPackRegistry {
    public static final Map<ResourceLocation, Pair<PackResources, Boolean>> packResources = new HashMap();

    public BuiltInPackRegistry() {
    }

    public static void getPacks(Consumer<Pack> consumer, boolean client) {
        if (!BuiltInPackRegistry.packResources.isEmpty()) {
            Iterator var2 = BuiltInPackRegistry.packResources.entrySet().iterator();

            while(true) {
                while(var2.hasNext()) {
                    Map.Entry<ResourceLocation, Pair<PackResources, Boolean>> entry = (Map.Entry)var2.next();
                    Pair<PackResources, Boolean> pair = (Pair)entry.getValue();
                    PackResources packResources = (PackResources)pair.getFirst();
                    ResourceLocation id = (ResourceLocation)entry.getKey();
                    if (packResources == null) {
                        System.out.println("Pack for location: {} is null, skipping it!", id);
                    } else if (client && packResources.getNamespaces(PackType.CLIENT_RESOURCES).isEmpty()) {
                        System.out.println(packResources.packId() + " has no assets, skipping it!");
                    } else if (!client && packResources.getNamespaces(PackType.SERVER_DATA).isEmpty()) {
                        System.out.println(packResources.packId() + " has no data, skipping it!");
                    } else {
                        String var10000 = packResources.packId();
                        String var10001 = id.getNamespace();
                        Pack pack = Pack.readMetaAndCreate(var10000, Component.literal(var10001 + "/" + id.getPath()), (Boolean)pair.getSecond(), (ignored) -> {
                            return packResources;
                        }, PackType.SERVER_DATA, Position.TOP, new BuiltinResourcePackSource(id.getNamespace()));
                        if (pack != null) {
                            consumer.accept(pack);
                        } else {
                            System.out.println(packResources.packId() + " couldn't be created");
                        }
                    }
                }

                return;
            }
        }
    }
}

