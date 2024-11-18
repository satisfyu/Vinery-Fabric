package net.satisfy.vinery.forge.core.terraform.sign;

import net.minecraft.world.level.block.state.BlockBehaviour;

public interface BlockSettingsLock {
    void lock();

    static BlockBehaviour.Properties lock(BlockBehaviour.Properties settings) {
        ((BlockSettingsLock)settings).lock();
        return settings;
    }
}
