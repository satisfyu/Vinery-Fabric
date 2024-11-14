package net.satisfy.vinery.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.satisfy.vinery.client.gui.handler.AbstractPrivateRecipeScreenHandler;

@Environment(EnvType.CLIENT)
public abstract class AbstractRecipeBookGUIScreen<T extends AbstractPrivateRecipeScreenHandler> extends AbstractContainerScreen<T> {
    private final ResourceLocation BACKGROUND;
    private boolean narrow;

    public AbstractRecipeBookGUIScreen(T handler, Inventory inventory, Component title, ResourceLocation background) {
        super(handler, inventory, title);
        this.BACKGROUND = background;
    }

    protected void init() {
        super.init();
        this.narrow = this.width < 379;
        this.titleLabelX += 20;
    }

    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        this.renderBackground(guiGraphics);

        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    protected void renderBg(GuiGraphics guiGraphics, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, this.BACKGROUND);
        int posX = this.leftPos;
        int posY = this.topPos;
        guiGraphics.blit(this.BACKGROUND, posX, posY, 0, 0, this.imageWidth - 1, this.imageHeight);
        this.renderProgressArrow(guiGraphics);
        this.renderBurnIcon(guiGraphics, posX, posY);
    }

    protected void renderProgressArrow(GuiGraphics guiGraphics) {
    }

    protected void renderBurnIcon(GuiGraphics guiGraphics, int posX, int posY) {
    }

    protected void containerTick() {
        super.containerTick();
    }

//    public boolean mouseClicked(double mouseX, double mouseY, int button) {
//    }

    protected void slotClicked(Slot slot, int slotId, int button, ClickType actionType) {
        super.slotClicked(slot, slotId, button, actionType);
    }

//    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
//        return !this.recipeBook.keyPressed(keyCode, scanCode, modifiers) && super.keyPressed(keyCode, scanCode, modifiers);
//    }

//    protected boolean hasClickedOutside(double mouseX, double mouseY, int left, int top, int button) {
//        boolean bl = mouseX < (double)left || mouseY < (double)top || mouseX >= (double)(left + this.imageWidth) || mouseY >= (double)(top + this.imageHeight);
//        return this.recipeBook.isClickOutsideBounds(mouseX, mouseY, this.leftPos, this.topPos, this.imageWidth, this.imageHeight) && bl;
//    }

//    public boolean charTyped(char chr, int modifiers) {
//        return this.recipeBook.charTyped(chr, modifiers) || super.charTyped(chr, modifiers);
//    }

    public void removed() {
        super.removed();
    }

//    public ResourceLocation getRecipeButtonTexture() {
//        return RECIPE_BUTTON_TEXTURE;
//    }
}

