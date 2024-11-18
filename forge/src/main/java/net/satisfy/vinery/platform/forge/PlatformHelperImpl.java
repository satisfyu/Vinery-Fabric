package net.satisfy.vinery.platform.forge;

import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.forgespi.locating.IModFile;
import net.minecraftforge.resource.PathPackResources;
import net.satisfy.vinery.forge.core.config.VineryForgeConfig;
import net.satisfy.vinery.forge.core.packs.BuiltInPackRegistry;
import net.satisfy.vinery.forge.core.registry.BurningBlockRegistry;

import javax.annotation.Nullable;
import java.nio.file.Path;
import java.util.List;

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

    @Nullable
    public static Path getResourceDirectory(String modId, String subPath) {
        ModContainer container = ModList.get().getModContainerById(modId).orElse(null);
        if (container == null) {
            System.out.println("Mod container for modId:" + modId + " is null");
            return null;
        } else {
            IModFile file = container.getModInfo().getOwningFile().getFile();
            Path path = file.findResource(subPath);
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

    public static List<? extends String> getBasketBlacklist() {
        return VineryForgeConfig.BASKET_BLACKLIST.get();
    }

    public static double getTraderSpawnChance() {
        return VineryForgeConfig.TRADER_SPAWN_CHANCE.get();
    }

    public static boolean shouldSpawnWithMules() {
        return VineryForgeConfig.SPAWN_WITH_MULES.get();
    }

    public static int getTraderSpawnDelay() {
        return VineryForgeConfig.TRADER_SPAWN_DELAY.get();
    }
}
