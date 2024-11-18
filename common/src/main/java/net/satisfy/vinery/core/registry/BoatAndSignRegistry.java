package net.satisfy.vinery.core.registry;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.HangingSignItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.level.block.Block;
import net.satisfy.vinery.platform.PlatformHelper;
import net.satisfy.vinery.core.terraform.boat.TerraformBoatType;
import net.satisfy.vinery.core.terraform.boat.item.TerraformBoatItemHelper;
import net.satisfy.vinery.core.util.VineryIdentifier;

public class BoatAndSignRegistry {
    public static final ResourceLocation DARK_CHERRY_BOAT_TYPE = new VineryIdentifier("dark_cherry");

    public static final ResourceLocation DARK_CHERRY_SIGN_TEXTURE = new VineryIdentifier("entity/signs/dark_cherry");
    public static final RegistrySupplier<Block> DARK_CHERRY_SIGN = ObjectRegistry.registerWithoutItem("dark_cherry_sign", () -> PlatformHelper.getSign(DARK_CHERRY_SIGN_TEXTURE));
    public static final RegistrySupplier<Block> DARK_CHERRY_WALL_SIGN = ObjectRegistry.registerWithoutItem("dark_cherry_wall_sign", () -> PlatformHelper.getWallSign(DARK_CHERRY_SIGN_TEXTURE));
    public static final RegistrySupplier<Item> DARK_CHERRY_SIGN_ITEM = ObjectRegistry.registerItem("dark_cherry_sign", () -> new SignItem(ObjectRegistry.getSettings().stacksTo(16), DARK_CHERRY_SIGN.get(), DARK_CHERRY_WALL_SIGN.get()));
    public static final ResourceLocation DARK_CHERRY_HANGING_SIGN_TEXTURE = new VineryIdentifier("entity/signs/hanging/dark_cherry");
    public static final ResourceLocation DARK_CHERRY_HANGING_SIGN_GUI_TEXTURE = new VineryIdentifier("textures/gui/hanging_signs/dark_cherry");

    public static final RegistrySupplier<Block> DARK_CHERRY_HANGING_SIGN = ObjectRegistry.registerWithoutItem("dark_cherry_hanging_sign", () -> PlatformHelper.getHangingSign(DARK_CHERRY_HANGING_SIGN_TEXTURE, DARK_CHERRY_HANGING_SIGN_GUI_TEXTURE));
    public static final RegistrySupplier<Block> DARK_CHERRY_WALL_HANGING_SIGN = ObjectRegistry.registerWithoutItem("dark_cherry_wall_hanging_sign", () -> PlatformHelper.getWallHangingSign(DARK_CHERRY_HANGING_SIGN_TEXTURE, DARK_CHERRY_HANGING_SIGN_GUI_TEXTURE));
    public static final RegistrySupplier<Item> DARK_CHERRY_HANGING_SIGN_ITEM = ObjectRegistry.registerItem("dark_cherry_hanging_sign", () -> new HangingSignItem(DARK_CHERRY_HANGING_SIGN.get(), DARK_CHERRY_WALL_HANGING_SIGN.get(), ObjectRegistry.getSettings().stacksTo(16)));

    public static final RegistrySupplier<Item> DARK_CHERRY_BOAT = TerraformBoatItemHelper.registerBoatItem(ObjectRegistry.ITEMS, "dark_cherry_boat", DARK_CHERRY_BOAT_TYPE, false);
    public static final RegistrySupplier<Item> DARK_CHERRY_CHEST_BOAT = TerraformBoatItemHelper.registerBoatItem(ObjectRegistry.ITEMS, "dark_cherry_chest_boat", DARK_CHERRY_BOAT_TYPE, true);

    public static void init() {
        PlatformHelper.registerBoatType(DARK_CHERRY_BOAT_TYPE, new TerraformBoatType.Builder().item(DARK_CHERRY_BOAT).chestItem(DARK_CHERRY_CHEST_BOAT).build());
    }
}
