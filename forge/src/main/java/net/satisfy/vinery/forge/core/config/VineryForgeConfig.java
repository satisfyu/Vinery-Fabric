package net.satisfy.vinery.forge.core.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.config.ModConfigEvent;

import java.io.File;
import java.util.List;

public class VineryForgeConfig {
    public static final ForgeConfigSpec COMMON_CONFIG;


    public static final ForgeConfigSpec.IntValue TOTAL_FERMENTATION_TIME;
    public static final ForgeConfigSpec.IntValue MAX_FLUID_LEVEL;
    public static final ForgeConfigSpec.IntValue MAX_FLUID_INCREASE;
    public static final ForgeConfigSpec.IntValue APPLE_PRESS_MASHING_TIME;
    public static final ForgeConfigSpec.IntValue APPLE_PRESS_FERMENTING_TIME;
    public static final ForgeConfigSpec.IntValue GRAPEVINE_POT_MAX_STORAGE;
    public static final ForgeConfigSpec.DoubleValue CHERRY_GROWTH_CHANCE;
    public static final ForgeConfigSpec.DoubleValue APPLE_GROWTH_CHANCE;
    public static final ForgeConfigSpec.DoubleValue GRAPE_GROWTH_CHANCE;
    public static final ForgeConfigSpec.IntValue MAX_LEVEL;
    public static final ForgeConfigSpec.IntValue START_DURATION;
    public static final ForgeConfigSpec.IntValue DURATION_PER_YEAR;
    public static final ForgeConfigSpec.IntValue DAYS_PER_YEAR;
    public static final ForgeConfigSpec.IntValue YEARS_PER_EFFECT_LEVEL;
    public static final ForgeConfigSpec.IntValue MAX_DURATION;
    public static final ForgeConfigSpec.BooleanValue GIVE_EFFECT;
    public static final ForgeConfigSpec.BooleanValue SHOW_TOOLTIP;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> LEVEL1_TRADES;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> LEVEL2_TRADES;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> LEVEL3_TRADES;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> LEVEL4_TRADES;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> LEVEL5_TRADES;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> BASKET_BLACKLIST;
    public static final ForgeConfigSpec.DoubleValue TRADER_SPAWN_CHANCE;
    public static final ForgeConfigSpec.BooleanValue SPAWN_WITH_MULES;
    public static final ForgeConfigSpec.IntValue TRADER_SPAWN_DELAY;

