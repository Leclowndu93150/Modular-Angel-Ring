package com.leclowndu93150.flightutils.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class AngelRingItem extends Item implements ICurioItem {

    public AngelRingItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public int getMaxStackSize(@NotNull ItemStack stack) {
        return 1;
    }

}
