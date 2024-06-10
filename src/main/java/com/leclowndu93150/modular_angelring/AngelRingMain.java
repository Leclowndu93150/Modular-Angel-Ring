package com.leclowndu93150.modular_angelring;

import com.leclowndu93150.modular_angelring.common.AngelRingItem;
import com.leclowndu93150.modular_angelring.registry.CreativeTabRegistry;
import com.leclowndu93150.modular_angelring.registry.DataComponentRegistry;
import com.leclowndu93150.modular_angelring.registry.ItemRegistry;
import com.leclowndu93150.modular_angelring.render.AngelRingRendererLeft;
import com.leclowndu93150.modular_angelring.render.AngelRingRendererRight;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.common.NeoForge;

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
