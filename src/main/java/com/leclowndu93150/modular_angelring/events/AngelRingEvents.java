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
        if (player.onGround()) return;

        CuriosApi.getCuriosInventory(player)
                .flatMap(handler -> handler.findFirstCurio(ItemRegistry.ANGEL_RING.get()))
                .ifPresent(slotResult -> {
                    ItemStack angelRingStack = slotResult.stack();
                    if (getMiningSpeedModifier(angelRingStack)) {
                        event.setNewSpeed(event.getOriginalSpeed() * 5f);
                    }
                });
    }

    @SubscribeEvent
    public static void stopDrift(PlayerTickEvent.Pre event) {
        Player player = event.getEntity();
        if (!player.getAbilities().flying) return;

        CuriosApi.getCuriosInventory(player)
                .flatMap(handler -> handler.findFirstCurio(ItemRegistry.ANGEL_RING.get()))
                .ifPresent(slotResult -> {
                    ItemStack angelRingStack = slotResult.stack();
                    EnabledModifiersComponent data = angelRingStack.getOrDefault(DataComponentRegistry.MODIFIERS_ENABLED, EnabledModifiersComponent.EMPTY);

                    if (getInertiaModifier(angelRingStack) && data.inertiaEnabled() &&
                            player.getPersistentData().getBoolean(PayloadActions.NO_KEYS_PRESSED)) {
                        Vec3 motion = player.getDeltaMovement();
                        if (motion.x != 0 || motion.z != 0) {
                            player.setDeltaMovement(
                                    motion.x * AngelRingConfig.slowdownFactor,
                                    motion.y,
                                    motion.z * AngelRingConfig.slowdownFactor
                            );
                        }
                    }
                });
    }


    @SubscribeEvent
    public static void useMagnet(PlayerTickEvent.Pre event) {
        Player player = event.getEntity();
        if (player.isCrouching()) return;

        CuriosApi.getCuriosInventory(player)
                .flatMap(handler -> handler.findFirstCurio(ItemRegistry.ANGEL_RING.get()))
                .ifPresent(slotResult -> {
                    ItemStack angelRingStack = slotResult.stack();
                    EnabledModifiersComponent data = angelRingStack.getOrDefault(DataComponentRegistry.MODIFIERS_ENABLED, EnabledModifiersComponent.EMPTY);

                    if (getMagnetModifier(angelRingStack) && data.magnetEnabled()) {
                        double radius = AngelRingConfig.magnetRadius;
                        Vec3 playerPos = player.position();

                        player.level().getEntitiesOfClass(ItemEntity.class, player.getBoundingBox().inflate(radius))
                                .forEach(itemEntity -> {
                                    Vec3 pullVec = playerPos.subtract(itemEntity.position())
                                            .normalize()
                                            .scale(AngelRingConfig.magnetPullSpeed);
                                    itemEntity.setDeltaMovement(itemEntity.getDeltaMovement().add(pullVec));
                                });
                    }
                });
    }
}
