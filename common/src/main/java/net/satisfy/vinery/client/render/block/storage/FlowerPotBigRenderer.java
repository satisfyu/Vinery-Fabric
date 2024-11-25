package net.satisfy.vinery.client.render.block.storage;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.satisfy.vinery.client.util.ClientUtil;
import net.satisfy.vinery.core.block.entity.StorageBlockEntity;

public class FlowerPotBigRenderer implements StorageTypeRenderer {

    @Override
    public void render(StorageBlockEntity entity, PoseStack matrices, MultiBufferSource vertexConsumers, NonNullList<ItemStack> itemStacks) {
        if (!itemStacks.isEmpty()) {
            ItemStack itemStack = itemStacks.get(0);
            if (itemStack.getItem() instanceof BlockItem blockItem) {
                BlockState state = blockItem.getBlock().defaultBlockState();
                matrices.translate(-0.5f, 0.4f, -0.5f);
                ClientUtil.renderBlock(state, matrices, vertexConsumers, entity);
                state = blockItem.getBlock().defaultBlockState().setValue(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER);
                matrices.translate(0f, 1f, 0f);
                ClientUtil.renderBlock(state, matrices, vertexConsumers, entity);
            }
        }
    }
}
