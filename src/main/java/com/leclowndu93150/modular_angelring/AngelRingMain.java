package com.leclowndu93150.modular_angelring;

import com.leclowndu93150.modular_angelring.common.AngelRingItem;
import com.leclowndu93150.modular_angelring.registry.AttachementRegistry;
import com.leclowndu93150.modular_angelring.registry.CreativeTabRegistry;
import com.leclowndu93150.modular_angelring.registry.DataComponentRegistry;
import com.leclowndu93150.modular_angelring.registry.ItemRegistry;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;

@Mod(AngelRingMain.MODID)
public class AngelRingMain {
    public static final String MODID = "modular_angelring";

    public AngelRingMain(IEventBus modEventBus, ModContainer modContainer) {
        CreativeTabRegistry.CREATIVE_MODE_TABS.register(modEventBus);
        ItemRegistry.ITEMS.register(modEventBus);
        DataComponentRegistry.COMPONENTS.register(modEventBus);
        AttachementRegistry.ATTACHMENT_TYPES.register(modEventBus);
        modEventBus.addListener(AngelRingItem::registerCapabilities);
        modContainer.registerConfig(ModConfig.Type.COMMON, AngelRingConfig.SPEC);
    }
    
}
