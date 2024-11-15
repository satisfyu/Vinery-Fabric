package net.satisfy.vinery.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class WineYears {
	public static final int YEARS_START = 0;
	public static final int MAX_LEVEL = 5;
	public static final int START_DURATION = 1800;
	public static final int DURATION_PER_YEAR = 200;
	public static final int DAYS_PER_YEAR = 24;
	public static final int YEARS_PER_EFFECT_LEVEL = 6;
	public static final String AMPLIFIER_KEY = "Amplifier";

	public static int getYear(Level world) {
		return world != null ? YEARS_START + (int) (world.getDayTime() / 24000 / DAYS_PER_YEAR) : YEARS_START;
	}

	public static int getEffectLevel(ItemStack wine, Level world) {
		return Math.max(0, Math.min(MAX_LEVEL, getWineAge(wine, world) / YEARS_PER_EFFECT_LEVEL));
	}

	public static int getWineAge(ItemStack wine, Level world) {
		if (!hasWineYear(wine)) return -1;
		return getYear(world) - getWineYear(wine);
	}

	public static void setWineYear(ItemStack wine, Level world) {
		wine.getOrCreateTag().putInt("Year", getYear(world));
	}

	public static int getWineYear(ItemStack wine) {
		CompoundTag nbt = wine.getOrCreateTag();
		return nbt.getInt("Year");
	}

	public static int getEffectDuration(ItemStack wine, Level world) {
		int age = getWineAge(wine, world);
		return START_DURATION + (DURATION_PER_YEAR * age);
	}

	public static void createNewWine(ItemStack wine) {
		setWineYearToZero(wine);
		setAmplifier(wine, 0);
	}

	public static void setAmplifier(ItemStack wine, int amplifier) {
		wine.getOrCreateTag().putInt(AMPLIFIER_KEY, amplifier);
	}

	public static int getAmplifier(ItemStack wine) {
		CompoundTag nbt = wine.getOrCreateTag();
		return nbt.getInt(AMPLIFIER_KEY);
	}

	public static boolean hasWineYear(ItemStack wine) {
		return wine.getOrCreateTag().contains("Year");
	}

	public static void setWineYearToZero(ItemStack wine) {
		CompoundTag nbt = wine.getOrCreateTag();
		nbt.putInt("Year", YEARS_START);
	}
}
