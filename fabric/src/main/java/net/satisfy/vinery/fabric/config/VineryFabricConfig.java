package net.satisfy.vinery.fabric.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "vinery")
@Config.Gui.Background("vinery:textures/block/dark_cherry_planks.png")
public class VineryFabricConfig implements ConfigData {

    @ConfigEntry.Gui.CollapsibleObject
    public BlocksSettings blocks = new BlocksSettings();

    public static class BlocksSettings {
        @ConfigEntry.BoundedDiscrete(min = 1, max = 10000)
        public int totalFermentationTime = 6000;

        @ConfigEntry.BoundedDiscrete(min = 1, max = 10000)
        public int applePressMaxProgress = 600;

        @ConfigEntry.BoundedDiscrete(min = 0, max = 1)
        public double cherryGrowthChance = 0.4;

        @ConfigEntry.BoundedDiscrete(min = 0, max = 1)
        public double appleGrowthChance = 0.4;

        @ConfigEntry.BoundedDiscrete(min = 0, max = 1)
        public double grapeGrowthChance = 0.5;
    }
}
