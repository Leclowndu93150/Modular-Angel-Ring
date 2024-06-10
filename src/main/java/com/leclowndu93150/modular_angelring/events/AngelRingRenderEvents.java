package com.leclowndu93150.modular_angelring.events;

import com.leclowndu93150.modular_angelring.AngelRingMain;
import com.leclowndu93150.modular_angelring.render.AngelRingRendererLeft;
import com.leclowndu93150.modular_angelring.render.AngelRingRendererRight;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = AngelRingMain.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class AngelRingRenderEvents {

    @SubscribeEvent
    public static void registerEntityLayers(EntityRenderersEvent.AddLayers event) {
        EntityRenderer<? extends Player> def = event.getSkin(PlayerSkin.Model.WIDE);
        EntityRenderer<? extends Player> slim = event.getSkin(PlayerSkin.Model.SLIM);

        if (def instanceof PlayerRenderer playerRendererA) {
            playerRendererA.addLayer(new AngelRingRendererLeft(playerRendererA));
            playerRendererA.addLayer(new AngelRingRendererRight(playerRendererA));
        }
        if (slim instanceof PlayerRenderer playerRendererB) {
            playerRendererB.addLayer(new AngelRingRendererLeft(playerRendererB));
            playerRendererB.addLayer(new AngelRingRendererRight(playerRendererB));
        }
    }
}
