package com.leclowndu93150.flightutils.items;

import com.leclowndu93150.flightutils.registry.ItemRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

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

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getMainHandItem();
        pPlayer.sendSystemMessage(Component.literal(itemStack.getTags().toList().toString()));
        return super.use(pLevel, pPlayer, pUsedHand);
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
}
