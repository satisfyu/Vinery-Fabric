package net.satisfy.vinery.client.render.block.storage;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.satisfy.vinery.core.block.storage.WineBottleBlock;
import net.satisfy.vinery.core.block.entity.StorageBlockEntity;
import net.satisfy.vinery.client.util.ClientUtil;

@Environment(EnvType.CLIENT)
public class BigBottleRenderer implements StorageTypeRenderer {
    @Override
    public void render(StorageBlockEntity entity, PoseStack matrices, MultiBufferSource vertexConsumers, NonNullList<ItemStack> itemStacks) {
        matrices.translate(-0.4, 0.07, -0.5);
        matrices.scale(0.8f, 0.8f, 0.9f);
        ItemStack stack = itemStacks.get(0);
        if (!stack.isEmpty() && stack.getItem() instanceof BlockItem blockItem) {
            ClientUtil.renderBlock(blockItem.getBlock().defaultBlockState().setValue(WineBottleBlock.FAKE_MODEL, false), matrices, vertexConsumers, entity);
        }
    }
}
