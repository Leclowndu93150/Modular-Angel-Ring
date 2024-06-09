package com.leclowndu93150.flightutils;

import com.leclowndu93150.flightutils.common.AngelRingItem;
import com.leclowndu93150.flightutils.registry.CreativeTabRegistry;
import com.leclowndu93150.flightutils.registry.DataComponentRegistry;
import com.leclowndu93150.flightutils.registry.ItemRegistry;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(FlightUtilsMain.MODID)
public class FlightUtilsMain {
    public static final String MODID = "flightutils";



    public FlightUtilsMain(IEventBus modEventBus) {
        CreativeTabRegistry.CREATIVE_MODE_TABS.register(modEventBus);
        ItemRegistry.ITEMS.register(modEventBus);
        DataComponentRegistry.COMPONENTS.register(modEventBus);
        modEventBus.addListener(AngelRingItem::registerCapabilities);
    }

}
