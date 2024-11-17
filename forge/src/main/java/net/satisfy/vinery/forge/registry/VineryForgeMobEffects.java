package net.satisfy.vinery.forge.registry;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.satisfy.vinery.Vinery;
import net.satisfy.vinery.effect.NormalEffect;
import net.satisfy.vinery.effect.instant.CreeperEffect;
import net.satisfy.vinery.effect.instant.TeleportEffect;
import net.satisfy.vinery.effect.normal.ArmorEffect;
import net.satisfy.vinery.effect.normal.ImprovedHealthEffect;
import net.satisfy.vinery.effect.normal.LuckEffect;
import net.satisfy.vinery.effect.normal.ResistanceEffect;
import net.satisfy.vinery.effect.ticking.*;

import java.util.function.Supplier;

public class VineryForgeMobEffects {

    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Vinery.MOD_ID);

    public static void register(IEventBus modEventBus) {
        MOB_EFFECTS.register(modEventBus);
    }

    public static <T extends MobEffect> RegistryObject<T> registerEffect(String name, Supplier<T> effect) {
        return MOB_EFFECTS.register(name, effect);
    }

    public static final RegistryObject<MobEffect> ARMOR_EFFECT = registerEffect("armor_effect", ArmorEffect::new);
    public static final RegistryObject<MobEffect> HEALTH_EFFECT = registerEffect("health_effect", ImprovedHealthEffect::new);
    public static final RegistryObject<MobEffect> LUCK_EFFECT = registerEffect("luck_effect", LuckEffect::new);
    public static final RegistryObject<MobEffect> RESISTANCE_EFFECT = registerEffect("resistance_effect", ResistanceEffect::new);
    public static final RegistryObject<MobEffect> EXPERIENCE_EFFECT = registerEffect("experience_effect", () -> new NormalEffect(MobEffectCategory.BENEFICIAL, 0x00FF00));
    public static final RegistryObject<MobEffect> IMPROVED_JUMP_BOOST = registerEffect("double_jump", () -> new NormalEffect(MobEffectCategory.BENEFICIAL, 0x90F891));
    public static final RegistryObject<MobEffect> PARTY_EFFECT = registerEffect("party_effect", () -> new NormalEffect(MobEffectCategory.BENEFICIAL, 0xFF0000));
    public static final RegistryObject<MobEffect> TELEPORT = registerEffect("teleport", TeleportEffect::new);
    public static final RegistryObject<MobEffect> CREEPER_EFFECT = registerEffect("creeper_effect", CreeperEffect::new);
    public static final RegistryObject<MobEffect> CLIMBING_EFFECT = registerEffect("climbing_effect", ClimbingEffect::new);
    public static final RegistryObject<MobEffect> FROSTY_ARMOR_EFFECT = registerEffect("frosty_armor", FrostyArmorEffect::new);
    public static final RegistryObject<MobEffect> JELLIE = registerEffect("jellie", JellieEffect::new);
    public static final RegistryObject<MobEffect> LAVA_WALKER = registerEffect("lava_walker", LavaWalkerEffect::new);
    public static final RegistryObject<MobEffect> MAGNET = registerEffect("magnet", MagnetEffect::new);
    public static final RegistryObject<MobEffect> STAGGER_EFFECT = registerEffect("staggering", StaggerEffect::new);
    public static final RegistryObject<MobEffect> WATER_WALKER = registerEffect("water_walker", WaterWalkerEffect::new);
}
