package net.satisfy.vinery.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.satisfy.vinery.client.gui.handler.FermentationBarrelGuiHandler;
import net.satisfy.vinery.core.util.VineryIdentifier;

@Environment(EnvType.CLIENT)
public class FermentationBarrelGui extends AbstractContainerScreen<FermentationBarrelGuiHandler> {

    public static final ResourceLocation BACKGROUND = new VineryIdentifier("textures/gui/aging_barrel_gui.png");

    public static final int ARROW_X = 94;
    public static final int ARROW_Y = 37;

    public FermentationBarrelGui(FermentationBarrelGuiHandler handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        this.imageWidth = 176; 
        this.imageHeight = 166; 
        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        this.renderBackground(guiGraphics); 
        super.render(guiGraphics, mouseX, mouseY, delta); 
        this.renderTooltip(guiGraphics, mouseX, mouseY); 
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, BACKGROUND);
        int x = this.leftPos;
        int y = this.topPos;
        guiGraphics.blit(BACKGROUND, x, y, 0, 0, this.imageWidth, this.imageHeight);

        this.renderProgressArrow(guiGraphics);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 4210752, false);
        guiGraphics.drawString(this.font, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY, 4210752, false);
    }

    protected void renderProgressArrow(GuiGraphics guiGraphics) {
        int progress = this.menu.getScaledProgress(23);
        guiGraphics.blit(BACKGROUND, leftPos + ARROW_X, topPos + ARROW_Y, 177, 17, progress, 10);
    }
}
