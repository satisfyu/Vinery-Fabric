package net.satisfy.vinery.client.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.satisfy.vinery.client.gui.handler.FermentationBarrelGuiHandler;
import net.satisfy.vinery.util.VineryIdentifier;

@Environment(EnvType.CLIENT)
public class FermentationBarrelGui extends AbstractGUIScreen<FermentationBarrelGuiHandler> {

    public static final ResourceLocation BACKGROUND = VineryIdentifier.of("textures/gui/barrel_gui.png");

    public static final int ARROW_X = 94;
    public static final int ARROW_Y = 37;

    public FermentationBarrelGui(FermentationBarrelGuiHandler abstractContainerMenu, Inventory inventory, Component component) {
        super(abstractContainerMenu, inventory, component, BACKGROUND);
    }

    @Override
    protected void init() {
        super.init();
        titleLabelX += 20;
    }

    @Override
    protected void renderProgressArrow(GuiGraphics guiGraphics) {
        int progress = this.menu.getScaledProgress(23);
        guiGraphics.blit(BACKGROUND, leftPos + ARROW_X, topPos + ARROW_Y, 177, 17, progress, 10);
    }

}