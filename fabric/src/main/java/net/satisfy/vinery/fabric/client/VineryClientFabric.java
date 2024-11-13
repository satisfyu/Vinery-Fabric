package net.satisfy.vinery.fabric.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.satisfy.vinery.client.VineryClient;
import net.satisfy.vinery.fabric.client.renderer.StrawHatRenderer;
import net.satisfy.vinery.fabric.client.renderer.WinemakerBootsRenderer;
import net.satisfy.vinery.fabric.client.renderer.WinemakerChestplateRenderer;
import net.satisfy.vinery.fabric.client.renderer.WinemakerLeggingsRenderer;
import net.satisfy.vinery.registry.ObjectRegistry;

public class VineryClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        VineryClient.preInitClient();
        VineryClient.onInitializeClient();

        ArmorRenderer.register(new StrawHatRenderer(), ObjectRegistry.STRAW_HAT.get());
        ArmorRenderer.register(new WinemakerChestplateRenderer(), ObjectRegistry.WINEMAKER_APRON.get());
        ArmorRenderer.register(new WinemakerLeggingsRenderer(), ObjectRegistry.WINEMAKER_LEGGINGS.get());
        ArmorRenderer.register(new WinemakerBootsRenderer(), ObjectRegistry.WINEMAKER_BOOTS.get());    }
}
