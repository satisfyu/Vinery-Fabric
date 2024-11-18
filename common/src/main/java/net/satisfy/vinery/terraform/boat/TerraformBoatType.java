package net.satisfy.vinery.terraform.boat;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.item.Item;

public interface TerraformBoatType {
    boolean isRaft();

    Item getItem();

    Item getChestItem();

    Item getPlanks();

    public static class Builder {
        private boolean raft;
        private RegistrySupplier<Item> item;
        private RegistrySupplier<Item> chestItem;

        public Builder() {
        }

        public TerraformBoatType build() {
            return new TerraformBoatTypeImpl(this.raft, this.item, this.chestItem);
        }

        public TerraformBoatType.Builder raft() {
            this.raft = true;
            return this;
        }

        public TerraformBoatType.Builder item(RegistrySupplier<Item> item) {
            this.item = item;
            return this;
        }

        public TerraformBoatType.Builder chestItem(RegistrySupplier<Item> chestItem) {
            this.chestItem = chestItem;
            return this;
        }
    }
}

