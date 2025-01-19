package net.satisfy.vinery.core.mixin;

import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;
import net.satisfy.vinery.core.registry.MobEffectRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Field;
import java.util.Objects;

@Mixin(ExperienceOrb.class)
public abstract class ExperienceOrbMixin {

    @Inject(method = "playerTouch", at = @At("HEAD"))
    public void onPlayerTouch(Player player, CallbackInfo ci) {
        if (player.hasEffect(MobEffectRegistry.EXPERIENCE_EFFECT.get())) {
            int amplifier = Objects.requireNonNull(player.getEffect(MobEffectRegistry.EXPERIENCE_EFFECT.get())).amplifier;
            ExperienceOrb self = (ExperienceOrb) (Object) this;

            try {
                Field valueField = ExperienceOrb.class.getDeclaredField("value");
                valueField.setAccessible(true);

                int originalXp = (int) valueField.get(self);

                int modifiedXp = (int) (originalXp + (originalXp * amplifier * 0.5));

                valueField.set(self, modifiedXp);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException("Failed to access ExperienceOrb value field", e);
            }
        }
    }
}
