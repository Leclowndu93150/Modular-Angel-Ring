package com.leclowndu93150.modular_angelring.client;

import com.leclowndu93150.modular_angelring.AngelRingMain;
import com.leclowndu93150.modular_angelring.api.AngelRing;
import com.leclowndu93150.modular_angelring.api.InstalledModules;
import com.leclowndu93150.modular_angelring.api.RingModule;
import com.leclowndu93150.modular_angelring.networking.ToggleModulePayload;
import com.leclowndu93150.modular_angelring.registry.ModuleRegistry;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

import java.util.LinkedHashMap;
import java.util.Map;

@EventBusSubscriber(modid = AngelRingMain.MODID, value = Dist.CLIENT)
public final class ModuleKeyMappings {
    private static final KeyMapping.Category CATEGORY = new KeyMapping.Category(AngelRingMain.id("modules"));
    private static final Map<RingModule, KeyMapping> MAPPINGS = new LinkedHashMap<>();

    private ModuleKeyMappings() {
    }

    static void register(RegisterKeyMappingsEvent event) {
        event.registerCategory(CATEGORY);
        ModuleRegistry.REGISTRY.stream().filter(RingModule::isToggleable).forEach(module -> {
            KeyMapping mapping = new KeyMapping(module.getId().toLanguageKey("key"), module.getDefaultKey(), CATEGORY);
            MAPPINGS.put(module, mapping);
            event.register(mapping);
        });
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        Player player = Minecraft.getInstance().player;
        if (player == null) return;

        InstalledModules modules = InstalledModules.of(AngelRing.find(player));
        MAPPINGS.forEach((module, mapping) -> {
            while (mapping.consumeClick()) {
                if (modules.has(module)) {
                    ClientPacketDistributor.sendToServer(new ToggleModulePayload(module));
                }
            }
        });
    }
}
