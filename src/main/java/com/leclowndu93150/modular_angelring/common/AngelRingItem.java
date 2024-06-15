package com.leclowndu93150.modular_angelring.common;

import com.leclowndu93150.modular_angelring.registry.KeyBindRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.common.NeoForgeMod;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AngelRingItem extends Item {

    public AngelRingItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public int getMaxStackSize(@NotNull ItemStack stack) {
        return 1;
    }

    @Override
    public boolean isFoil(@NotNull ItemStack pStack) {
        return true;
    }

    private static void startFlight(Player player) {
        if (!player.isCreative() && !player.isSpectator()) {
            player.getAttribute(NeoForgeMod.CREATIVE_FLIGHT).setBaseValue(1);
        }
    }

    private static void stopFlight(Player player) {
        if (!player.isCreative() && !player.isSpectator()) {
            player.getAttribute(NeoForgeMod.CREATIVE_FLIGHT).setBaseValue(0);
        }
    }

    public void tickRing(ItemStack stack, Player player) {
        if (!(stack.getItem() instanceof AngelRingItem)) return;
        if (player.getAttribute(NeoForgeMod.CREATIVE_FLIGHT).getBaseValue() != 1) {
                startFlight(player);
            }
    }

    public static void tickPlayer(Player player) {
        List<ItemStack> items = new ArrayList<>(player.getInventory().items);
        boolean hasAngelRing = false;
        for (ItemStack item : items) {
            if (item.getItem() instanceof AngelRingItem angelRing) {
                angelRing.tickRing(item, player);
                hasAngelRing = true;
                break;
            }
        }
        if (!hasAngelRing) {
            stopFlight(player);
        }
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext pContext, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pTooltipFlag) {
        if (AngelRingModules.getMiningSpeedModifier(stack) /* && KeyBindRegistry.miningEnabled */) {
            pTooltipComponents.add(Component.literal("Mining Module")/*.append("Enabled")*/.withStyle(ChatFormatting.GRAY));
        }
        if (AngelRingModules.getInertiaModifier(stack) && KeyBindRegistry.inertiaEnabled){
            pTooltipComponents.add(Component.literal("Inertia Module: ").append("Enabled").withStyle(ChatFormatting.GREEN));
        }
        if (AngelRingModules.getMiningSpeedModifier(stack) && !KeyBindRegistry.miningEnabled) {
            pTooltipComponents.add(Component.literal("Mining Module: ").append("Disabled").withStyle(ChatFormatting.RED));
        }
        if (AngelRingModules.getInertiaModifier(stack) && !KeyBindRegistry.inertiaEnabled){
            pTooltipComponents.add(Component.literal("Inertia Module: ").append("Disabled").withStyle(ChatFormatting.RED));
        }

        super.appendHoverText(stack, pContext, pTooltipComponents, pTooltipFlag);
    }
}
