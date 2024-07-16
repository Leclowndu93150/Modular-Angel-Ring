package com.leclowndu93150.modular_angelring.networking;

import com.leclowndu93150.modular_angelring.AngelRingMain;
import com.leclowndu93150.modular_angelring.registry.KeyBindRegistry;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public final class PayloadActions {
    public static void keyPressedAction(KeyPressedPayload payload, IPayloadContext ctx) {
        // SERVER-SIDE
        Player player = ctx.player();
        Level level = player.level();
        int key = payload.key();
        if (key == KeyBindRegistry.INERTIA_MODULE.get().getKey().getValue()) {
            // do stuff if inertia module key was pressed
            System.out.println("Inertia pressed");
        }
    }
}
