package com.leclowndu93150.modular_angelring.modules;

import com.leclowndu93150.modular_angelring.AngelRingConfig;
import com.leclowndu93150.modular_angelring.api.AngelRing;
import com.leclowndu93150.modular_angelring.api.RingModule;
import com.leclowndu93150.modular_angelring.registry.DataComponentRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class SpeedModule extends RingModule {
    public static final float DEFAULT_SPEED = 0.05F;
    private static final float SPEED_STEP = 0.005F;

    public SpeedModule() {
        super(Properties.toggleable(GLFW.GLFW_KEY_K));
    }

    public static int toPercentage(float speed) {
        return Math.round(speed / DEFAULT_SPEED * 100);
    }

    private static float getMaxSpeed() {
        return AngelRingConfig.MAX_FLIGHT_SPEED.get().floatValue();
    }

    public float getSpeed(ItemStack ring) {
        return ring.getOrDefault(DataComponentRegistry.FLIGHT_SPEED, DEFAULT_SPEED);
    }

    public float getFlyingSpeed(Player player, float abilitySpeed) {
        ItemStack ring = AngelRing.find(player);
        return isEnabled(ring) ? getSpeed(ring) : abilitySpeed;
    }

    @Override
    protected void onInstall(ItemStack ring) {
        ring.set(DataComponentRegistry.FLIGHT_SPEED, DEFAULT_SPEED);
    }

    @Override
    public boolean onRingUse(Level level, Player player, ItemStack ring) {
        if (level.isClientSide()) return true;

        float speed = getSpeed(ring);
        if (player.isShiftKeyDown()) {
            speed = Math.max(speed - SPEED_STEP, 0F);
        } else if (speed < getMaxSpeed()) {
            speed = Math.min(speed + SPEED_STEP, getMaxSpeed());
        }
        ring.set(DataComponentRegistry.FLIGHT_SPEED, speed);

        player.sendOverlayMessage(Component.translatable("text.modular_angelring.speed_changed", toPercentage(speed)).withStyle(ChatFormatting.WHITE));
        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.NOTE_BLOCK_BELL, SoundSource.PLAYERS, 0.4F, 0.01F);
        return true;
    }

    @Override
    public void appendRingTooltip(ItemStack ring, List<Component> tooltip) {
        tooltip.add(Component.translatable("text.modular_angelring.speed", getName(), toPercentage(getSpeed(ring)))
                .withStyle(isEnabled(ring) ? ChatFormatting.GREEN : ChatFormatting.RED));
    }

    @Override
    public List<Component> getItemTooltip(boolean detailed) {
        List<Component> tooltip = new ArrayList<>();
        tooltip.add(Component.translatable(getDescriptionId() + ".desc", toPercentage(getMaxSpeed())).withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable(getDescriptionId() + ".more_info").withStyle(ChatFormatting.GRAY));
        if (detailed) {
            tooltip.add(Component.translatable(getDescriptionId() + ".increase").withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.translatable(getDescriptionId() + ".decrease").withStyle(ChatFormatting.GRAY));
        }
        return tooltip;
    }
}
