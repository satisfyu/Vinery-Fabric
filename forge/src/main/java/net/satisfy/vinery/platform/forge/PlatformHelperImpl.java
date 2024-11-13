package net.satisfy.vinery.platform.forge;

import net.satisfy.vinery.forge.config.VineryForgeConfig;

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
}