    static {
        ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
        COMMON_BUILDER.push("Blocks");

        TOTAL_FERMENTATION_TIME = COMMON_BUILDER
                .comment("Total fermentation time in ticks")
                .defineInRange("totalFermentationTime", 6000, 1, Integer.MAX_VALUE);

        MAX_FLUID_LEVEL = COMMON_BUILDER
                .comment("Maximum fluid level in the fermentation barrel")
                .defineInRange("maxFluidLevel", 100, 10, 1000);

        MAX_FLUID_INCREASE = COMMON_BUILDER
                .comment("How much Fluid a Grapejuice Bottle fills")
                .defineInRange("maxFluidLevel", 25, 1, 1000);

        GRAPEVINE_POT_MAX_STORAGE = COMMON_BUILDER
                .comment("Maximum Grape storage capacity for Grapevine pots")
                .defineInRange("maxStorageCapacity", 6, 6, 100);

        APPLE_PRESS_MASHING_TIME = COMMON_BUILDER
                .comment("Apple Press mashing time in ticks")
                .defineInRange("applePressMaxMashingProgress", 600, 1, 1000);

        APPLE_PRESS_FERMENTING_TIME = COMMON_BUILDER
                .comment("Apple Press fermenting time in ticks")
                .defineInRange("applePressMaxFermentingProgress", 800, 1, 1000);

        CHERRY_GROWTH_CHANCE = COMMON_BUILDER
                .comment("Chance for cherries to grow")
                .defineInRange("cherryGrowthChance", 0.4, 0.0, 1.0);

        APPLE_GROWTH_CHANCE = COMMON_BUILDER
                .comment("Chance for apples to grow")
                .defineInRange("appleGrowthChance", 0.4, 0.0, 1.0);

        GRAPE_GROWTH_CHANCE = COMMON_BUILDER
                .comment("Chance for grapes to grow")
                .defineInRange("grapeGrowthChance", 0.5, 0.0, 1.0);

        COMMON_BUILDER.pop();

        COMMON_BUILDER.push("Items");
        COMMON_BUILDER.push("Wine");

        MAX_LEVEL = COMMON_BUILDER
                .comment("Maximum level for wine")
                .defineInRange("maxLevel", 5, 1, 10);

        START_DURATION = COMMON_BUILDER
                .comment("Start duration for wine in seconds")
                .defineInRange("startDuration", 1800, 1, 100000);

        DURATION_PER_YEAR = COMMON_BUILDER
                .comment("Duration per year in seconds")
                .defineInRange("durationPerYear", 200, 1, 10000);

        DAYS_PER_YEAR = COMMON_BUILDER
                .comment("Days per year")
                .defineInRange("daysPerYear", 24, 1, 100);

        YEARS_PER_EFFECT_LEVEL = COMMON_BUILDER
                .comment("Years per effect level")
                .defineInRange("yearsPerEffectLevel", 6, 1, 100);

        MAX_DURATION = COMMON_BUILDER
                .comment("Maximum duration in seconds")
                .defineInRange("maxDuration", 15000, 1, 100000);

        COMMON_BUILDER.pop();
        COMMON_BUILDER.push("Banner");

        GIVE_EFFECT = COMMON_BUILDER
                .comment("Set to false to disable the banner's effect.")
                .define("giveEffect", true);

        SHOW_TOOLTIP = COMMON_BUILDER
                .comment("Set to false to hide the banner's tooltip. If giveEffect is false, showTooltip is automatically false.")
                .define("showTooltip", true);

        COMMON_BUILDER.pop();
        COMMON_BUILDER.push("Basket");

        BASKET_BLACKLIST = COMMON_BUILDER
                .comment("List of item IDs that are blacklisted from being placed in the picnic basket. Format: 'modid:itemname'")
                .defineList("basketBlacklist", List.of(
                        "vinery:basket"
                ), obj -> obj instanceof String);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.push("WanderingTrader");
        TRADER_SPAWN_CHANCE = COMMON_BUILDER
                .comment("Chance for the custom trader to spawn. Range: 0.0 to 1.0")
                .defineInRange("spawnChance", 0.5, 0.0, 1.0);

        SPAWN_WITH_MULES = COMMON_BUILDER
                .comment("If true, the trader will spawn with mules.")
                .define("spawnWithMules", true);

        TRADER_SPAWN_DELAY = COMMON_BUILDER
                .comment("Spawn delay for the trader in ticks.")
                .defineInRange("spawnDelay", 48000, 1, Integer.MAX_VALUE);
        COMMON_BUILDER.pop();
        COMMON_BUILDER.push("VillagerTrades");

        LEVEL1_TRADES = COMMON_BUILDER
                .comment("List of trades for Level 1. Format: item|price|quantity|maxUses|isSelling")
                .defineList("level1Trades", List.of(
                        "vinery:red_grape|5|4|5|false",
                        "vinery:white_grape|5|4|5|false",
                        "vinery:red_grape_seeds|2|1|1|true",
                        "vinery:white_grape_seeds|2|1|1|true"
                ), obj -> obj instanceof String);

        LEVEL2_TRADES = COMMON_BUILDER
                .comment("List of trades for Level 2. Format: item|price|quantity|maxUses|isSelling")
                .defineList("level2Trades", List.of(
                        "vinery:wine_bottle|1|2|7|true"
                ), obj -> obj instanceof String);

        LEVEL3_TRADES = COMMON_BUILDER
                .comment("List of trades for Level 3. Format: item|price|quantity|maxUses|isSelling")
                .defineList("level3Trades", List.of(
                        "vinery:flower_box|3|1|10|true",
                        "vinery:white_grape_bag|7|1|10|true",
                        "vinery:red_grape_bag|7|1|10|true"
                ), obj -> obj instanceof String);

        LEVEL4_TRADES = COMMON_BUILDER
                .comment("List of trades for Level 4. Format: item|price|quantity|maxUses|isSelling")
                .defineList("level4Trades", List.of(
                        "vinery:basket|4|1|10|true",
                        "vinery:flower_pot_big|5|1|10|true",
                        "vinery:window|12|1|10|true",
                        "vinery:dark_cherry_beam|6|1|10|true",
                        "vinery:taiga_red_grape_seeds|2|1|5|true",
                        "vinery:taiga_white_grape_seeds|2|1|5|true"
                ), obj -> obj instanceof String);

        LEVEL5_TRADES = COMMON_BUILDER
                .comment("List of trades for Level 5. Format: item|price|quantity|maxUses|isSelling")
                .defineList("level5Trades", List.of(
                        "vinery:wine_box|10|1|10|true",
                        "vinery:lilitu_wine|4|1|10|true",
                        "vinery:calendar|12|1|15|true"
                ), obj -> obj instanceof String);

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
