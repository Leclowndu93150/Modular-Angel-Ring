package com.leclowndu93150.modular_angelring.common;

import com.leclowndu93150.modular_angelring.registry.ItemRegistry;
import com.leclowndu93150.modular_angelring.registry.KeyBindRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.List;

public class AngelRingItem extends Item {

    public AngelRingItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public int getMaxStackSize(@NotNull ItemStack stack) {
        return 1;
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



    public static void registerCapabilities(final RegisterCapabilitiesEvent evt) {
        evt.registerItem(
                CuriosCapability.ITEM,
                (stack, context) -> new ICurio() {

                    @Override
                    public ItemStack getStack() {
                        return ItemRegistry.ANGEL_RING.toStack();
                    }

                    @Override
                    public void curioTick(SlotContext slotContext) {
                        if (slotContext.entity() instanceof Player player) {
                            if (!player.getAbilities().mayfly) {
                                startFlight(player);
                            }
                        }
                    }

                    @Override
                    public boolean canEquipFromUse(SlotContext slotContext) {
                        return true;
                    }

                    @Override
                    public boolean canEquip(SlotContext slotContext) {
                        return !CuriosApi.getCuriosHelper().findEquippedCurio(ItemRegistry.ANGEL_RING.get(), slotContext.entity()).isPresent();
                    }

                    @Override
                    public void onEquip(SlotContext slotContext, ItemStack prevStack) {
                        if (slotContext.entity() instanceof Player)
                            startFlight((Player) slotContext.entity());
                    }

                    @Override
                    public void onUnequip(SlotContext slotContext, ItemStack newStack) {
                        if (slotContext.entity() instanceof Player player)
                            stopFlight(player);
                    }

                },
                ItemRegistry.ANGEL_RING.get()

        );


    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext pContext, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pTooltipFlag) {
        if (AngelRingModules.getMiningSpeedModifier(stack) && KeyBindRegistry.miningEnabled) {
            pTooltipComponents.add(Component.literal("Mining Module:").append("Enabled").withStyle(ChatFormatting.GREEN));
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
