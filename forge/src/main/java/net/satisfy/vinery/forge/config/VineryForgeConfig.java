package net.satisfy.vinery.forge.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.config.ModConfigEvent;

import java.io.File;

public class VineryForgeConfig {
    public static ForgeConfigSpec COMMON_CONFIG;
    public static final ForgeConfigSpec.IntValue TOTAL_FERMENTATION_TIME;
    public static final ForgeConfigSpec.IntValue APPLE_PRESS_CRAFTING_TIME;
    public static final ForgeConfigSpec.DoubleValue CHERRY_GROWTH_CHANCE;
    public static final ForgeConfigSpec.DoubleValue APPLE_GROWTH_CHANCE;
    public static final ForgeConfigSpec.DoubleValue GRAPE_GROWTH_CHANCE;

    static {
        ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
        COMMON_BUILDER.push("Blocks");
        TOTAL_FERMENTATION_TIME = COMMON_BUILDER
                .comment("Total fermentation time in ticks")
                .defineInRange("totalFermentationTime", 6000, 1, Integer.MAX_VALUE);
        APPLE_PRESS_CRAFTING_TIME = COMMON_BUILDER
                .comment("Apple Press crafting time in ticks")
                .defineInRange("applePressMaxProgress", 600, 1, 1000);
        CHERRY_GROWTH_CHANCE = COMMON_BUILDER
                .comment("Chance for cherries to grow")
                .defineInRange("cherryGrowthChance", 0.4, 0.0, 1.0);
        APPLE_GROWTH_CHANCE = COMMON_BUILDER
                .comment("Chance for apples to grow")
                .defineInRange("appleGrowthChance", 0.4, 0.0, 1.0);
        GRAPE_GROWTH_CHANCE = COMMON_BUILDER
                .comment("Chance for grapes to grow")
                .defineInRange("grapeGrowthChance", 0.4, 0.0, 1.0);

        COMMON_BUILDER.pop();
        COMMON_CONFIG = COMMON_BUILDER.build();
    }

    @SubscribeEvent
    public static void onLoad(final ModConfigEvent.Loading configEvent) {
    }

    @SubscribeEvent
    public static void onReload(final ModConfigEvent.Reloading configEvent) {
    }

    public static void loadConfig(ForgeConfigSpec spec, String path) {
        final CommentedFileConfig file = CommentedFileConfig.builder(new File(path))
                .sync()
                .preserveInsertionOrder()
                .autosave()
                .writingMode(WritingMode.REPLACE)
                .build();
        file.load();
        spec.setConfig(file);
    }
}
