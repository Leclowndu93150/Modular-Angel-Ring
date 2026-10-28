package com.leclowndu93150.modular_angelring.modules;

import com.leclowndu93150.modular_angelring.api.RingModule;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class VoidGuardianModule extends RingModule {
    private static final double LIFT_SPEED = 0.5;

    public VoidGuardianModule() {
        super(Properties.passive());
    }

    @Override
    public void tick(Player player, ItemStack ring) {
        if (player.isSpectator() || player.getY() >= player.level().getMinY()) return;

        player.resetFallDistance();
        Vec3 motion = player.getDeltaMovement();
        player.setDeltaMovement(motion.x, Math.max(motion.y, LIFT_SPEED), motion.z);
        if (!player.getAbilities().flying) {
            player.getAbilities().flying = true;
            player.onUpdateAbilities();
        }
    }
}
