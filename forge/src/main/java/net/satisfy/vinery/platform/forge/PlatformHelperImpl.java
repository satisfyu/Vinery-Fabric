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
    
    public static <T extends Entity> Supplier<EntityType<T>> registerBoatType(String name, EntityType.EntityFactory<T> factory, MobCategory category, float width, float height, int clientTrackingRange) {
        return ENTITY_TYPES.register(name, () -> EntityType.Builder.of(factory, category).sized(width, height).build(name));
    }
}
