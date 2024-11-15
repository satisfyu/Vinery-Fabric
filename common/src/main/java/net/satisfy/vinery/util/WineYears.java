package net.satisfy.vinery.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.satisfy.vinery.platform.PlatformHelper;

public class WineYears {
	public static final int YEARS_START = 0;

	public static int getYear(Level world) {
		return world != null
				? YEARS_START + (int) (world.getDayTime() / 24000 / PlatformHelper.getWineDaysPerYear())
				: YEARS_START;
	}

	public static int getEffectLevel(ItemStack wine, Level world) {
		return Math.max(0, Math.min(
				PlatformHelper.getWineMaxLevel(),
				getWineAge(wine, world) / PlatformHelper.getWineYearsPerEffectLevel()
		));
	}

	public static int getWineAge(ItemStack wine, Level world) {
		if (hasWineYear(wine)) {
			return 0;
		}
		return getYear(world) - getWineYear(wine);
	}

	public static void setWineYear(ItemStack wine, Level world) {
		if (world != null) {
			wine.getOrCreateTag().putInt("Year", getYear(world));
		} else {
			wine.getOrCreateTag().putInt("Year", YEARS_START);
		}
	}

	public static int getWineYear(ItemStack wine) {
		CompoundTag nbt = wine.getOrCreateTag();
		return nbt.getInt("Year");
	}

	public static int getEffectDuration(ItemStack wine, Level world) {
		int age = getWineAge(wine, world);
		int duration = PlatformHelper.getWineStartDuration() + (PlatformHelper.getWineDurationPerYear() * age);
		return Math.min(duration, PlatformHelper.getWineMaxDuration());
	}

	public static boolean hasWineYear(ItemStack wine) {
		return !wine.getOrCreateTag().contains("Year");
	}
}
