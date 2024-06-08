package com.leclowndu93150.flightutils;

import com.leclowndu93150.flightutils.curio.FlightCapability;
import com.leclowndu93150.flightutils.registry.CreativeTabRegistry;
import com.leclowndu93150.flightutils.registry.ItemRegistry;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(FlightUtilsMain.MODID)
public class FlightUtilsMain
{
    public static final String MODID = "flightutils";

    public FlightUtilsMain(IEventBus modEventBus)
    {
        CreativeTabRegistry.CREATIVE_MODE_TABS.register(modEventBus);
        ItemRegistry.ITEMS.register(modEventBus);
        modEventBus.addListener(new FlightCapability()::registerCapabilities);
    }
}
