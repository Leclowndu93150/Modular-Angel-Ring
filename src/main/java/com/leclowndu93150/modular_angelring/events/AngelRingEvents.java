package com.leclowndu93150.modular_angelring.events;

import com.leclowndu93150.modular_angelring.AngelRingMain;
import com.leclowndu93150.modular_angelring.registry.ItemRegistry;
import com.leclowndu93150.modular_angelring.registry.KeyBindRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import java.util.Optional;

import static com.leclowndu93150.modular_angelring.common.AngelRingModules.getInertiaModifier;
import static com.leclowndu93150.modular_angelring.common.AngelRingModules.getMiningSpeedModifier;

@EventBusSubscriber(modid = AngelRingMain.MODID)
public class AngelRingEvents {

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void setRingBreakSpeed(PlayerEvent.BreakSpeed event) {
        Optional<SlotResult> slotResult = CuriosApi.getCuriosHelper().getCuriosHandler(event.getEntity()).flatMap(curiosHandler -> curiosHandler.findFirstCurio(ItemRegistry.ANGEL_RING.get()));
        if (slotResult.isPresent()) {
            ItemStack angelRingStack = slotResult.get().stack();
            float newDigSpeed = event.getOriginalSpeed();
            if (getMiningSpeedModifier(angelRingStack) && !event.getEntity().onGround() && KeyBindRegistry.miningEnabled) {
                newDigSpeed *= 5f;
            }
            if (newDigSpeed != event.getOriginalSpeed()) {
                event.setNewSpeed(newDigSpeed);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void stopDrift(PlayerTickEvent.Pre event) {
            Optional<SlotResult> slotResult = CuriosApi.getCuriosHelper().getCuriosHandler(event.getEntity()).flatMap(curiosHandler -> curiosHandler.findFirstCurio(ItemRegistry.ANGEL_RING.get()));
            if (slotResult.isPresent()) {
                ItemStack angelRingStack = slotResult.get().stack();
                Vec3 motion = event.getEntity().getDeltaMovement();
                if (event.getEntity().getAbilities().flying && getInertiaModifier(angelRingStack) && KeyBindRegistry.inertiaEnabled) {
                    Options opt = Minecraft.getInstance().options;
                    if (!opt.keyUp.isDown() && !opt.keyDown.isDown() && !opt.keyLeft.isDown() && !opt.keyRight.isDown()) {
                        if (motion.x != 0 || motion.z != 0) {
                            event.getEntity().setDeltaMovement(0, motion.y, 0);
                        }
                    }
                }
            }
    }
}
