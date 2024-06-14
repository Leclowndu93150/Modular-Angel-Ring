package com.leclowndu93150.modular_angelring.common;

import com.leclowndu93150.modular_angelring.registry.KeyBindRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;

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
            player.getAbilities().mayfly = true;
            player.onUpdateAbilities();
        }
    }

    private static void stopFlight(Player player) {
        if (!player.isCreative() && !player.isSpectator()) {
            player.getAbilities().flying = false;
            player.getAbilities().mayfly = false;
            player.onUpdateAbilities();
        }
    }

    public void tickRing(ItemStack stack, Player player) {
        if (stack.getItem() != this) return;
        if (!player.getAbilities().mayfly) {
                startFlight(player);
            }
    }

    static Boolean canFlyWithRing = false;
    public static void tickPlayer(Player player) {
        for (ItemStack item : player.getInventory().items) {
            if (item.getItem() instanceof AngelRingItem angelRing) {
                angelRing.tickRing(item,player);
            } else{
                stopFlight(player);
            }
            break;
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
