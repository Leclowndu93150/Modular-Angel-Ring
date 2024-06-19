package com.leclowndu93150.modular_angelring.events;

import com.leclowndu93150.modular_angelring.AngelRingMain;
import com.leclowndu93150.modular_angelring.common.AngelRingItem;
import com.leclowndu93150.modular_angelring.registry.DataComponentRegistry;
import com.leclowndu93150.modular_angelring.registry.ItemRegistry;
import com.leclowndu93150.modular_angelring.registry.KeyBindRegistry;
import com.leclowndu93150.modular_angelring.utils.FlightSpeedPercentage;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
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
            if (getMiningSpeedModifier(angelRingStack) && !player.onGround() && KeyBindRegistry.miningEnabled) {
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
            Options opt = Minecraft.getInstance().options;
            if (player.getAbilities().flying && getInertiaModifier(angelRingStack) && KeyBindRegistry.inertiaEnabled) {
                if (!opt.keyUp.isDown() && !opt.keyDown.isDown() && !opt.keyLeft.isDown() && !opt.keyRight.isDown()) {
                    if (motion.x != 0 || motion.z != 0) {
                        player.setDeltaMovement(0, motion.y, 0);
                    }
                }
            }
        }
    }


    @SubscribeEvent(priority = EventPriority.LOW)
    public static void newFlightSpeed(PlayerInteractEvent.RightClickItem event) {
        final float MAX_FLY_SPEED = 0.15F;
        final float MIN_FLY_SPEED = 0.0F;
        final float SPEED_INCREMENT = 0.005F;
        Player player = event.getEntity();
        ItemStack item = player.getMainHandItem();

        if (item.is(ItemRegistry.ANGEL_RING) && item.has(DataComponentRegistry.SPEED_MODIFIER)) {
            float currentFlySpeed = getSpeedModifier(item);

            if (player.isCrouching()) {
                currentFlySpeed = Math.max(currentFlySpeed - SPEED_INCREMENT, MIN_FLY_SPEED); // Ensure it doesn't go below the min
            } else if (currentFlySpeed < MAX_FLY_SPEED) {
                currentFlySpeed = Math.min(currentFlySpeed + SPEED_INCREMENT, MAX_FLY_SPEED); // Ensure it doesn't exceed the max
            }

            setSpeedModifier(item, currentFlySpeed);

            int percentage = FlightSpeedPercentage.speedToPercentage(currentFlySpeed);
            player.displayClientMessage(Component.literal("Speed: ")
                    .append(String.valueOf(percentage))
                    .append("%")
                    .withStyle(ChatFormatting.WHITE), true);

            player.level().playSound(player, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.NOTE_BLOCK_BELL.value(), SoundSource.PLAYERS, 0.4f, 0.01f);
        }
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onPlayerTick(PlayerTickEvent.Pre event) {
        AngelRingItem.tickPlayer(event.getEntity());
    }
}
