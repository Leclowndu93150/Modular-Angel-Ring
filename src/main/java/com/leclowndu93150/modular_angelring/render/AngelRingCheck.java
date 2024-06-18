package com.leclowndu93150.modular_angelring.render;

import com.leclowndu93150.modular_angelring.registry.ItemRegistry;
import net.minecraft.client.player.AbstractClientPlayer;
import top.theillusivec4.curios.api.CuriosApi;

public class AngelRingCheck {
    public static boolean isBaseEquipped(AbstractClientPlayer playerEntity) {
        return CuriosApi.getCuriosInventory(playerEntity).flatMap(handler -> handler.findFirstCurio(ItemRegistry.ANGEL_RING.get())).isPresent();
    }

    public static boolean isEquipped(AbstractClientPlayer playerEntity) {
        return isBaseEquipped(playerEntity);
    }
}
