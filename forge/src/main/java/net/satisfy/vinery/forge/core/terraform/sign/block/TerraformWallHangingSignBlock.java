package net.satisfy.vinery.forge.core.terraform.sign.block;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.WallHangingSignBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.satisfy.vinery.forge.core.terraform.sign.BlockSettingsLock;
import net.satisfy.vinery.forge.core.terraform.sign.TerraformHangingSign;

public class TerraformWallHangingSignBlock extends WallHangingSignBlock implements TerraformHangingSign {
    private final ResourceLocation texture;
    private final ResourceLocation guiTexture;

    public TerraformWallHangingSignBlock(ResourceLocation texture, ResourceLocation guiTexture, BlockBehaviour.Properties settings) {
        super(BlockSettingsLock.lock(settings), WoodType.OAK);
        this.texture = texture;
        this.guiTexture = guiTexture;
    }

    public ResourceLocation getTexture() {
        return this.texture;
    }

    public ResourceLocation getGuiTexture() {
        return this.guiTexture;
    }
}
