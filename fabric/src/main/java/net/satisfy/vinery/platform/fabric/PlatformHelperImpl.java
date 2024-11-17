package net.satisfy.vinery.platform.fabric;

import net.minecraft.world.effect.MobEffect;
import net.satisfy.vinery.fabric.config.VineryFabricConfig;
import net.satisfy.vinery.platform.PlatformHelper;
import me.shedaniel.autoconfig.AutoConfig;
import net.satisfy.vinery.registry.MobEffectRegistry;

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

    public static int getWineMaxLevel() {
        VineryFabricConfig config = AutoConfig.getConfigHolder(VineryFabricConfig.class).getConfig();
        return config.items.wine.maxLevel;
    }

    public static int getWineStartDuration() {

        VineryFabricConfig config = AutoConfig.getConfigHolder(VineryFabricConfig.class).getConfig();
        return config.items.wine.startDuration;
    }

    public static int getWineDurationPerYear() {
        VineryFabricConfig config = AutoConfig.getConfigHolder(VineryFabricConfig.class).getConfig();
        return config.items.wine.durationPerYear;
    }

    public static int getWineDaysPerYear() {
        VineryFabricConfig config = AutoConfig.getConfigHolder(VineryFabricConfig.class).getConfig();
        return config.items.wine.daysPerYear;
    }

    public static int getWineYearsPerEffectLevel() {
        VineryFabricConfig config = AutoConfig.getConfigHolder(VineryFabricConfig.class).getConfig();
        return config.items.wine.yearsPerEffectLevel;
    }

    public static int getWineMaxDuration() {
        VineryFabricConfig config = AutoConfig.getConfigHolder(VineryFabricConfig.class).getConfig();
        return config.items.wine.maxDuration;
    }

    public static boolean shouldGiveEffect() {
        VineryFabricConfig config = AutoConfig.getConfigHolder(VineryFabricConfig.class).getConfig();
        return config.items.banner.giveEffect;
    }

    public static boolean shouldShowTooltip() {
        VineryFabricConfig config = AutoConfig.getConfigHolder(VineryFabricConfig.class).getConfig();
        return config.items.banner.isShowTooltipEnabled();
    }

    public static MobEffect getArmorEffect() {
        return MobEffectRegistry.ARMOR_EFFECT.get();
    }

    public static MobEffect getHealthEffect() {
        return MobEffectRegistry.HEALTH_EFFECT.get();
    }

    public static MobEffect getLuckEffect() {
        return MobEffectRegistry.LUCK_EFFECT.get();
    }

    public static MobEffect getResistanceEffect() {
        return MobEffectRegistry.RESISTANCE_EFFECT.get();
    }

    public static MobEffect getExperienceEffect() {
        return MobEffectRegistry.EXPERIENCE_EFFECT.get();
    }

    public static MobEffect getImprovedJumpBoostEffect() {
        return MobEffectRegistry.IMPROVED_JUMP_BOOST.get();
    }

    public static MobEffect getPartyEffect() {
        return MobEffectRegistry.PARTY_EFFECT.get();
    }

    public static MobEffect getTeleportEffect() {
        return MobEffectRegistry.TELEPORT.get();
    }

    public static MobEffect getCreeperEffect() {
        return MobEffectRegistry.CREEPER_EFFECT.get();
    }

    public static MobEffect getClimbingEffect() {
        return MobEffectRegistry.CLIMBING_EFFECT.get();
    }

    public static MobEffect getFrostyArmorEffect() {
        return MobEffectRegistry.FROSTY_ARMOR_EFFECT.get();
    }

    public static MobEffect getJellieEffect() {
        return MobEffectRegistry.JELLIE.get();
    }

    public static MobEffect getLavaWalkerEffect() {
        return MobEffectRegistry.LAVA_WALKER.get();
    }

    public static MobEffect getMagnetEffect() {
        return MobEffectRegistry.MAGNET.get();
    }

    public static MobEffect getStaggerEffect() {
        return MobEffectRegistry.STAGGER_EFFECT.get();
    }

    public static MobEffect getWaterWalkerEffect() {
        return MobEffectRegistry.WATER_WALKER.get();
    }
}
