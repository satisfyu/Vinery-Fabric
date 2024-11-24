package net.satisfy.vinery.fabric.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

import java.util.ArrayList;
import java.util.List;

@Config(name = "vinery")
@Config.Gui.Background("vinery:textures/block/dark_cherry_planks.png")
public class VineryFabricConfig implements ConfigData {

    @ConfigEntry.Gui.CollapsibleObject
    public BlocksSettings blocks = new BlocksSettings();

    @ConfigEntry.Gui.CollapsibleObject
    public ItemsSettings items = new ItemsSettings();


    @ConfigEntry.Gui.CollapsibleObject
    public VillagerSettings villager = new VillagerSettings();

    public static class BlocksSettings {
        @ConfigEntry.BoundedDiscrete(min = 1, max = 10000)
        public int totalFermentationTime = 6000;

        @ConfigEntry.BoundedDiscrete(min = 100, max = 10000)
        public int maxFluidLevel = 100;

        @ConfigEntry.BoundedDiscrete(min = 1, max = 10000)
        public int applePressMaxProgress = 600;

        @ConfigEntry.BoundedDiscrete(min = 6, max = 100)
        public int grapevinePotMaxStorage = 6;

        @ConfigEntry.BoundedDiscrete(min = 0, max = 1)
        public double cherryGrowthChance = 0.4;

        @ConfigEntry.BoundedDiscrete(min = 0, max = 1)
        public double appleGrowthChance = 0.4;

        @ConfigEntry.BoundedDiscrete(min = 0, max = 1)
        public double grapeGrowthChance = 0.5;
    }

    public static class ItemsSettings {
        @ConfigEntry.Gui.CollapsibleObject
        public WineSettings wine = new WineSettings();

        @ConfigEntry.Gui.CollapsibleObject
        public BannerSettings banner = new BannerSettings();

        @ConfigEntry.Gui.CollapsibleObject
        public BasketSettings basket = new BasketSettings();

        public static class WineSettings {
            @ConfigEntry.BoundedDiscrete(min = 1, max = 100000)
            public int startDuration = 1800;

            @ConfigEntry.BoundedDiscrete(min = 1, max = 100000)
            public int maxDuration = 15000;

            @ConfigEntry.BoundedDiscrete(min = 1, max = 10)
            public int maxLevel = 5;

            @ConfigEntry.BoundedDiscrete(min = 1, max = 10000)
            public int durationPerYear = 200;

            @ConfigEntry.BoundedDiscrete(min = 1, max = 100)
            public int daysPerYear = 24;

            @ConfigEntry.BoundedDiscrete(min = 1, max = 100)
            public int yearsPerEffectLevel = 6;
        }
    }

    public static class BannerSettings {

        public boolean giveEffect = true;

        public boolean showTooltip = true;

        public boolean isShowTooltipEnabled() {
            return giveEffect && showTooltip;
        }
    }

    @ConfigEntry.Gui.CollapsibleObject
    public TraderSettings trader = new TraderSettings();
    public static class TraderSettings {
        @ConfigEntry.BoundedDiscrete(min = 0, max = 1)
        public double spawnChance = 0.5;
        public boolean spawnWithMules = true;
        @ConfigEntry.BoundedDiscrete(min = 0, max = 72000)
        public int spawnDelay = 48000;
    }

    public static class VillagerSettings {

        @ConfigEntry.Gui.CollapsibleObject
        public TradeLevelSettings level1 = new TradeLevelSettings(1);

        @ConfigEntry.Gui.CollapsibleObject
        public TradeLevelSettings level2 = new TradeLevelSettings(2);

        @ConfigEntry.Gui.CollapsibleObject
        public TradeLevelSettings level3 = new TradeLevelSettings(3);

        @ConfigEntry.Gui.CollapsibleObject
        public TradeLevelSettings level4 = new TradeLevelSettings(4);

        @ConfigEntry.Gui.CollapsibleObject
        public TradeLevelSettings level5 = new TradeLevelSettings(5);

        public static class TradeLevelSettings {
            public int level;
            public List<TradeEntry> trades;

            public TradeLevelSettings(int level) {
                this.level = level;
                this.trades = new ArrayList<>();
                if (level == 1) {
                    trades.add(new TradeEntry("vinery:red_grape", TradeType.BUY, 15, 1, 4, 5));
                    trades.add(new TradeEntry("vinery:white_grape", TradeType.BUY, 15, 1, 4, 5));
                    trades.add(new TradeEntry("vinery:red_grape_seeds", TradeType.SELL, 2, 1, 1, 5));
                    trades.add(new TradeEntry("vinery:white_grape_seeds", TradeType.SELL, 2, 1, 1, 5));
                } else if (level == 2) {
                    trades.add(new TradeEntry("vinery:wine_bottle", TradeType.SELL, 1, 1, 2, 7));
                } else if (level == 3) {
                    trades.add(new TradeEntry("vinery:flower_box", TradeType.SELL, 3, 1, 1, 10));
                    trades.add(new TradeEntry("vinery:white_grape_bag", TradeType.SELL, 7, 1, 1, 10));
                    trades.add(new TradeEntry("vinery:red_grape_bag", TradeType.SELL, 7, 1, 1, 10));
                } else if (level == 4) {
                    trades.add(new TradeEntry("vinery:basket", TradeType.SELL, 4, 1, 1, 10));
                    trades.add(new TradeEntry("vinery:flower_pot_big", TradeType.SELL, 5, 1, 1, 10));
                    trades.add(new TradeEntry("vinery:window", TradeType.SELL, 12, 1, 1, 10));
                    trades.add(new TradeEntry("vinery:dark_cherry_beam", TradeType.SELL, 6, 1, 1, 10));
                    trades.add(new TradeEntry("vinery:taiga_red_grape_seeds", TradeType.SELL, 2, 1, 1, 5));
                    trades.add(new TradeEntry("vinery:taiga_white_grape_seeds", TradeType.SELL, 2, 1, 1, 5));
                } else if (level == 5) {
                    trades.add(new TradeEntry("vinery:wine_box", TradeType.SELL, 10, 1, 1, 10));
                    trades.add(new TradeEntry("vinery:lilitu_wine", TradeType.SELL, 4, 1, 1, 10));
                    trades.add(new TradeEntry("vinery:calendar", TradeType.SELL, 12, 1, 1, 15));
                }
            }
        }

        public static class TradeEntry {
            public String item = "vinery:white_grape";
            public TradeType type = TradeType.BUY;
            public int price = 1;
            public int count = 1;
            public int maxUses = 1;
            public int experience = 1;

            public TradeEntry() {}

            public TradeEntry(String item, TradeType type, int price, int count, int maxUses, int experience) {
                this.item = item;
                this.type = type;
                this.price = price;
                this.count = count;
                this.maxUses = maxUses;
                this.experience = experience;
            }
        }

        public enum TradeType {
            BUY,
            SELL
        }
    }

    public static class BasketSettings {
        @ConfigEntry.Gui.CollapsibleObject
        @ConfigEntry.Category("Items/Basket")
        public BasketBlacklistSettings blacklist = new BasketBlacklistSettings();
        public static class BasketBlacklistSettings {
            public List<String> basketBlacklist = new ArrayList<>();
            public BasketBlacklistSettings() {
                basketBlacklist.add("minecraft:shulker_box");
                basketBlacklist.add("vinery:basket");
            }
        }
    }
}
