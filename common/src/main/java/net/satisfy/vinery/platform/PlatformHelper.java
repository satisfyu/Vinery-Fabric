package net.satisfy.vinery.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;

public class PlatformHelper {
    @ExpectPlatform
    public static int getTotalFermentationTime() {
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
}