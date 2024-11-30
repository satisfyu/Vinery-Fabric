package net.satisfy.vinery.forge.client;

import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.RegisterEvent;
import net.satisfy.vinery.core.Vinery;
import net.satisfy.vinery.client.VineryClient;
import net.satisfy.vinery.core.entity.DarkCherryBoatEntity;

@Mod.EventBusSubscriber(modid = Vinery.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class VineryClientForge {

    @SubscribeEvent
    public static void onClientSetup(RegisterEvent event) {
        VineryClient.preInitClient();
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        VineryClient.onInitializeClient();
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        for (DarkCherryBoatEntity.Type type : DarkCherryBoatEntity.Type.values()) {
            event.registerLayerDefinition(new ModelLayerLocation(new ResourceLocation(Vinery.MOD_ID, type.getModelLocation()), "main"), BoatModel::createBodyModel);
            event.registerLayerDefinition(new ModelLayerLocation(new ResourceLocation(Vinery.MOD_ID, type.getChestModelLocation()), "main"), ChestBoatModel::createBodyModel);
        }
    }
}
