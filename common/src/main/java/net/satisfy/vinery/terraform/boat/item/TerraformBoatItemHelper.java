package net.satisfy.vinery.terraform.boat.item;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.DispenserBlock;

public final class TerraformBoatItemHelper {
    private static final Map<RegistrySupplier<? extends ItemLike>, TerraformBoatDispenserBehavior> DISPENSER_BEHAVIOURS = new HashMap();

    private TerraformBoatItemHelper() {
    }

    public static RegistrySupplier<Item> registerBoatItem(DeferredRegister<Item> register, String name, ResourceLocation boatTypeName, boolean chest) {
        return registerBoatItem(register, name, boatTypeName, chest, (new Item.Properties()).stacksTo(1));
    }

    public static RegistrySupplier<Item> registerBoatItem(DeferredRegister<Item> register, String name, ResourceLocation boatTypeName, boolean chest, Item.Properties settings) {
        RegistrySupplier<Item> item = register.register(name, () -> {
            return new TerraformBoatItem(boatTypeName, chest, settings);
        });
        registerBoatDispenserBehavior(item, boatTypeName, chest);
        return item;
    }

    public static <T extends ItemLike> void registerBoatDispenserBehavior(RegistrySupplier<T> item, ResourceLocation boatTypeName, boolean chest) {
        DISPENSER_BEHAVIOURS.put(item, new TerraformBoatDispenserBehavior(boatTypeName, chest));
    }

    public static void registerDispenserBehaviours() {
        DISPENSER_BEHAVIOURS.forEach((item, dispenserBehavior) -> {
            DispenserBlock.registerBehavior((ItemLike)item.get(), dispenserBehavior);
        });
    }
}

