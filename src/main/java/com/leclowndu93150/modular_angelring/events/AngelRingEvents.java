package com.leclowndu93150.modular_angelring.events;

import com.leclowndu93150.modular_angelring.AngelRingMain;
import com.leclowndu93150.modular_angelring.common.AngelRingItem;
import com.leclowndu93150.modular_angelring.registry.KeyBindRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import static com.leclowndu93150.modular_angelring.common.AngelRingModules.getInertiaModifier;
import static com.leclowndu93150.modular_angelring.common.AngelRingModules.getMiningSpeedModifier;

@EventBusSubscriber(modid = AngelRingMain.MODID)
public class AngelRingEvents {
    @SubscribeEvent(priority = EventPriority.LOW)
    public static void setRingBreakSpeed(PlayerEvent.BreakSpeed event) {
        Player player = event.getEntity();
        for (ItemStack item : player.getInventory().items) {
            if (item.getItem() instanceof AngelRingItem) {
                float newDigSpeed = event.getOriginalSpeed();
                if (getMiningSpeedModifier(item) && !player.onGround() && KeyBindRegistry.miningEnabled) {
                    newDigSpeed *= 5f;
                }
                if (newDigSpeed != event.getOriginalSpeed()) {
                    event.setNewSpeed(newDigSpeed);
                }
                break;
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void stopDrift(PlayerTickEvent.Pre event) {
        Player player = event.getEntity();
        for (ItemStack item : player.getInventory().items) {
            if (item.getItem() instanceof AngelRingItem) {
                Vec3 motion = player.getDeltaMovement();
                Options opt = Minecraft.getInstance().options;
                if (player.getAbilities().flying && getInertiaModifier(item) && KeyBindRegistry.inertiaEnabled) {
                    if (!opt.keyUp.isDown() && !opt.keyDown.isDown() && !opt.keyLeft.isDown() && !opt.keyRight.isDown()) {
                        if (motion.x != 0 || motion.z != 0) {
                            player.setDeltaMovement(0, motion.y, 0);
                        }
                    }
                }
                break;
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onPlayerTick(PlayerTickEvent.Pre event) {
        AngelRingItem.tickPlayer(event.getEntity());
    }


    }
