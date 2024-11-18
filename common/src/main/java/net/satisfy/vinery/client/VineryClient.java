package net.satisfy.vinery.client;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;
import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;
import dev.architectury.registry.client.rendering.ColorHandlerRegistry;
import dev.architectury.registry.client.rendering.RenderTypeRegistry;
import dev.architectury.registry.menu.MenuRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.satisfy.vinery.client.gui.ApplePressGui;
import net.satisfy.vinery.client.gui.BasketGui;
import net.satisfy.vinery.client.gui.FermentationBarrelGui;
import net.satisfy.vinery.client.model.*;
import net.satisfy.vinery.client.render.block.BasketRenderer;
import net.satisfy.vinery.client.render.block.CompletionistBannerRenderer;
import net.satisfy.vinery.client.render.entity.MuleRenderer;
import net.satisfy.vinery.client.render.entity.WanderingWinemakerRenderer;
import net.satisfy.vinery.registry.BoatAndSignRegistry;
import net.satisfy.vinery.registry.EntityTypeRegistry;
import net.satisfy.vinery.registry.ScreenhandlerTypeRegistry;
import net.satisfy.vinery.terraform.sign.TerraformSignHelper;

import static net.satisfy.vinery.registry.ObjectRegistry.*;

@Environment(EnvType.CLIENT)
public class VineryClient {
    public static void onInitializeClient() {
        RenderTypeRegistry.register(RenderType.cutout(),
                RED_GRAPE_BUSH.get(), WHITE_GRAPE_BUSH.get(), DARK_CHERRY_DOOR.get(), FERMENTATION_BARREL.get(),
                MELLOHI_WINE.get(), CLARK_WINE.get(), BOLVAR_WINE.get(), CHERRY_WINE.get(),
                LILITU_WINE.get(), CHENET_WINE.get(), NOIR_WINE.get(), APPLE_CIDER.get(),
                APPLE_WINE.get(), SOLARIS_WINE.get(), JELLIE_WINE.get(), AEGIS_WINE.get(), KELP_CIDER.get(),
                SAVANNA_RED_GRAPE_BUSH.get(), SAVANNA_WHITE_GRAPE_BUSH.get(),
                CHORUS_WINE.get(), STAL_WINE.get(), MAGNETIC_WINE.get(), STRAD_WINE.get(), JUNGLE_WHITE_GRAPE_BUSH.get(),
                JUNGLE_RED_GRAPE_BUSH.get(), TAIGA_RED_GRAPE_BUSH.get(), TAIGA_WHITE_GRAPE_BUSH.get(),
                GRAPEVINE_STEM.get(), WINE_BOX.get(), DARK_CHERRY_WINE_RACK_MID.get(), DARK_CHERRY_WINE_RACK_BIG.get(),
                APPLE_PRESS.get(), GRASS_SLAB.get(), DARK_CHERRY_SAPLING.get(), APPLE_TREE_SAPLING.get(),
                STACKABLE_LOG.get(), APPLE_LEAVES.get(), POTTED_APPLE_TREE_SAPLING.get(), DARK_CHERRY_WINE_RACK_SMALL.get(),
                POTTED_DARK_CHERRY_TREE_SAPLING.get(), RED_WINE.get(), DARK_CHERRY_CHAIR.get(), CRISTEL_WINE.get(),
                VILLAGERS_FRIGHT.get(), EISWEIN.get(), CREEPERS_CRUSH.get(),
                GLOWING_WINE.get(), JO_SPECIAL_MIXTURE.get(), MEAD.get(), BOTTLE_MOJANG_NOIR.get(),
                DARK_CHERRY_TABLE.get(), OAK_WINE_RACK_MID.get(), DARK_OAK_WINE_RACK_MID.get(), BIRCH_WINE_RACK_MID.get(),
                SPRUCE_WINE_RACK_MID.get(), JUNGLE_WINE_RACK_MID.get(), MANGROVE_WINE_RACK_MID.get(), BAMBOO_WINE_RACK_MID.get(),
                ACACIA_WINE_RACK_MID.get(), OAK_LATTICE.get(), SPRUCE_LATTICE.get(),
                BIRCH_LATTICE.get(), DARK_OAK_LATTICE.get(), CHERRY_LATTICE.get(), BAMBOO_LATTICE.get(), ACACIA_LATTICE.get(), JUNGLE_LATTICE.get(),
                MANGROVE_LATTICE.get()
        );

        ClientStorageTypes.init();
        RenderTypeRegistry.register(RenderType.translucent(), WINDOW.get());

        ColorHandlerRegistry.registerItemColors((stack, tintIndex) -> GrassColor.get(0.5, 1.0), GRASS_SLAB);
        ColorHandlerRegistry.registerBlockColors((state,world,pos,tintIndex)->{
                    if(world== null || pos == null){
                        return -1;
                    }
                    return BiomeColors.getAverageGrassColor(world,pos);
                },  GRASS_SLAB.get()
        );

        BlockEntityRendererRegistry.register(EntityTypeRegistry.BASKET_ENTITY.get(), BasketRenderer::new);
        BlockEntityRendererRegistry.register(EntityTypeRegistry.VINERY_STANDARD.get(), CompletionistBannerRenderer::new);
        MenuRegistry.registerScreenFactory(ScreenhandlerTypeRegistry.FERMENTATION_BARREL_GUI_HANDLER.get(), FermentationBarrelGui::new);
        MenuRegistry.registerScreenFactory(ScreenhandlerTypeRegistry.APPLE_PRESS_GUI_HANDLER.get(), ApplePressGui::new);
        MenuRegistry.registerScreenFactory(ScreenhandlerTypeRegistry.BASKET_GUI_HANDLER.get(), BasketGui::new);
    }

