package net.satisfy.vinery.terraform.boat;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class TerraformBoatTypeImpl implements TerraformBoatType {
    private final boolean raft;
    private final RegistrySupplier<Item> item;
    private final RegistrySupplier<Item> chestItem;

    public TerraformBoatTypeImpl(boolean raft, RegistrySupplier<Item> item, RegistrySupplier<Item> chestItem) {
        this.raft = raft;
        this.item = item;
        this.chestItem = chestItem;
    }

    public boolean isRaft() {
        return this.raft;
    }

    public Item getItem() {
        return (Item)this.item.orElse(Items.OAK_BOAT);
    }

    public Item getChestItem() {
        return (Item)this.chestItem.orElse(Items.OAK_CHEST_BOAT);
    }

    public Item getPlanks() {
        return Items.OAK_PLANKS;
    }
}