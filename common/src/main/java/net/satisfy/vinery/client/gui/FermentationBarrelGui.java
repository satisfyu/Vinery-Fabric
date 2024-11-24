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

    private static final int WHITE_FLUID_TEXTURE_X = 176;
    private static final int WHITE_FLUID_TEXTURE_Y_START = 33;
    private static final int WHITE_FLUID_HEIGHT = 4;

    private static final int RED_FLUID_TEXTURE_X = 176;
    private static final int RED_FLUID_TEXTURE_Y_START = 29;
    private static final int RED_FLUID_HEIGHT = 4;

    private static final int FLUID_WIDTH = 20;
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

        if (isMouseOverFluidArea(mouseX, mouseY)) {
            String juiceType = this.menu.getJuiceType();
            int fluidLevel = this.menu.getFluidLevel();

            Component tooltip;
            if ("red".equals(juiceType)) {
                tooltip = Component.translatable("tooltip.vinery.fermentation_barrel.red_juice_with_percentage", fluidLevel);
            } else {
                tooltip = Component.translatable("tooltip.vinery.fermentation_barrel.white_juice_with_percentage", fluidLevel);
            }

            guiGraphics.renderTooltip(this.font, tooltip, mouseX, mouseY);
        }

        if (isMouseOverCraftingTimeArea(mouseX, mouseY)) {
            int remainingTicks = this.menu.getScaledProgress(this.menu.data.get(1)) - this.menu.data.get(0);
            int seconds = remainingTicks / 20;
            int minutes = seconds / 60;
            seconds %= 60;

            String formattedTime = String.format("%d:%02d Seconds", minutes, seconds);
            Component tooltip = Component.translatable("tooltip.vinery.fermentation_barrel.crafting_time", formattedTime);
            guiGraphics.renderTooltip(this.font, tooltip, mouseX, mouseY);
        }
    }

    private boolean isMouseOverFluidArea(int mouseX, int mouseY) {
        int fluidAreaLeft = this.leftPos + 80; 
        int fluidAreaTop = this.topPos + 42;  
        int fluidAreaRight = this.leftPos + 103; 
        int fluidAreaBottom = this.topPos + 49; 

        return mouseX >= fluidAreaLeft && mouseX <= fluidAreaRight &&
                mouseY >= fluidAreaTop && mouseY <= fluidAreaBottom;
    }

    private boolean isMouseOverCraftingTimeArea(int mouseX, int mouseY) {
        int craftingTimeAreaLeft = this.leftPos + 121;
        int craftingTimeAreaTop = this.topPos + 20;
        int craftingTimeAreaRight = this.leftPos + 133;
        int craftingTimeAreaBottom = this.topPos + 48;

        return mouseX >= craftingTimeAreaLeft && mouseX <= craftingTimeAreaRight &&
                mouseY >= craftingTimeAreaTop && mouseY <= craftingTimeAreaBottom;
    }

    @Override
    @SuppressWarnings("all")
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, BACKGROUND);
        int x = this.leftPos;
        int y = this.topPos;
        guiGraphics.blit(BACKGROUND, x, y, 0, 0, this.imageWidth, this.imageHeight);

        int fluidWidth = getScaledFluidLevel();

        String juiceType = this.menu.getJuiceType();
        int textureX = WHITE_FLUID_TEXTURE_X;
        int textureY = WHITE_FLUID_TEXTURE_Y_START;
        int fluidHeight = WHITE_FLUID_HEIGHT;

        if ("red".equals(juiceType)) {
            textureX = RED_FLUID_TEXTURE_X;
            textureY = RED_FLUID_TEXTURE_Y_START;
            fluidHeight = RED_FLUID_HEIGHT;
        }

        guiGraphics.blit(BACKGROUND, x + FLUID_X, y + FLUID_Y, textureX, textureY, fluidWidth, fluidHeight);

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
