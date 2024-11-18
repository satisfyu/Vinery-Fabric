package net.satisfy.vinery.platform.forge;

import com.mojang.datafixers.util.Pair;
import de.cristelknight.doapi.forge.terraform.boat.impl.entity.TerraformBoatEntity;
import de.cristelknight.doapi.forge.terraform.boat.impl.entity.TerraformChestBoatEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.forgespi.locating.IModFile;
import net.minecraftforge.resource.PathPackResources;
import net.satisfy.vinery.forge.config.VineryForgeConfig;
import net.satisfy.vinery.forge.packs.BuiltInPackRegistry;
import net.satisfy.vinery.forge.registry.BurningBlockRegistry;
import net.satisfy.vinery.forge.terraform.sign.SpriteIdentifierRegistry;
import net.satisfy.vinery.forge.terraform.sign.block.TerraformHangingSignBlock;
import net.satisfy.vinery.forge.terraform.sign.block.TerraformSignBlock;
import net.satisfy.vinery.forge.terraform.sign.block.TerraformWallHangingSignBlock;
import net.satisfy.vinery.forge.terraform.sign.block.TerraformWallSignBlock;
import net.satisfy.vinery.terraform.boat.TerraformBoatType;
import net.satisfy.vinery.terraform.boat.TerraformBoatTypeRegistry;

import javax.annotation.Nullable;
import java.nio.file.Path;

public class PlatformHelperImpl {
    public static int getTotalFermentationTime() {
        return VineryForgeConfig.TOTAL_FERMENTATION_TIME.get();
    }

    public static int getApplePressMaxProgress() {
        return VineryForgeConfig.APPLE_PRESS_CRAFTING_TIME.get();
    }

    public static double getCherryGrowthChance() {
        return VineryForgeConfig.CHERRY_GROWTH_CHANCE.get();
    }

    public static double getAppleGrowthChance() {
        return VineryForgeConfig.APPLE_GROWTH_CHANCE.get();
    }

    public static double getGrapeGrowthChance() {
        return VineryForgeConfig.GRAPE_GROWTH_CHANCE.get();
    }

    public static int getWineMaxLevel() {
        return VineryForgeConfig.MAX_LEVEL.get();
    }

    public static int getWineStartDuration() {
        return VineryForgeConfig.START_DURATION.get();
    }

    public static int getWineDurationPerYear() {
        return VineryForgeConfig.DURATION_PER_YEAR.get();
    }

    public static int getWineDaysPerYear() {
        return VineryForgeConfig.DAYS_PER_YEAR.get();
    }

    public static int getWineYearsPerEffectLevel() {
        return VineryForgeConfig.YEARS_PER_EFFECT_LEVEL.get();
    }

    public static int getWineMaxDuration() {
        return VineryForgeConfig.MAX_DURATION.get();
    }

    public static boolean shouldGiveEffect() {
        return VineryForgeConfig.GIVE_EFFECT.get();
    }

    public static boolean shouldShowTooltip() {
        return VineryForgeConfig.GIVE_EFFECT.get() && VineryForgeConfig.SHOW_TOOLTIP.get();
    }

    public static void addFlammable(int burnOdd, int igniteOdd, Block... blocks) {
        BurningBlockRegistry.add(burnOdd, igniteOdd, blocks);
    }

    public static Block getSign(ResourceLocation signTextureId) {
        return new TerraformSignBlock(signTextureId, BlockBehaviour.Properties.copy(Blocks.OAK_SIGN));
    }

    public static Block getWallSign(ResourceLocation signTextureId) {
        return new TerraformWallSignBlock(signTextureId, BlockBehaviour.Properties.copy(Blocks.OAK_SIGN));
    }

    public static Block getHangingSign(ResourceLocation hangingSignTextureId, ResourceLocation hangingSignGuiTextureId) {
        return new TerraformHangingSignBlock(hangingSignTextureId, hangingSignGuiTextureId, BlockBehaviour.Properties.copy(Blocks.OAK_HANGING_SIGN));
    }

    public static Block getWallHangingSign(ResourceLocation hangingSignTextureId, ResourceLocation hangingSignGuiTextureId) {
        return new TerraformWallHangingSignBlock(hangingSignTextureId, hangingSignGuiTextureId, BlockBehaviour.Properties.copy(Blocks.OAK_HANGING_SIGN));
    }

    public static void addSignSprite(ResourceLocation signTextureId) {
        SpriteIdentifierRegistry.INSTANCE.addIdentifier(signTextureId);
    }

    public static void registerBoatType(ResourceLocation resourceLocation, TerraformBoatType type) {
        TerraformBoatTypeRegistry.register(resourceLocation, type);
    }

    @Nullable
    public static Path getResourceDirectory(String modId, String subPath) {
        ModContainer container = (ModContainer) ModList.get().getModContainerById(modId).orElse(null);
        if (container == null) {
            System.out.println("Mod container for modId:" + modId + " is null");
            return null;
        } else {
            IModFile file = container.getModInfo().getOwningFile().getFile();
            Path path = file.findResource(new String[]{subPath});
            if (path == null) {
                System.out.println("Path for subPath: " + subPath + " in modId: " + modId + " is null");
            }

            return path;
        }
    }

    public static void registerBuiltInPack(String modId, ResourceLocation location, boolean alwaysEnabled) {
        String stringPath = location.getPath();
        Path path = getResourceDirectory(modId, "resourcepacks/" + stringPath);
        if (path != null) {
            String[] pathElements = stringPath.split("/");
            BuiltInPackRegistry.packResources.put(location, new Pair(new PathPackResources(pathElements[pathElements.length - 1], true, path), alwaysEnabled));
        }
    }

    public static Boat createBoat(ResourceLocation boatTypeName, Level world, double x, double y, double z, boolean chest) {
        de.cristelknight.doapi.terraform.boat.TerraformBoatType boatType = de.cristelknight.doapi.forge.terraform.boat.api.TerraformBoatTypeRegistry.get(boatTypeName);
        Object boatEntity;
        if (chest) {
            TerraformChestBoatEntity chestBoat = new TerraformChestBoatEntity(world, x, y, z);
            chestBoat.setTerraformBoat(boatType);
            boatEntity = chestBoat;
        } else {
            TerraformBoatEntity boat = new TerraformBoatEntity(world, x, y, z);
            boat.setTerraformBoat(boatType);
            boatEntity = boat;
        }

        return (Boat)boatEntity;
    }
}
