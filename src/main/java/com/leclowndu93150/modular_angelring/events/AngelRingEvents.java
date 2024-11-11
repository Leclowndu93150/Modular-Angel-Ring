package com.leclowndu93150.modular_angelring.events;

import com.leclowndu93150.modular_angelring.AngelRingConfig;
import com.leclowndu93150.modular_angelring.AngelRingMain;
import com.leclowndu93150.modular_angelring.common.EnabledModifiersComponent;
import com.leclowndu93150.modular_angelring.networking.PayloadActions;
import com.leclowndu93150.modular_angelring.registry.DataComponentRegistry;
import com.leclowndu93150.modular_angelring.registry.ItemRegistry;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import java.util.List;
import java.util.Optional;

import static com.leclowndu93150.modular_angelring.common.AngelRingModules.*;

@EventBusSubscriber(modid = AngelRingMain.MODID)
public class AngelRingEvents {
    @SubscribeEvent
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

    @SubscribeEvent
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
                        player.setDeltaMovement(motion.x * AngelRingConfig.slowdownFactor, motion.y, motion.z * AngelRingConfig.slowdownFactor);
                    }
                }
            }
        }
    }


    @SubscribeEvent
    public static void useMagnet(PlayerTickEvent.Pre event){
        Player player = event.getEntity();
        Optional<SlotResult> slotResult = CuriosApi.getCuriosInventory(player).flatMap(handler -> handler.findFirstCurio(ItemRegistry.ANGEL_RING.get()));
        if (slotResult.isPresent()) {
            ItemStack angelRingStack = slotResult.get().stack();
            EnabledModifiersComponent data = angelRingStack.getOrDefault(DataComponentRegistry.MODIFIERS_ENABLED, EnabledModifiersComponent.EMPTY);
            if (!player.isCrouching() && getMagnetModifier(angelRingStack) && data.magnetEnabled()) {
                List<ItemEntity> nearbyItems = player.level().getEntitiesOfClass(ItemEntity.class, player.getBoundingBox().inflate(AngelRingConfig.magnetRadius));
                for (ItemEntity itemEntity : nearbyItems) {
                    Vec3 pullVec = player.position().subtract(itemEntity.position()).normalize().scale(AngelRingConfig.magnetPullSpeed);
                    itemEntity.setPickUpDelay(0);
                    itemEntity.setDeltaMovement(itemEntity.getDeltaMovement().add(pullVec));
                }
            }
        }
    }
}
