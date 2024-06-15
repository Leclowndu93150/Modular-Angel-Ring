package com.leclowndu93150.modular_angelring.registry;

import com.leclowndu93150.modular_angelring.common.AngelRingItem;
import com.leclowndu93150.modular_angelring.common.AngelRingModules;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.util.Lazy;
import org.lwjgl.glfw.GLFW;

import static com.leclowndu93150.modular_angelring.AngelRingMain.MODID;

@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD)
public class KeyBindRegistry {
    public static final Lazy<KeyMapping> INERTIA_MODULE = Lazy.of(() ->new KeyMapping(
            "Inertia Module", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_L, "Modular Angel Ring"));


    @SubscribeEvent
    public static void registerBindings(RegisterKeyMappingsEvent event) {
        //event.register(MINING_MODULE.get());
        event.register(INERTIA_MODULE.get());
        NeoForge.EVENT_BUS.addListener(KeyBindRegistry::onKey);
    }

    public static boolean miningEnabled = true;
    public static boolean inertiaEnabled = true;

    public static void onKey(InputEvent.Key event) {
        Player player = Minecraft.getInstance().player;
        if (player == null) return;

        for (ItemStack item : player.getInventory().items) {
            if (item.getItem() instanceof AngelRingItem) {
                if (INERTIA_MODULE.get().consumeClick() && AngelRingModules.getInertiaModifier(item)) {
                    inertiaEnabled = !inertiaEnabled;
                    if (inertiaEnabled) {
                        player.displayClientMessage(Component.literal("Inertia Module: Enabled").withStyle(ChatFormatting.GREEN), true);
                        player.level().playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.NOTE_BLOCK_BELL.value(), SoundSource.PLAYERS, 0.4f, 0.01f);
                    } else {
                        player.displayClientMessage(Component.literal("Inertia Module: Disabled").withStyle(ChatFormatting.RED), true);
                        player.level().playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.NOTE_BLOCK_BELL.value(), SoundSource.PLAYERS, 0.4f, 0.09f);
                    }
                    break;
                }
            }
        }
    }
}
