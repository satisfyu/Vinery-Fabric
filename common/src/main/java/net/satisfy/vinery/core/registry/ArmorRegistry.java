package net.satisfy.vinery.core.registry;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.satisfy.vinery.core.item.WinemakerBootsItem;
import net.satisfy.vinery.core.item.WinemakerChestItem;
import net.satisfy.vinery.core.item.WinemakerHelmetItem;
import net.satisfy.vinery.core.item.WinemakerLegsItem;

public class ArmorRegistry {
    public static boolean setBonusActive = false;

    public static void checkArmorSet(Player player) {
        ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
        ItemStack chestplate = player.getItemBySlot(EquipmentSlot.CHEST);
        ItemStack leggings = player.getItemBySlot(EquipmentSlot.LEGS);
        ItemStack boots = player.getItemBySlot(EquipmentSlot.FEET);

        setBonusActive = helmet.getItem() instanceof WinemakerHelmetItem &&
                chestplate.getItem() instanceof WinemakerChestItem &&
                leggings.getItem() instanceof WinemakerLegsItem &&
                boots.getItem() instanceof WinemakerBootsItem;
    }
}
