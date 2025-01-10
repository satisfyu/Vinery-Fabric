package net.satisfy.vinery.platform.forge;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.satisfy.vinery.core.Vinery;
import net.satisfy.vinery.forge.core.config.VineryForgeConfig;
import net.satisfy.vinery.forge.core.registry.BurningBlockRegistry;

import java.util.List;
import java.util.function.Supplier;

public class PlatformHelperImpl {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Vinery.MOD_ID);

    public static void updateWineConfig(int totalFermentationTime, int maxFluidLevel, int maxFluidIncrease, int applePressMashingTime,
                                        int applePressFermentationTime, int grapevinePotMaxStorage, int grapevinePotRequiredJumps,
                                        boolean shouldShowSplashParticles, double cherryGrowthChance, double appleGrowthChance,
                                        double grapeGrowthChance, int maxWineLevel, int startDuration, int durationPerYear,
                                        int daysPerYear, int yearsPerEffectLevel, int maxDuration, boolean giveEffect, boolean showTooltip) {
        VineryForgeConfig.TOTAL_FERMENTATION_TIME.set(totalFermentationTime);
        VineryForgeConfig.MAX_FLUID_LEVEL.set(maxFluidLevel);
        VineryForgeConfig.MAX_FLUID_INCREASE.set(maxFluidIncrease);
        VineryForgeConfig.APPLE_PRESS_MASHING_TIME.set(applePressMashingTime);
        VineryForgeConfig.APPLE_PRESS_FERMENTING_TIME.set(applePressFermentationTime);
        VineryForgeConfig.GRAPEVINE_POT_MAX_STORAGE.set(grapevinePotMaxStorage);
        VineryForgeConfig.GRAPEVINE_POT_REQUIRED_JUMPS.set(grapevinePotRequiredJumps);
        VineryForgeConfig.SHOULD_SHOW_SPLASH_PARTICLES.set(shouldShowSplashParticles);
        VineryForgeConfig.CHERRY_GROWTH_CHANCE.set(cherryGrowthChance);
        VineryForgeConfig.APPLE_GROWTH_CHANCE.set(appleGrowthChance);
        VineryForgeConfig.GRAPE_GROWTH_CHANCE.set(grapeGrowthChance);
        VineryForgeConfig.MAX_LEVEL.set(maxWineLevel);
        VineryForgeConfig.START_DURATION.set(startDuration);
        VineryForgeConfig.DURATION_PER_YEAR.set(durationPerYear);
        VineryForgeConfig.DAYS_PER_YEAR.set(daysPerYear);
        VineryForgeConfig.YEARS_PER_EFFECT_LEVEL.set(yearsPerEffectLevel);
        VineryForgeConfig.MAX_DURATION.set(maxDuration);
        VineryForgeConfig.GIVE_EFFECT.set(giveEffect);
        VineryForgeConfig.SHOW_TOOLTIP.set(showTooltip);
    }

    public static int getTotalFermentationTime() {
        return VineryForgeConfig.TOTAL_FERMENTATION_TIME.get();
    }

    public static int getMaxFluidLevel() {
        return VineryForgeConfig.MAX_FLUID_LEVEL.get();
    }

    public static int getMaxFluidIncrease() {
        return VineryForgeConfig.MAX_FLUID_INCREASE.get();
    }

    public static int getApplePressMashingTime() {
        return VineryForgeConfig.APPLE_PRESS_MASHING_TIME.get();
    }

    public static int getApplePressFermentationTime() {
        return VineryForgeConfig.APPLE_PRESS_FERMENTING_TIME.get();
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

    public static int getGrapevinePotMaxStorage() {
        return VineryForgeConfig.GRAPEVINE_POT_MAX_STORAGE.get();
    }

    public static <T extends Entity> Supplier<EntityType<T>> registerBoatType(String name, EntityType.EntityFactory<T> factory, MobCategory category, float width, float height, int clientTrackingRange) {
        return ENTITY_TYPES.register(name, () -> EntityType.Builder.of(factory, category).sized(width, height).build(name));
    }

    public static boolean shouldShowSplashParticles() {
        return VineryForgeConfig.SHOULD_SHOW_SPLASH_PARTICLES.get();
    }

    public static int getGrapevinePotRequiredJumps() {
        return VineryForgeConfig.GRAPEVINE_POT_REQUIRED_JUMPS.get();
    }
}
