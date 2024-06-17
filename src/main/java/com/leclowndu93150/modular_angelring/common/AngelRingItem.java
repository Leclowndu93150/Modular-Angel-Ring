package com.leclowndu93150.modular_angelring.common;

import com.leclowndu93150.modular_angelring.registry.ItemRegistry;
import com.leclowndu93150.modular_angelring.registry.KeyBindRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.common.NeoForgeMod;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public static void tickRing(ItemStack stack, Player player) {
        if (!(stack.getItem() instanceof AngelRingItem)) return;
        if (player.getAttribute(NeoForgeMod.CREATIVE_FLIGHT).getBaseValue() != 1) {
                startFlight(player);
            }
    }

    public static void tickPlayer(Player player) {
        Optional<SlotResult> slotResult = CuriosApi.getCuriosHelper().getCuriosHandler(player).flatMap(curiosHandler -> curiosHandler.findFirstCurio(ItemRegistry.ANGEL_RING.get()));
        if (slotResult.isPresent()) {
            ItemStack angelRingStack = slotResult.get().stack();
                tickRing(angelRingStack, player);
            }
        if (slotResult.isEmpty()) {
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
