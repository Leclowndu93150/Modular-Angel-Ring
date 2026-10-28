package com.leclowndu93150.modular_angelring.networking;

import com.leclowndu93150.modular_angelring.AngelRingMain;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

@EventBusSubscriber(modid = AngelRingMain.MODID)
public final class AngelRingNetwork {
    private static final String PROTOCOL_VERSION = "2";

    private AngelRingNetwork() {
    }

    @SubscribeEvent
    public static void registerPayloads(RegisterPayloadHandlersEvent event) {
        event.registrar(PROTOCOL_VERSION)
                .playToServer(ToggleModulePayload.TYPE, ToggleModulePayload.STREAM_CODEC, ToggleModulePayload::handle);
    }
}
