package net.satisfy.vinery.core.registry;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.satisfy.vinery.platform.PlatformHelper;

public class FlammableBlockRegistry {

    public static void init() {
        addFlammable(5, 20, ObjectRegistry.DARK_CHERRY_PLANKS.get(), ObjectRegistry.DARK_CHERRY_SLAB.get(), ObjectRegistry.DARK_CHERRY_STAIRS.get(), ObjectRegistry.DARK_CHERRY_FENCE.get(),
                ObjectRegistry.DARK_CHERRY_FENCE_GATE.get());

        addFlammable(5, 5, ObjectRegistry.STRIPPED_DARK_CHERRY_LOG.get(), ObjectRegistry.DARK_CHERRY_LOG.get(), ObjectRegistry.APPLE_LOG.get(),
                ObjectRegistry.STRIPPED_DARK_CHERRY_WOOD.get(), ObjectRegistry.DARK_CHERRY_WOOD.get(), ObjectRegistry.APPLE_WOOD.get());

        addFlammable(30, 60, ObjectRegistry.DARK_CHERRY_LEAVES.get(), ObjectRegistry.GRAPEVINE_LEAVES.get());
    }

    public static void addFlammable(int burnOdd, int igniteOdd, Block... blocks) {
        FireBlock fireBlock = (FireBlock) Blocks.FIRE;
        for (Block block : blocks) {
            fireBlock.setFlammable(block, burnOdd, igniteOdd);
        }
    }
}
