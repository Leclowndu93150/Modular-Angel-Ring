package com.leclowndu93150.flightutils;

import com.leclowndu93150.flightutils.registry.CreativeTabRegistry;
import com.leclowndu93150.flightutils.registry.ItemRegistry;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(FlightUtilsMain.MODID)
public class FlightUtilsMain
{
    public static final String MODID = "flightutils";

    public FlightUtilsMain(IEventBus modEventBus)
    {
        CreativeTabRegistry.CREATIVE_MODE_TABS.register(modEventBus);
        ItemRegistry.ITEMS.register(modEventBus);
    }
}
