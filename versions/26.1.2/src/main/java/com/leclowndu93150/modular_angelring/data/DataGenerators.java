package com.leclowndu93150.modular_angelring.data;

import com.leclowndu93150.modular_angelring.AngelRingMain;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = AngelRingMain.MODID)
public final class DataGenerators {
    private DataGenerators() {
    }

    @SubscribeEvent
    public static void gatherClientData(GatherDataEvent.Client event) {
        event.createProvider(ModItemModelProvider::new);
        event.createProvider(WornWingsItemProvider::new);
        event.createProvider(ModLanguageProvider::new);
        addServerProviders(event);
    }

    @SubscribeEvent
    public static void gatherServerData(GatherDataEvent.Server event) {
        addServerProviders(event);
    }

    private static void addServerProviders(GatherDataEvent event) {
        event.createProvider(ModRecipeProvider.Runner::new);
        event.createProvider(ModCuriosProvider::new);
    }
}
