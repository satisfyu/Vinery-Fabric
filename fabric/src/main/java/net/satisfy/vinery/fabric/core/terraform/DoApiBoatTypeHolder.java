package net.satisfy.vinery.fabric.core.terraform;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.item.Item;
import net.satisfy.vinery.core.terraform.boat.TerraformBoatTypeImpl;

public class DoApiBoatTypeHolder extends TerraformBoatTypeImpl {
    public DoApiBoatTypeHolder(boolean raft, RegistrySupplier<Item> item, RegistrySupplier<Item> chestItem) {
        super(raft, item, chestItem);
    }
}
