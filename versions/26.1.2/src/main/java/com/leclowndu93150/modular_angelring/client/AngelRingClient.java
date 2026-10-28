package com.leclowndu93150.modular_angelring.client;

import com.leclowndu93150.modular_angelring.AngelRingMain;
import com.leclowndu93150.modular_angelring.registry.ItemRegistry;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import top.theillusivec4.curios.api.client.ICurioRenderer;

@EventBusSubscriber(modid = AngelRingMain.MODID, value = Dist.CLIENT)
public final class AngelRingClient {
    private AngelRingClient() {
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        ICurioRenderer.register(ItemRegistry.ANGEL_RING.get(), WingsRenderer::new);
    }

    @SubscribeEvent
    public static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
        ModuleKeyMappings.register(event);
    }
}
