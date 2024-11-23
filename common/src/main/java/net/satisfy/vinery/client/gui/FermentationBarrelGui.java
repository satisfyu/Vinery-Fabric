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
    private static final int FLUID_TEXTURE_X = 176;
    private static final int FLUID_TEXTURE_Y = 29;
    private static final int FLUID_WIDTH = 19; 
    private static final int FLUID_HEIGHT = 3; 
    private static final int FLUID_X = 82; 
    private static final int FLUID_Y = 44; 

    private static final int CRAFT_PROGRESS_TEXTURE_X = 176;
    private static final int CRAFT_PROGRESS_TEXTURE_Y = 0;
    private static final int CRAFT_PROGRESS_WIDTH = 11; 
    private static final int CRAFT_PROGRESS_HEIGHT = 28; 
    private static final int CRAFT_PROGRESS_GUI_X = 122; 
    private static final int CRAFT_PROGRESS_GUI_Y = 20; 
    private static final int CRAFT_PROGRESS_GUI_HEIGHT = 28; 

    public FermentationBarrelGui(FermentationBarrelGuiHandler handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
        this.imageWidth = 176;
        this.imageHeight = 166;
        this.titleLabelX = 8;
        this.titleLabelY = 6;
        this.inventoryLabelX = 8;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    protected void init() {
        super.init();
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
        
        int fluidWidth = getScaledFluidLevel();
        guiGraphics.blit(BACKGROUND, x + FLUID_X, y + FLUID_Y, FLUID_TEXTURE_X, FLUID_TEXTURE_Y, fluidWidth, FLUID_HEIGHT);

        this.renderCraftingProgress(guiGraphics, x, y);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.drawString(this.font, this.title.getString(), this.titleLabelX, this.titleLabelY, 4210752, false);
        guiGraphics.drawString(this.font, this.playerInventoryTitle.getString(), this.inventoryLabelX, this.inventoryLabelY, 4210752, false);
    }

    protected void renderCraftingProgress(GuiGraphics guiGraphics, int guiLeft, int guiTop) {
        int filledHeight = this.menu.getScaledProgress(28);
        int drawY = guiTop + CRAFT_PROGRESS_GUI_Y + (CRAFT_PROGRESS_GUI_HEIGHT - filledHeight);

        RenderSystem.setShaderTexture(0, BACKGROUND);

        guiGraphics.blit(BACKGROUND, guiLeft + CRAFT_PROGRESS_GUI_X, drawY, CRAFT_PROGRESS_TEXTURE_X, CRAFT_PROGRESS_TEXTURE_Y + (CRAFT_PROGRESS_HEIGHT - filledHeight), CRAFT_PROGRESS_WIDTH, filledHeight);
    }

    protected int getScaledFluidLevel() {
        int fluidLevel = this.menu.getFluidLevel();
        return fluidLevel * FermentationBarrelGui.FLUID_WIDTH / 100;
    }
}
