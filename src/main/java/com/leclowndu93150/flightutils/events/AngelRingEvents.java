package com.leclowndu93150.flightutils.events;

import com.leclowndu93150.flightutils.FlightUtilsMain;
import com.leclowndu93150.flightutils.registry.ItemRegistry;
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

import static com.leclowndu93150.flightutils.common.AngelRingModules.getInertiaModifier;
import static com.leclowndu93150.flightutils.common.AngelRingModules.getMiningSpeedModifier;

@EventBusSubscriber(modid = FlightUtilsMain.MODID)
public class AngelRingEvents {

    @SubscribeEvent(priority = EventPriority.LOW)
    public void setRingBreakSpeed(PlayerEvent.BreakSpeed event) {
        ItemStack angelRingStack = CuriosApi.getCuriosHelper().getCuriosHandler(event.getEntity()).get().findFirstCurio(ItemRegistry.ANGEL_RING.get()).get().stack();
        float newDigSpeed = event.getOriginalSpeed();
        if (getMiningSpeedModifier(angelRingStack) && !event.getEntity().onGround()){
            newDigSpeed *= 5f;
        }
        if (newDigSpeed != event.getOriginalSpeed()) {
            event.setNewSpeed(newDigSpeed);
        }
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void stopDrift(PlayerTickEvent event) {
        ItemStack angelRingStack = CuriosApi.getCuriosHelper().getCuriosHandler(event.getEntity()).get().findFirstCurio(ItemRegistry.ANGEL_RING.get()).get().stack();
        Vec3 motion = event.getEntity().getDeltaMovement();
        if(event.getEntity().getAbilities().flying && getInertiaModifier(angelRingStack)){
            Options opt = Minecraft.getInstance().options;
            if (!opt.keyUp.isDown() && !opt.keyDown.isDown() && !opt.keyLeft.isDown() && !opt.keyRight.isDown()) {
                if (motion.x != 0 || motion.z != 0) {
                    event.getEntity().setDeltaMovement(0, motion.y, 0);
                }
            }
        }
    }
}
