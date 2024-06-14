package com.leclowndu93150.modular_angelring.render;

import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AngelRingRendererRight extends AbstractAngelRingRenderer {
    public AngelRingRendererRight(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> playerModel) {
        super(playerModel, true);
    }
}