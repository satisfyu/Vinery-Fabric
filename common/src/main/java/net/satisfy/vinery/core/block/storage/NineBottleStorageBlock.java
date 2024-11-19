package net.satisfy.vinery.core.block.storage;

import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.satisfy.vinery.core.registry.StorageTypeRegistry;

import static net.satisfy.vinery.core.registry.ObjectRegistry.*;

public class NineBottleStorageBlock extends StorageBlock {


    public NineBottleStorageBlock(Properties settings) {
        super(settings);
    }
    @Override
    public int size(){
        return 9;
    }

    @Override
    public boolean canInsertStack(ItemStack stack) {
        return stack.getItem() == CHERRY_WINE_ITEM.get() ||
                stack.getItem() == RED_WINE_ITEM.get() ||
                stack.getItem() == STAL_WINE_ITEM.get() ||
                stack.getItem() == BOLVAR_WINE_ITEM.get() ||
                stack.getItem() == SOLARIS_WINE_ITEM.get() ||
                stack.getItem() == KELP_CIDER_ITEM.get() ||
                stack.getItem() == CLARK_WINE_ITEM.get() ||
                stack.getItem() == BOTTLE_MOJANG_NOIR_ITEM.get() ||
                stack.getItem() == VILLAGERS_FRIGHT_ITEM.get() ||
                stack.getItem() == NOIR_WINE_ITEM.get();
    }

    @Override
    public ResourceLocation type() {
        return StorageTypeRegistry.NINE_BOTTLE;
    }

    @Override
    public Direction[] unAllowedDirections() {
        return new Direction[]{Direction.DOWN, Direction.UP};
    }

    @Override
    public int getSection(Float x, Float y) {

        float l = (float) 1/3;

        int nSection;
        if (x < 0.375F) {
            nSection = 0;
        } else {
            nSection = x < 0.6875F ? 1 : 2;
        }

        int i = y >= l*2 ? 0 : y >= l ? 1 : 2;
        return nSection + i * 3;
    }
}
