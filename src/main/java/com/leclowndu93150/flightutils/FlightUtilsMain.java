package com.leclowndu93150.flightutils;

import com.leclowndu93150.flightutils.items.AngelRingItem;
import com.leclowndu93150.flightutils.items.InertiaRingItem;
import com.leclowndu93150.flightutils.registry.CreativeTabRegistry;
import com.leclowndu93150.flightutils.registry.ItemRegistry;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import top.theillusivec4.curios.api.CuriosApi;

@Mod(FlightUtilsMain.MODID)
public class FlightUtilsMain
{
    public static final String MODID = "flightutils";

    public FlightUtilsMain(IEventBus modEventBus)
    {
        CreativeTabRegistry.CREATIVE_MODE_TABS.register(modEventBus);
        ItemRegistry.ITEMS.register(modEventBus);
        modEventBus.addListener(AngelRingItem::registerCapabilities);
        //modEventBus.addListener(this::setup);
    }

    /*
      * private void setup(final FMLCommonSetupEvent evt) {
      * CuriosApi.registerCurio(ItemRegistry.ANGEL_RING.get(), new AngelRingItem(new Item.Properties()));
      * CuriosApi.registerCurio(ItemRegistry.INERTIA_RING.get(), new InertiaRingItem(new Item.Properties()));
      *CuriosApi.registerCurio(ItemRegistry.MINING_RING.get(), new AngelRingItem(new Item.Properties()));
     }
     */

}
