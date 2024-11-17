package net.satisfy.vinery.util;

import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.satisfy.vinery.registry.MobEffectRegistry;

import java.util.function.Supplier;
public enum Wine {
    CHERRY_WINE(() -> MobEffects.REGENERATION),
    MAGNETIC_WINE(MobEffectRegistry.MAGNET),
    JO_SPECIAL_MIXTURE(MobEffectRegistry.CLIMBING_EFFECT),
    CRISTEL_WINE(MobEffectRegistry.EXPERIENCE_EFFECT),
    GLOWING_WINE(() -> MobEffects.GLOWING),
    RED_WINE(MobEffectRegistry.STAGGER_EFFECT),
    JELLIE_WINE(MobEffectRegistry.JELLIE),
    STAL_WINE(MobEffectRegistry.HEALTH_EFFECT),
    BOLVAR_WINE(MobEffectRegistry.LAVA_WALKER),
    SOLARIS_WINE(() -> MobEffects.DIG_SPEED),
    EISWEIN(MobEffectRegistry.FROSTY_ARMOR_EFFECT),
    CHENET_WINE(MobEffectRegistry.CLIMBING_EFFECT),
    KELP_CIDER(MobEffectRegistry.WATER_WALKER),
    AEGIS_WINE(MobEffectRegistry.ARMOR_EFFECT),
    CLARK_WINE(MobEffectRegistry.IMPROVED_JUMP_BOOST),
    MELLOW_WINE(MobEffectRegistry.LUCK_EFFECT),
    STRAD_WINE(MobEffectRegistry.RESISTANCE_EFFECT),
    APPLE_CIDER(() -> MobEffects.HEALTH_BOOST),
    APPLE_METH(() -> MobEffects.ABSORPTION),
    LILITUL_WINE(MobEffectRegistry.PARTY_EFFECT),
    BOTTLE_MOJANG_NOIR(MobEffectRegistry.ARMOR_EFFECT),
    VILLAGERS_FRIGHT(() -> MobEffects.BAD_OMEN),
    CHORUS_WINE(MobEffectRegistry.TELEPORT),
    CREEPERS_CRUSH(MobEffectRegistry.CREEPER_EFFECT),
    MEAD(() -> MobEffects.SATURATION),
    NOIR_WINE(() -> MobEffects.HEAL);

    private final LazyLoadedValue<MobEffect> effect;

    Wine(Supplier<MobEffect> effectSupplier) {
        this.effect = new LazyLoadedValue<>(effectSupplier);
    }

    public MobEffect getEffect() {
        return this.effect.get();
    }
}

