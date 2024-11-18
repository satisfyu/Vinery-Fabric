package net.satisfy.vinery.forge.core.terraform.sign.block;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.satisfy.vinery.forge.core.terraform.sign.BlockSettingsLock;
import net.satisfy.vinery.forge.core.terraform.sign.TerraformSign;

public class TerraformSignBlock extends StandingSignBlock implements TerraformSign {
    private final ResourceLocation texture;

    public TerraformSignBlock(ResourceLocation texture, BlockBehaviour.Properties settings) {
        super(BlockSettingsLock.lock(settings), WoodType.OAK);
        this.texture = texture;
    }

    public ResourceLocation getTexture() {
        return this.texture;
    }
}
