package com.leclowndu93150.modular_angelring.networking;

import com.leclowndu93150.modular_angelring.AngelRingMain;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = AngelRingMain.MODID, bus = EventBusSubscriber.Bus.MOD)
public final class NetworkingEvents {
    @SubscribeEvent
    public static void registerPayloads(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(AngelRingMain.MODID);
        registrar.playToServer(KeyPressedPayload.TYPE, KeyPressedPayload.STREAM_CODEC, PayloadActions::keyPressedAction);
        registrar.playBidirectional(NoKeyPressedPayload.TYPE, NoKeyPressedPayload.STREAM_CODEC, PayloadActions::noKeyPressedAction);
    }
}
