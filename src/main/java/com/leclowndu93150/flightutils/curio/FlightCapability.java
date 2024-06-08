package com.leclowndu93150.flightutils.curio;

import com.leclowndu93150.flightutils.registry.ItemRegistry;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class FlightCapability {

    private void startFlight(Player player){
        if(!player.isCreative() && !player.isSpectator()){
            player.getAbilities().mayfly = true;
            player.onUpdateAbilities();
        }
    }

    private void stopFlight(Player player) {
        if (!player.isCreative() && !player.isSpectator()) {
            player.getAbilities().flying = false;
            player.getAbilities().mayfly = false;
            player.onUpdateAbilities();
        }
    }

    public void registerCapabilities(final RegisterCapabilitiesEvent evt) {
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
                    return CuriosApi.getCuriosHelper().findEquippedCurio(ItemRegistry.ANGEL_RING.get(), slotContext.entity()).isEmpty();
                }

                @Override
                public void onEquip(SlotContext slotContext, ItemStack prevStack) {
                    if (slotContext.getWearer() instanceof Player)
                        startFlight((Player) slotContext.getWearer());
                }

                @Override
                public void onUnequip(SlotContext slotContext, ItemStack newStack) {
                    if (slotContext.getWearer() instanceof Player)
                        stopFlight((Player) slotContext.getWearer());
                }

            }

    );
}
}

