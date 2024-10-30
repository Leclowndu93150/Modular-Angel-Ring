package com.leclowndu93150.modular_angelring.registry;

import com.leclowndu93150.modular_angelring.networking.NoKeyPressedPayload;
import com.leclowndu93150.modular_angelring.networking.PayloadActions;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.network.PacketDistributor;
import org.lwjgl.glfw.GLFW;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import java.util.Optional;

import static com.leclowndu93150.modular_angelring.AngelRingMain.MODID;

@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class KeyBindRegistry {
    public static final Lazy<KeyMapping> INERTIA_MODULE = Lazy.of(() -> new KeyMapping(
            "Inertia Module", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_L, "Modular Angel Ring"));

    public static final Lazy<KeyMapping> SPEED_MODULE = Lazy.of(() -> new KeyMapping(
            "Speed Module", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_K, "Modular Angel Ring"));

    public static final Lazy<KeyMapping> NIGHT_VISION_MODULE = Lazy.of(() -> new KeyMapping(
            "Night Vision Module", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_N, "Modular Angel Ring"));

    public static final Lazy<KeyMapping> MAGNET_MODULE = Lazy.of(() -> new KeyMapping(
            "Magnet Module", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_P, "Modular Angel Ring"));

    @SubscribeEvent
    public static void registerBindings(RegisterKeyMappingsEvent event) {
        event.register(SPEED_MODULE.get());
        event.register(INERTIA_MODULE.get());
        event.register(NIGHT_VISION_MODULE.get());
        event.register(MAGNET_MODULE.get());
    }

    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
    public static class Client {
        @SubscribeEvent
        public static void clientTick(ClientTickEvent.Post event) {
            Minecraft mc = Minecraft.getInstance();
            Options opt = mc.options;
            Player player = mc.player;
            Optional<SlotResult> slotResult = CuriosApi.getCuriosInventory(player).flatMap(handler -> handler.findFirstCurio(ItemRegistry.ANGEL_RING.get()));
            if (mc.level != null) {
                if (mc.level.isClientSide()) {
                    if (slotResult.isPresent() && slotResult.get().stack().has(DataComponentRegistry.INERTIA_MODIFIER)){
                        CompoundTag persistentData = player.getPersistentData();
                        if (opt.keyUp.isDown() || opt.keyDown.isDown() || opt.keyLeft.isDown() || opt.keyRight.isDown()) {
                            PacketDistributor.sendToServer(new NoKeyPressedPayload(false));
                            persistentData.putBoolean(PayloadActions.NO_KEYS_PRESSED, false);
                        } else {
                            PacketDistributor.sendToServer(new NoKeyPressedPayload(true));
                            persistentData.putBoolean(PayloadActions.NO_KEYS_PRESSED, true);
                        }
                    }
                }
            }
        }

    }

}
