package net.satisfy.vinery.forge.config;

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
    public static final ForgeConfigSpec.IntValue APPLE_PRESS_CRAFTING_TIME;
    public static final ForgeConfigSpec.DoubleValue CHERRY_GROWTH_CHANCE;
    public static final ForgeConfigSpec.DoubleValue APPLE_GROWTH_CHANCE;
    public static final ForgeConfigSpec.DoubleValue GRAPE_GROWTH_CHANCE;

    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> LEVEL1_TRADES;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> LEVEL2_TRADES;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> LEVEL3_TRADES;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> LEVEL4_TRADES;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> LEVEL5_TRADES;

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

        COMMON_BUILDER.push("VillagerTrades");

        LEVEL1_TRADES = COMMON_BUILDER
                .comment("List of trades for Level 1. Format: item|price|quantity|maxUses|isSelling")
                .defineList("level1Trades", List.of("vinery:red_grape|5|4|5|false", "vinery:white_grape|5|4|5|false"), obj -> obj instanceof String);

        LEVEL2_TRADES = COMMON_BUILDER
                .comment("List of trades for Level 2. Format: item|price|quantity|maxUses|isSelling")
                .defineList("level2Trades", List.of("vinery:wine_bottle|1|2|7|true"), obj -> obj instanceof String);

        LEVEL3_TRADES = COMMON_BUILDER
                .comment("List of trades for Level 3. Format: item|price|quantity|maxUses|isSelling")
                .defineList("level3Trades", List.of("vinery:flower_box|3|1|10|true", "vinery:white_grape_bag|7|1|10|true"), obj -> obj instanceof String);

        LEVEL4_TRADES = COMMON_BUILDER
                .comment("List of trades for Level 4. Format: item|price|quantity|maxUses|isSelling")
                .defineList("level4Trades", List.of("vinery:basket|4|1|10|true", "vinery:flower_pot_big|5|1|10|true"), obj -> obj instanceof String);

        LEVEL5_TRADES = COMMON_BUILDER
                .comment("List of trades for Level 5. Format: item|price|quantity|maxUses|isSelling")
                .defineList("level5Trades", List.of("vinery:wine_box|10|1|10|true", "vinery:lilitu_wine|4|1|10|true"), obj -> obj instanceof String);

        COMMON_BUILDER.pop();

        COMMON_CONFIG = COMMON_BUILDER.build();
    }

    @SubscribeEvent
    public static void onLoad(final ModConfigEvent.Loading configEvent) {}

    @SubscribeEvent
    public static void onReload(final ModConfigEvent.Reloading configEvent) {}

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
