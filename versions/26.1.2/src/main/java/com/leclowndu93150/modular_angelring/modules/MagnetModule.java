package com.leclowndu93150.modular_angelring.modules;

import com.leclowndu93150.modular_angelring.AngelRingConfig;
import com.leclowndu93150.modular_angelring.api.RingModule;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.lwjgl.glfw.GLFW;

import java.util.List;

public class MagnetModule extends RingModule {
    public MagnetModule() {
        super(Properties.toggleable(GLFW.GLFW_KEY_P));
    }

    @Override
    public void tick(Player player, ItemStack ring) {
        if (player.isCrouching()) return;

        double pullSpeed = AngelRingConfig.MAGNET_PULL_SPEED.get();
        Vec3 target = player.position();
        AABB area = player.getBoundingBox().inflate(AngelRingConfig.MAGNET_RADIUS.get());
        for (Entity entity : player.level().getEntities(player, area, entity -> entity instanceof ItemEntity || entity instanceof ExperienceOrb)) {
            Vec3 pull = target.subtract(entity.position()).normalize().scale(pullSpeed);
            entity.setDeltaMovement(entity.getDeltaMovement().add(pull));
        }
    }

    @Override
    public void appendRingTooltip(ItemStack ring, List<Component> tooltip) {
        if (!isEnabled(ring)) {
            super.appendRingTooltip(ring, tooltip);
            return;
        }
        int radius = AngelRingConfig.MAGNET_RADIUS.get().intValue();
        tooltip.add(Component.translatable("text.modular_angelring.magnet_radius", getStateText(true), radius).withStyle(ChatFormatting.GREEN));
    }
}
