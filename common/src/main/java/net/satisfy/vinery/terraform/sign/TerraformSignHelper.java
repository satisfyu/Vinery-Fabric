package net.satisfy.vinery.terraform.sign;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.satisfy.vinery.platform.PlatformHelper;

public class TerraformSignHelper {
    public TerraformSignHelper() {
    }

    public static Block getSign(ResourceLocation signTextureId) {
        return PlatformHelper.getSign(signTextureId);
    }

    public static Block getWallSign(ResourceLocation signTextureId) {
        return PlatformHelper.getWallSign(signTextureId);
    }

    public static Block getHangingSign(ResourceLocation hangingSignTextureId, ResourceLocation hangingSignGuiTextureId) {
        return PlatformHelper.getHangingSign(hangingSignTextureId, hangingSignGuiTextureId);
    }

    public static Block getWallHangingSign(ResourceLocation hangingSignTextureId, ResourceLocation hangingSignGuiTextureId) {
        return PlatformHelper.getWallHangingSign(hangingSignTextureId, hangingSignGuiTextureId);
    }

    public static void regsterSignSprite(ResourceLocation signTextureId) {
        PlatformHelper.addSignSprite(signTextureId);
    }
}

