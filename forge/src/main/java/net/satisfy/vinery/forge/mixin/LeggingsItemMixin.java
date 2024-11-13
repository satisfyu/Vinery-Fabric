package net.satisfy.vinery.forge.mixin;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.satisfy.vinery.item.WinemakerLegsItem;
import net.satisfy.vinery.registry.ArmorRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.function.Consumer;

@SuppressWarnings("all")
@Mixin(WinemakerLegsItem.class)
public abstract class LeggingsItemMixin extends ArmorItem {
    @Shadow
    @Final
    private ResourceLocation leggingsTexture;

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(
                new IClientItemExtensions() {
                    @Override
                    public @NotNull Model getGenericArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                        return ArmorRegistry.getLeggingsModel(itemStack.getItem(), original.rightLeg, original.leftLeg);
                    }
                }
        );
    }

    @Override
    public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return leggingsTexture.toString();
    }

    private LeggingsItemMixin(ArmorMaterial armorMaterial, Type armorType, Properties itemProperties) {
        super(armorMaterial, armorType, itemProperties);
    }
}
