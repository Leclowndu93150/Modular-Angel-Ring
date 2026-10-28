package com.leclowndu93150.modular_angelring.modules;

import com.leclowndu93150.modular_angelring.AngelRingConfig;
import com.leclowndu93150.modular_angelring.api.RingModule;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import org.lwjgl.glfw.GLFW;

public class FastBuildModule extends RingModule {
    public FastBuildModule() {
        super(Properties.toggleable(GLFW.GLFW_KEY_UNKNOWN));
    }

    public int getPlaceDelay(Player player, int vanillaDelay) {
        boolean holdingBlock = player.getMainHandItem().getItem() instanceof BlockItem || player.getOffhandItem().getItem() instanceof BlockItem;
        return holdingBlock && isActive(player) ? AngelRingConfig.FAST_BUILD_DELAY.get() : vanillaDelay;
    }
}
