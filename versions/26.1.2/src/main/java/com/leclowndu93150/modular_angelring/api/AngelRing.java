package com.leclowndu93150.modular_angelring.api;

import com.leclowndu93150.modular_angelring.registry.ItemRegistry;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import java.util.Optional;

public final class AngelRing {
    private AngelRing() {
    }

    public static Optional<SlotResult> findSlot(LivingEntity entity) {
        return CuriosApi.getCuriosInventory(entity)
                .flatMap(inventory -> inventory.findFirstCurio(ItemRegistry.ANGEL_RING.get()));
    }

    public static ItemStack find(LivingEntity entity) {
        return findSlot(entity).map(SlotResult::stack).orElse(ItemStack.EMPTY);
    }
}
