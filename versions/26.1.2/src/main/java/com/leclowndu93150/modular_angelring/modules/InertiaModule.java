package com.leclowndu93150.modular_angelring.modules;

import com.leclowndu93150.modular_angelring.AngelRingConfig;
import com.leclowndu93150.modular_angelring.api.RingModule;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.lwjgl.glfw.GLFW;

public class InertiaModule extends RingModule {
    public InertiaModule() {
        super(Properties.toggleable(GLFW.GLFW_KEY_L));
    }

    public void stopDrift(Player player) {
        if (!player.getAbilities().flying || !isActive(player)) return;
        double slowdown = AngelRingConfig.SLOWDOWN_FACTOR.get();
        Vec3 motion = player.getDeltaMovement();
        player.setDeltaMovement(motion.x * slowdown, motion.y, motion.z * slowdown);
    }
}
