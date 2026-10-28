package com.leclowndu93150.modular_angelring.modules;

import com.leclowndu93150.modular_angelring.AngelRingMain;
import com.leclowndu93150.modular_angelring.api.RingModule;
import com.leclowndu93150.modular_angelring.registry.ModuleRegistry;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@EventBusSubscriber(modid = AngelRingMain.MODID)
public class MiningModule extends RingModule {
    private static final float AIRBORNE_MULTIPLIER = 5F;

    public MiningModule() {
        super(Properties.passive());
    }

    @SubscribeEvent
    public static void onBreakSpeed(PlayerEvent.BreakSpeed event) {
        Player player = event.getEntity();
        if (!player.onGround() && ModuleRegistry.MINING.get().isActive(player)) {
            event.setNewSpeed(event.getNewSpeed() * AIRBORNE_MULTIPLIER);
        }
    }
}
