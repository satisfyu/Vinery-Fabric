package net.satisfy.vinery.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import de.cristelknight.doapi.DoApiRL;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;

@Environment(EnvType.CLIENT)
public abstract class AbstractGUIScreen<T extends AbstractContainerMenu> extends AbstractContainerScreen<T> {
    private final ResourceLocation BACKGROUND;
    private static final ResourceLocation RECIPE_BUTTON_TEXTURE = DoApiRL.asResourceLocation("textures/gui/recipe_button.png");
    private static final WidgetSprites DOAPI_RECIPE_BUTTON = new WidgetSprites(DoApiRL.asResourceLocation("recipe_book/button"), DoApiRL.asResourceLocation("recipe_book/button_highlighted"));
    private boolean narrow;

    public AbstractGUIScreen(T handler, Inventory inventory, Component title, ResourceLocation background) {
        super(handler, inventory, title);
        this.BACKGROUND = background;
    }

    protected void init() {
        super.init();
        this.narrow = this.width < 379;
        this.titleLabelX += 20;
    }

    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        this.renderBackground(guiGraphics, mouseX, mouseY, delta);

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

    protected void slotClicked(Slot slot, int slotId, int button, ClickType actionType) {
        super.slotClicked(slot, slotId, button, actionType);
    }

    public void removed() {
        super.removed();
    }
}

