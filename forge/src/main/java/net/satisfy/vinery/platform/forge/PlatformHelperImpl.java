package net.satisfy.vinery.platform.forge;

import net.minecraft.world.effect.MobEffect;
import net.satisfy.vinery.forge.config.VineryForgeConfig;
import net.satisfy.vinery.forge.registry.VineryForgeMobEffects;

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

    public static MobEffect getArmorEffect() {
        return VineryForgeMobEffects.ARMOR_EFFECT.get();
    }

    public static MobEffect getHealthEffect() {
        return VineryForgeMobEffects.HEALTH_EFFECT.get();
    }

    public static MobEffect getLuckEffect() {
        return VineryForgeMobEffects.LUCK_EFFECT.get();
    }

    public static MobEffect getResistanceEffect() {
        return VineryForgeMobEffects.RESISTANCE_EFFECT.get();
    }

    public static MobEffect getExperienceEffect() {
        return VineryForgeMobEffects.EXPERIENCE_EFFECT.get();
    }

    public static MobEffect getImprovedJumpBoostEffect() {
        return VineryForgeMobEffects.IMPROVED_JUMP_BOOST.get();
    }

    public static MobEffect getPartyEffect() {
        return VineryForgeMobEffects.PARTY_EFFECT.get();
    }

    public static MobEffect getTeleportEffect() {
        return VineryForgeMobEffects.TELEPORT.get();
    }

    public static MobEffect getCreeperEffect() {
        return VineryForgeMobEffects.CREEPER_EFFECT.get();
    }

    public static MobEffect getClimbingEffect() {
        return VineryForgeMobEffects.CLIMBING_EFFECT.get();
    }

    public static MobEffect getFrostyArmorEffect() {
        return VineryForgeMobEffects.FROSTY_ARMOR_EFFECT.get();
    }

    public static MobEffect getJellieEffect() {
        return VineryForgeMobEffects.JELLIE.get();
    }

    public static MobEffect getLavaWalkerEffect() {
        return VineryForgeMobEffects.LAVA_WALKER.get();
    }

    public static MobEffect getMagnetEffect() {
        return VineryForgeMobEffects.MAGNET.get();
    }

    public static MobEffect getStaggerEffect() {
        return VineryForgeMobEffects.STAGGER_EFFECT.get();
    }

    public static MobEffect getWaterWalkerEffect() {
        return VineryForgeMobEffects.WATER_WALKER.get();
    }
}
