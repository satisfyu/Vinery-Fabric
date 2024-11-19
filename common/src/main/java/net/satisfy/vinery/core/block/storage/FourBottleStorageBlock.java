package net.satisfy.vinery.core.block.storage;

import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.satisfy.vinery.core.registry.StorageTypeRegistry;

import static net.satisfy.vinery.core.registry.ObjectRegistry.*;

public class FourBottleStorageBlock extends StorageBlock {

    public FourBottleStorageBlock(Properties settings) {
        super(settings);
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
    public int size(){
        return 4;
    }


    @Override
    public ResourceLocation type() {
        return StorageTypeRegistry.FOUR_BOTTLE;
    }

    @Override
    public Direction[] unAllowedDirections() {
        return new Direction[]{Direction.DOWN, Direction.UP};
    }


    @Override
    public int getSection(Float x, Float y) {

        if (x > 0.375 && x < 0.625) {
            if(y >= 0.55){
                return  0;
            }
            else if(y <= 0.45) {
                return 3;
            }
        }
        else if(y > 0.35 && y < 0.65){
            if(x < 0.4){
                return 1;
            }
            else if(x > 0.65){
                return 2;
            }
        }

        return Integer.MIN_VALUE;
    }
}
