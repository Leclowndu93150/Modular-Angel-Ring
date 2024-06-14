package com.leclowndu93150.modular_angelring.render;

import com.leclowndu93150.modular_angelring.common.AngelRingItem;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.item.ItemStack;

public class AngelRingCheck {
    public static boolean isBaseEquipped(AbstractClientPlayer playerEntity) {
        for (ItemStack item : playerEntity.getInventory().items) {
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
