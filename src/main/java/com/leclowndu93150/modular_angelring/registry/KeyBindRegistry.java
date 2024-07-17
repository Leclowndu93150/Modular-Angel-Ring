package com.leclowndu93150.modular_angelring.registry;

import com.leclowndu93150.modular_angelring.common.AngelRingModules;
import com.leclowndu93150.modular_angelring.networking.KeyPressedPayload;
import com.leclowndu93150.modular_angelring.networking.PayloadActions;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.network.PacketDistributor;
import org.lwjgl.glfw.GLFW;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import java.util.Optional;

import static com.leclowndu93150.modular_angelring.AngelRingMain.MODID;

@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD)
public class KeyBindRegistry {
    public static final Lazy<KeyMapping> INERTIA_MODULE = Lazy.of(() -> new KeyMapping(
            "Inertia Module", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_L, "Modular Angel Ring"));

    public static final Lazy<KeyMapping> SPEED_MODULE = Lazy.of(() -> new KeyMapping(
            "Speed Module", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_K, "Modular Angel Ring"));


    @SubscribeEvent
    public static void registerBindings(RegisterKeyMappingsEvent event) {
        event.register(SPEED_MODULE.get());
        event.register(INERTIA_MODULE.get());
        NeoForge.EVENT_BUS.addListener(KeyBindRegistry::onKey);
    }

    public static boolean inertiaEnabled = true;
    public static boolean speedEnabled = true;

    public static void onKey(InputEvent.Key event) {
        Player player = Minecraft.getInstance().player;
        if (player == null) return;
        Optional<SlotResult> slotResult = CuriosApi.getCuriosInventory(player).flatMap(handler -> handler.findFirstCurio(ItemRegistry.ANGEL_RING.get()));
        if (slotResult.isPresent()) {
            ItemStack angelRingStack = slotResult.get().stack();
            Level level = player.level();
            if (INERTIA_MODULE.get().consumeClick() && AngelRingModules.getInertiaModifier(angelRingStack)) {
                inertiaEnabled = !inertiaEnabled;
                PacketDistributor.sendToServer(new KeyPressedPayload(INERTIA_MODULE.get().getKey().getValue()));
                if (inertiaEnabled) {
                    player.displayClientMessage(Component.literal("Inertia Module: Enabled").withStyle(ChatFormatting.GREEN), true);
                    level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.NOTE_BLOCK_BELL.value(), SoundSource.PLAYERS, 0.4f, 0.01f);
                } else {
                    player.displayClientMessage(Component.literal("Inertia Module: Disabled").withStyle(ChatFormatting.RED), true);
                    level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.NOTE_BLOCK_BELL.value(), SoundSource.PLAYERS, 0.4f, 0.09f);
                }
            }
            if (SPEED_MODULE.get().consumeClick() && angelRingStack.has(DataComponentRegistry.SPEED_MODIFIER)) {
                speedEnabled = !speedEnabled;
                PacketDistributor.sendToServer(new KeyPressedPayload(SPEED_MODULE.get().getKey().getValue()));
                if (speedEnabled) {
                    player.displayClientMessage(Component.literal("Speed Module: Enabled").withStyle(ChatFormatting.GREEN), true);
                    level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.NOTE_BLOCK_BELL.value(), SoundSource.PLAYERS, 0.4f, 0.01f);
                } else {
                    player.displayClientMessage(Component.literal("Speed Module: Disabled").withStyle(ChatFormatting.RED), true);
                    level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.NOTE_BLOCK_BELL.value(), SoundSource.PLAYERS, 0.4f, 0.09f);
                }
            }
        }

    }
}
