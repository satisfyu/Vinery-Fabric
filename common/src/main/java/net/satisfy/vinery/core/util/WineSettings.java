package net.satisfy.vinery.core.util;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item.Properties;

import java.util.function.Supplier;

public class WineSettings {
    private final Properties properties;
    private final int baseDuration;

    public WineSettings(Supplier<MobEffect> effect, int duration, int strength) {
        this.baseDuration = duration;
        this.properties = new Properties()
                .food(createWineFoodComponent(effect, duration, strength));
    }

    public Properties getProperties() {
        return properties;
    }

    public int getBaseDuration() {
        return baseDuration;
    }

    private FoodProperties createWineFoodComponent(Supplier<MobEffect> effect, int duration, int strength) {
        FoodProperties.Builder builder = new FoodProperties.Builder()
                .alwaysEat();
        if (effect != null) {
            builder.effect(new MobEffectInstance(effect.get(), duration, strength), 1.0f);
        }
        return builder.build();
    }
}
