package net.satisfy.vinery.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import java.util.List;

public class PlatformHelper {
    @ExpectPlatform
    public static int getTotalFermentationTime() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static int getMaxFluidLevel() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static int getApplePressMaxProgress() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static double getCherryGrowthChance() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static double getAppleGrowthChance() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static double getGrapeGrowthChance() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static int getWineMaxLevel() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static int getWineStartDuration() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static int getWineDurationPerYear() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static int getWineDaysPerYear() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static int getWineYearsPerEffectLevel() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static int getWineMaxDuration() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static boolean shouldGiveEffect() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static boolean shouldShowTooltip() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void addFlammable(int burnOdd, int igniteOdd, Block[] blocks) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static List<String> getBasketBlacklist() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static double getTraderSpawnChance() {
        throw new AssertionError();
    }
    @ExpectPlatform

    public static boolean shouldSpawnWithMules() {
        throw new AssertionError();
    }
    @ExpectPlatform

    public static int getTraderSpawnDelay() {
        throw new AssertionError();
    }
}
