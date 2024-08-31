package com.leclowndu93150.modular_angelring.events;

import com.leclowndu93150.modular_angelring.AngelRingMain;
import com.leclowndu93150.modular_angelring.common.AngelRingModules;
import com.leclowndu93150.modular_angelring.common.EnabledModifiersComponent;
import com.leclowndu93150.modular_angelring.networking.KeyPressedPayload;
import com.leclowndu93150.modular_angelring.registry.DataComponentRegistry;
import com.leclowndu93150.modular_angelring.registry.ItemRegistry;
import com.leclowndu93150.modular_angelring.registry.KeyBindRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.network.PacketDistributor;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import java.util.Optional;

import static com.leclowndu93150.modular_angelring.common.AngelRingModules.getNightVisionModifier;

@EventBusSubscriber(modid = AngelRingMain.MODID, value = Dist.CLIENT)
public class AngelRingClientEvents {

    static boolean isNightVisionEnabled = false;

    @SubscribeEvent
    public static void onKey(InputEvent.Key event) {
        Player player = Minecraft.getInstance().player;
        Minecraft minecraft = Minecraft.getInstance();
        if (player == null) return;
        Optional<SlotResult> slotResult = CuriosApi.getCuriosInventory(player).flatMap(handler -> handler.findFirstCurio(ItemRegistry.ANGEL_RING.get()));
        if (slotResult.isPresent()) {
            ItemStack angelRingStack = slotResult.get().stack();
            EnabledModifiersComponent data = angelRingStack.getOrDefault(DataComponentRegistry.MODIFIERS_ENABLED, EnabledModifiersComponent.EMPTY);
            Level level = player.level();
            if (KeyBindRegistry.INERTIA_MODULE.get().consumeClick() && AngelRingModules.getInertiaModifier(angelRingStack)) {
                PacketDistributor.sendToServer(new KeyPressedPayload(0));
                // Needs to be inverted, since the data component has not yet synced to the client
                if (!data.inertiaEnabled()) {
                    player.displayClientMessage(Component.literal("Inertia Module: Enabled").withStyle(ChatFormatting.GREEN), true);
                    level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.NOTE_BLOCK_BELL.value(), SoundSource.PLAYERS, 0.4f, 0.01f);
                } else {
                    player.displayClientMessage(Component.literal("Inertia Module: Disabled").withStyle(ChatFormatting.RED), true);
                    level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.NOTE_BLOCK_BELL.value(), SoundSource.PLAYERS, 0.4f, 0.09f);
                }

            }

            if (KeyBindRegistry.SPEED_MODULE.get().consumeClick() && angelRingStack.has(DataComponentRegistry.SPEED_MODIFIER)) {
                PacketDistributor.sendToServer(new KeyPressedPayload(1));
                // Needs to be inverted, since the data component has not yet synced to the client
                if (!data.speedModifierEnabled()) {
                    player.displayClientMessage(Component.literal("Speed Module: Enabled").withStyle(ChatFormatting.GREEN), true);
                    level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.NOTE_BLOCK_BELL.value(), SoundSource.PLAYERS, 0.4f, 0.01f);
                } else {
                    player.displayClientMessage(Component.literal("Speed Module: Disabled").withStyle(ChatFormatting.RED), true);
                    level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.NOTE_BLOCK_BELL.value(), SoundSource.PLAYERS, 0.4f, 0.09f);
                }
            }

            if (KeyBindRegistry.NIGHT_VISION_MODULE.get().consumeClick() && angelRingStack.has(DataComponentRegistry.NIGHT_VISION_MODIFIER)) {
                PacketDistributor.sendToServer(new KeyPressedPayload(3));
                if (!data.nightVisionEnabled()) {
                    player.displayClientMessage(Component.literal("Night Vision Module: Enabled").withStyle(ChatFormatting.GREEN), true);
                    level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.NOTE_BLOCK_BELL.value(), SoundSource.PLAYERS, 0.4f, 0.01f);
                } else{
                    player.displayClientMessage(Component.literal("Night Vision Module: Disabled").withStyle(ChatFormatting.RED), true);
                    level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.NOTE_BLOCK_BELL.value(), SoundSource.PLAYERS, 0.4f, 0.09f);
                }
            }
        }

    }


    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Pre event){
        double initialGamma = 1.0;
        double maxGamma = 9999.0;
        Player player = Minecraft.getInstance().player;
        if (player == null || ModList.get().isLoaded("fullbrightnesstoggle")) return;
        Optional<SlotResult> slotResult = CuriosApi.getCuriosInventory(player).flatMap(handler -> handler.findFirstCurio(ItemRegistry.ANGEL_RING.get()));
        if (slotResult.isPresent()) {
            ItemStack angelRingStack = slotResult.get().stack();
            EnabledModifiersComponent data = angelRingStack.getOrDefault(DataComponentRegistry.MODIFIERS_ENABLED, EnabledModifiersComponent.EMPTY);
            if(getNightVisionModifier(angelRingStack) && data.nightVisionEnabled()){
                    if (Minecraft.getInstance().options.gamma().get() != maxGamma) {
                        Minecraft.getInstance().options.gamma().set(maxGamma);
                    }
            }
            if (!data.nightVisionEnabled() && Minecraft.getInstance().options.gamma().get() != initialGamma) {
                Minecraft.getInstance().options.gamma().set(initialGamma);
            }
        }

        if(slotResult.isEmpty() && Minecraft.getInstance().options.gamma().get() != initialGamma){
            Minecraft.getInstance().options.gamma().set(initialGamma);
        }
    }


}