    public static void preInitClient(){
        TerraformSignHelper.regsterSignSprite(BoatAndSignRegistry.DARK_CHERRY_SIGN_TEXTURE);
        EntityModelLayerRegistry.register(MuleModel.LAYER_LOCATION, MuleModel::getTexturedModelData);
        EntityModelLayerRegistry.register(BasketRenderer.LAYER_LOCATION, BasketRenderer::getTexturedModelData);
        EntityRendererRegistry.register(EntityTypeRegistry.MULE, MuleRenderer::new);
        EntityRendererRegistry.register(EntityTypeRegistry.WANDERING_WINEMAKER, WanderingWinemakerRenderer::new);
        EntityModelLayerRegistry.register(StrawHatModel.LAYER_LOCATION, StrawHatModel::createBodyLayer);
        EntityModelLayerRegistry.register(WinemakerChestplateModel.LAYER_LOCATION, WinemakerChestplateModel::createBodyLayer);
        EntityModelLayerRegistry.register(WinemakerLeggingsModel.LAYER_LOCATION, WinemakerLeggingsModel::createBodyLayer);
        EntityModelLayerRegistry.register(WinemakerBootsModel.LAYER_LOCATION, WinemakerBootsModel::createBodyLayer);
        EntityModelLayerRegistry.register(CompletionistBannerRenderer.LAYER_LOCATION, CompletionistBannerRenderer::createBodyLayer);
    }

    public static <T extends BlockEntity> void renderBlock(BlockState state, PoseStack matrices, MultiBufferSource vertexConsumers, T entity) {
        Level level = entity.getLevel();
        if (level != null) {
            Minecraft.getInstance().getBlockRenderer().renderSingleBlock(state, matrices, vertexConsumers, getLightLevel(level, entity.getBlockPos()), OverlayTexture.NO_OVERLAY);
        }
    }

    public static <T extends BlockEntity> void renderBlockFromItem(BlockItem item, PoseStack matrices, MultiBufferSource vertexConsumers, T entity) {
        renderBlock(item.getBlock().defaultBlockState(), matrices, vertexConsumers, entity);
    }

    public static <T extends BlockEntity> void renderItem(ItemStack stack, PoseStack matrices, MultiBufferSource vertexConsumers, T entity) {
        Level level = entity.getLevel();
        if (level != null) {
            Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.GUI, getLightLevel(level, entity.getBlockPos()), OverlayTexture.NO_OVERLAY, matrices, vertexConsumers, level, 1);
        }
    }

    public static int getLightLevel(Level world, BlockPos pos) {
        int bLight = world.getBrightness(LightLayer.BLOCK, pos);
        int sLight = world.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }
}