package net.satisfy.vinery.core.item;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.satisfy.vinery.core.registry.ArmorRegistryClient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WinemakerChestItem extends ArmorItem {
    private final ResourceLocation chestplateTexture;

    public WinemakerChestItem(ArmorMaterial armorMaterial, Type type, Properties properties, ResourceLocation chestplateTexture) {
        super(armorMaterial, type, properties);
        this.chestplateTexture = chestplateTexture;
    }

    public ResourceLocation getChestplateTexture() {
        return chestplateTexture;
    }

    @Override
    public @NotNull EquipmentSlot getEquipmentSlot() {
        return EquipmentSlot.CHEST;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level world, @NotNull List<Component> tooltip, @NotNull TooltipFlag context) {
        if (world != null && world.isClientSide()) {
            ArmorRegistryClient.appendToolTip(tooltip);
        }
    }
}
