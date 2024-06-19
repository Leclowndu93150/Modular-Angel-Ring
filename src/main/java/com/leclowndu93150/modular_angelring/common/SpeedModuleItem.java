package com.leclowndu93150.modular_angelring.common;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SpeedModuleItem extends Item {
    public SpeedModuleItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return 1;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @NotNull TooltipContext pContext, List<Component> pTooltipComponents, @NotNull TooltipFlag pTooltipFlag) {
        pTooltipComponents.add(Component.literal("Allows you to configure your flight speed. (Range: 0% - 300%)").withStyle(ChatFormatting.GRAY));
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
    }
}
