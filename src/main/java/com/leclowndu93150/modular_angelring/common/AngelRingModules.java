package com.leclowndu93150.modular_angelring.common;

import com.leclowndu93150.modular_angelring.registry.DataComponentRegistry;
import net.minecraft.world.item.ItemStack;


public class AngelRingModules {

    public static void setMiningSpeedModifier(ItemStack stack) {
        stack.set(DataComponentRegistry.MINING_MODIFIER, true);
    }

    public static Boolean getMiningSpeedModifier(ItemStack stack) {
        return stack.getOrDefault(DataComponentRegistry.MINING_MODIFIER, false);
    }

    public static void setInertiaModifier(ItemStack stack) {
        stack.set(DataComponentRegistry.INERTIA_MODIFIER, true);
    }

    public static Boolean getInertiaModifier(ItemStack stack) {
        return stack.getOrDefault(DataComponentRegistry.INERTIA_MODIFIER, false);
    }

    public static void setSpeedModifier(ItemStack stack, Float speedModifier) {
        stack.set(DataComponentRegistry.SPEED_MODIFIER, speedModifier);
    }

    public static Float getSpeedModifier(ItemStack stack) {
        return stack.getOrDefault(DataComponentRegistry.SPEED_MODIFIER,  0.02F );
    }

}
