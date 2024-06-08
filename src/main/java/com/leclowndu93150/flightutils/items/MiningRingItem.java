package com.leclowndu93150.flightutils.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class MiningRingItem extends Item implements ICurioItem {

    public MiningRingItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return 1;
    }
}
