package com.leclowndu93150.modular_angelring.events;

import com.leclowndu93150.modular_angelring.AngelRingMain;
import com.leclowndu93150.modular_angelring.common.AngelRingItem;
import com.leclowndu93150.modular_angelring.common.EnabledModifiersComponent;
import com.leclowndu93150.modular_angelring.networking.PayloadActions;
import com.leclowndu93150.modular_angelring.registry.DataComponentRegistry;
import com.leclowndu93150.modular_angelring.registry.ItemRegistry;
import com.leclowndu93150.modular_angelring.utils.FlightSpeedPercentage;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import java.util.Optional;

import static com.leclowndu93150.modular_angelring.common.AngelRingModules.*;

@EventBusSubscriber(modid = AngelRingMain.MODID)
public class AngelRingEvents {
    @SubscribeEvent(priority = EventPriority.LOW)
    public static void setRingBreakSpeed(PlayerEvent.BreakSpeed event) {
        Player player = event.getEntity();
        Optional<SlotResult> slotResult = CuriosApi.getCuriosInventory(player).flatMap(handler -> handler.findFirstCurio(ItemRegistry.ANGEL_RING.get()));
        if (slotResult.isPresent()) {
            ItemStack angelRingStack = slotResult.get().stack();
            float newDigSpeed = event.getOriginalSpeed();
            if (getMiningSpeedModifier(angelRingStack) && !player.onGround()) {
                newDigSpeed *= 5f;
            }
            if (newDigSpeed != event.getOriginalSpeed()) {
                event.setNewSpeed(newDigSpeed);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void stopDrift(PlayerTickEvent.Pre event) {
        Player player = event.getEntity();
        Optional<SlotResult> slotResult = CuriosApi.getCuriosInventory(player).flatMap(handler -> handler.findFirstCurio(ItemRegistry.ANGEL_RING.get()));
        if (slotResult.isPresent()) {
            ItemStack angelRingStack = slotResult.get().stack();
            Vec3 motion = player.getDeltaMovement();
            EnabledModifiersComponent data = angelRingStack.getOrDefault(DataComponentRegistry.MODIFIERS_ENABLED, EnabledModifiersComponent.EMPTY);
            if (player.getAbilities().flying && getInertiaModifier(angelRingStack) && data.inertiaEnabled()) {
                if (player.getPersistentData().getBoolean(PayloadActions.NO_KEYS_PRESSED)) {
                    if (motion.x != 0 || motion.z != 0) {
                        player.setDeltaMovement(0, motion.y, 0);
                    }
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onPlayerTick(PlayerTickEvent.Pre event) {
        AngelRingItem.tickPlayer(event.getEntity());
    }

}
