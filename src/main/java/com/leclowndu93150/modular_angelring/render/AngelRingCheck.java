package com.leclowndu93150.modular_angelring.render;

import com.leclowndu93150.modular_angelring.registry.DataComponentRegistry;
import com.leclowndu93150.modular_angelring.registry.ItemRegistry;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import java.util.Optional;

public class AngelRingCheck {
    public static boolean isBaseEquipped(AbstractClientPlayer playerEntity) {
        return CuriosApi.getCuriosInventory(playerEntity).flatMap(handler -> handler.findFirstCurio(ItemRegistry.ANGEL_RING.get())).isPresent();
    }

    public static boolean isEquipped(AbstractClientPlayer playerEntity) {
        return isBaseEquipped(playerEntity);
    }

    public static boolean isVisible(AbstractClientPlayer playerEntity) {
        if (isEquipped(playerEntity)) {
            Optional<SlotResult> slotResult = CuriosApi.getCuriosInventory(playerEntity).flatMap(handler -> handler.findFirstCurio(ItemRegistry.ANGEL_RING.get()));
            return slotResult.get().slotContext().visible();
        }
        return false;
    }

    public static String getWingType(AbstractClientPlayer playerEntity) {
        Optional<SlotResult> slotResult = CuriosApi.getCuriosInventory(playerEntity).flatMap(handler -> handler.findFirstCurio(ItemRegistry.ANGEL_RING.get()));
        if(slotResult.isPresent()){
            ItemStack angelRingStack = slotResult.get().stack();
            if(angelRingStack.has(DataComponentRegistry.WING)){
                return angelRingStack.get(DataComponentRegistry.WING);
            }
        }
        return "NO_WING";
    }
}
