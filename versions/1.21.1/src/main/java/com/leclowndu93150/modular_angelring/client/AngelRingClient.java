package com.leclowndu93150.modular_angelring.client;

import com.leclowndu93150.modular_angelring.AngelRingMain;
import com.leclowndu93150.modular_angelring.api.WingStyle;
import com.leclowndu93150.modular_angelring.registry.ItemRegistry;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

@EventBusSubscriber(modid = AngelRingMain.MODID, value = Dist.CLIENT)
public final class AngelRingClient {
    private AngelRingClient() {
    }

    public static ModelResourceLocation wornModel(WingStyle style) {
        return ModelResourceLocation.standalone(style.getWornModel());
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> ItemProperties.register(ItemRegistry.ANGEL_RING.get(), WingStyle.MODEL_PROPERTY,
                (stack, level, entity, seed) -> WingStyle.of(stack).ordinal()));
    }

    @SubscribeEvent
    public static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
        ModuleKeyMappings.register(event);
    }

    @SubscribeEvent
    public static void onRegisterAdditionalModels(ModelEvent.RegisterAdditional event) {
        for (WingStyle style : WingStyle.values()) {
            event.register(wornModel(style));
        }
    }

    @SubscribeEvent
    public static void onAddLayers(EntityRenderersEvent.AddLayers event) {
        for (PlayerSkin.Model skin : event.getSkins()) {
            if (event.getSkin(skin) instanceof PlayerRenderer renderer) {
                renderer.addLayer(new WingsLayer(renderer));
            }
        }
    }
}
