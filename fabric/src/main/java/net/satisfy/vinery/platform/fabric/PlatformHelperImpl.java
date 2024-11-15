package net.satisfy.vinery.platform.fabric;

import net.satisfy.vinery.fabric.config.VineryFabricConfig;
import net.satisfy.vinery.platform.PlatformHelper;
import me.shedaniel.autoconfig.AutoConfig;

public class PlatformHelperImpl extends PlatformHelper {
    public static int getTotalFermentationTime() {
        VineryFabricConfig config = AutoConfig.getConfigHolder(VineryFabricConfig.class).getConfig();
        return config.blocks.totalFermentationTime;
    }

    public static int getApplePressMaxProgress() {
        VineryFabricConfig config = AutoConfig.getConfigHolder(VineryFabricConfig.class).getConfig();
        return config.blocks.applePressMaxProgress;
    }

    public static double getCherryGrowthChance() {
        VineryFabricConfig config = AutoConfig.getConfigHolder(VineryFabricConfig.class).getConfig();
        return config.blocks.cherryGrowthChance;
    }

    public static double getAppleGrowthChance() {
        VineryFabricConfig config = AutoConfig.getConfigHolder(VineryFabricConfig.class).getConfig();
        return config.blocks.appleGrowthChance;
    }

    public static double getGrapeGrowthChance() {
        VineryFabricConfig config = AutoConfig.getConfigHolder(VineryFabricConfig.class).getConfig();
        return config.blocks.grapeGrowthChance;
    }
}
