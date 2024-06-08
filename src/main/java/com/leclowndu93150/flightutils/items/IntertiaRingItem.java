package com.leclowndu93150.flightutils.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class IntertiaRingItem extends Item {

    public IntertiaRingItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return 1;
    }

}
