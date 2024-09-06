package com.leclowndu93150.modular_angelring;

import com.leclowndu93150.modular_angelring.common.AngelRingItem;
import com.leclowndu93150.modular_angelring.registry.CreativeTabRegistry;
import com.leclowndu93150.modular_angelring.registry.DataComponentRegistry;
import com.leclowndu93150.modular_angelring.registry.ItemRegistry;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(AngelRingMain.MODID)
public class AngelRingMain {
    public static final String MODID = "modular_angelring";

    public AngelRingMain(IEventBus modEventBus) {
        CreativeTabRegistry.CREATIVE_MODE_TABS.register(modEventBus);
        ItemRegistry.ITEMS.register(modEventBus);
        DataComponentRegistry.COMPONENTS.register(modEventBus);
        modEventBus.addListener(AngelRingItem::registerCapabilities);
    }
    
}
