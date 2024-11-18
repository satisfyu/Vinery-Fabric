package net.satisfy.vinery.client.util;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ClientUtil {
    public static <T extends BlockEntity> void renderBlock(BlockState state, PoseStack matrices, MultiBufferSource vertexConsumers, T entity) {
        if (entity == null || state == null) return;

        Level level = entity.getLevel();
        if (level != null) {
            Minecraft.getInstance().getBlockRenderer().renderSingleBlock(state, matrices, vertexConsumers, getLightLevel(level, entity.getBlockPos()), OverlayTexture.NO_OVERLAY);
        }
    }

    public static <T extends BlockEntity> void renderBlockFromItem(BlockItem item, PoseStack matrices, MultiBufferSource vertexConsumers, T entity) {
        if (item == null || entity == null) return;

        renderBlock(item.getBlock().defaultBlockState(), matrices, vertexConsumers, entity);
    }

    public static <T extends BlockEntity> void renderItem(ItemStack stack, PoseStack matrices, MultiBufferSource vertexConsumers, T entity) {
        if (stack == null || entity == null) return;

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
