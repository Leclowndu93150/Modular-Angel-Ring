package com.leclowndu93150.modular_angelring.render;

import com.leclowndu93150.modular_angelring.common.AngelRingItem;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class AngelRingCheck {
    public static boolean isBaseEquipped(AbstractClientPlayer playerEntity) {
        List<ItemStack> items = playerEntity.getInventory().items;
        for (ItemStack item : items) {
            if (item.getItem() instanceof AngelRingItem) {
                return true;
            }
        }
        return false;
    }

    public static boolean isEquipped(AbstractClientPlayer playerEntity) {
        return isBaseEquipped(playerEntity);
    }
}
