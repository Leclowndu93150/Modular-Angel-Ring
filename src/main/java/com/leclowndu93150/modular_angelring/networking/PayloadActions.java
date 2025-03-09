package com.leclowndu93150.modular_angelring.networking;

import com.leclowndu93150.modular_angelring.common.EnabledModifiersComponent;
import com.leclowndu93150.modular_angelring.registry.AttachementRegistry;
import com.leclowndu93150.modular_angelring.registry.DataComponentRegistry;
import com.leclowndu93150.modular_angelring.registry.ItemRegistry;
import com.leclowndu93150.modular_angelring.registry.KeyBindRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import java.util.Optional;

public final class PayloadActions {

    public static final String NO_KEYS_PRESSED = "no_keys_pressed";

    public static void keyPressedAction(KeyPressedPayload payload, IPayloadContext ctx) {
        Player player = ctx.player();
        int key = payload.key();
        Optional<SlotResult> slotResult = CuriosApi.getCuriosInventory(player).flatMap(handler -> handler.findFirstCurio(ItemRegistry.ANGEL_RING.get()));
        if (slotResult.isPresent()) {
            ItemStack itemStack = slotResult.get().stack();
            EnabledModifiersComponent data = itemStack.getOrDefault(DataComponentRegistry.MODIFIERS_ENABLED, EnabledModifiersComponent.EMPTY);

            switch (key) {
                case 0 -> { // Inertia
                    if (itemStack.has(DataComponentRegistry.INERTIA_MODIFIER)) {
                        itemStack.set(DataComponentRegistry.MODIFIERS_ENABLED,
                                new EnabledModifiersComponent(!data.inertiaEnabled(), data.speedModifierEnabled(),
                                        data.miningEnabled(), data.nightVisionEnabled(), data.magnetEnabled()));
                    }
                }
                case 1 -> { // Speed
                    if (itemStack.has(DataComponentRegistry.SPEED_MODIFIER)) {
                        itemStack.set(DataComponentRegistry.MODIFIERS_ENABLED,
                                new EnabledModifiersComponent(data.inertiaEnabled(), !data.speedModifierEnabled(),
                                        data.miningEnabled(), data.nightVisionEnabled(), data.magnetEnabled()));
                    }
                }
                case 2 -> { // Night Vision
                    if (itemStack.has(DataComponentRegistry.NIGHT_VISION_MODIFIER)) {
                        boolean newNightVisionState = !data.nightVisionEnabled();
                        itemStack.set(DataComponentRegistry.MODIFIERS_ENABLED,
                                new EnabledModifiersComponent(data.inertiaEnabled(), data.speedModifierEnabled(),
                                        data.miningEnabled(), newNightVisionState, data.magnetEnabled()));

                        AttachementRegistry.setNightVision(player, newNightVisionState);
                        if (!newNightVisionState) {
                            player.removeEffect(MobEffects.NIGHT_VISION);
                        }
                    }
                }
                case 3 -> { // Magnet
                    if (itemStack.has(DataComponentRegistry.MAGNET_MODIFIER)) {
                        itemStack.set(DataComponentRegistry.MODIFIERS_ENABLED,
                                new EnabledModifiersComponent(data.inertiaEnabled(), data.speedModifierEnabled(),
                                        data.miningEnabled(), data.nightVisionEnabled(), !data.magnetEnabled()));
                    }
                }
            }
        }
    }

    public static void noKeyPressedAction(NoKeyPressedPayload payload, IPayloadContext ctx) {
        // SERVER-SIDE
        Player player = ctx.player();
        player.getPersistentData().putBoolean(NO_KEYS_PRESSED, payload.pressed());
    }
}
