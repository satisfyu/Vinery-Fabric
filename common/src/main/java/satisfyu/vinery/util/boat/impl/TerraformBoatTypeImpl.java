package satisfyu.vinery.util.boat.impl;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import satisfyu.vinery.util.boat.api.TerraformBoatType;

public class TerraformBoatTypeImpl implements TerraformBoatType {
	private final RegistrySupplier<Item> item;
	private final RegistrySupplier<Item> chestItem;

	public TerraformBoatTypeImpl(RegistrySupplier<Item> item, RegistrySupplier<Item> chestItem) {
		this.item = item;
		this.chestItem = chestItem;
	}

	@Override
	public Item getItem() {
		return this.item.get();
	}

	@Override
	public Item getChestItem() {
		return this.chestItem.get();
	}

	@Override
	public Item getPlanks() {
		return Items.OAK_PLANKS;
	}
}