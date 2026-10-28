package com.leclowndu93150.modular_angelring.client;

import com.leclowndu93150.modular_angelring.api.AngelRing;
import com.leclowndu93150.modular_angelring.api.WingStyle;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.joml.Quaternionf;

public class WingsLayer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
    private static final double FLAPS_PER_SECOND = 0.5;
    private static final double MAX_FLAP_ANGLE = 25.0;

    public WingsLayer(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> parent) {
        super(parent);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, AbstractClientPlayer player,
                       float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        if (player.isInvisible()) return;

        AngelRing.findSlot(player)
                .filter(slot -> slot.slotContext().visible())
                .ifPresent(slot -> renderWings(poseStack, buffer, WingStyle.of(slot.stack())));
    }

    private void renderWings(PoseStack poseStack, MultiBufferSource buffer, WingStyle style) {
        Minecraft minecraft = Minecraft.getInstance();
        BakedModel model = minecraft.getModelManager().getModel(AngelRingClient.wornModel(style));
        ItemStack displayStack = style.getWingsItem().getDefaultInstance();
        double flapAngle = MAX_FLAP_ANGLE * Math.sin(2 * Math.PI * FLAPS_PER_SECOND * (System.currentTimeMillis() / 1000.0));

        renderWing(poseStack, buffer, minecraft.getItemRenderer(), model, displayStack, -1, flapAngle);
        renderWing(poseStack, buffer, minecraft.getItemRenderer(), model, displayStack, 1, flapAngle);
    }

    private void renderWing(PoseStack poseStack, MultiBufferSource buffer, ItemRenderer itemRenderer, BakedModel model,
                            ItemStack displayStack, int side, double flapAngle) {
        poseStack.pushPose();
        getParentModel().body.translateAndRotate(poseStack);
        poseStack.translate(side * -0.5, 0.1, 0.35);
        poseStack.scale(0.9F, 0.9F, 0.9F);
        poseStack.mulPose(new Quaternionf().rotateXYZ(0F, (float) (Math.PI / 2 - side * (Math.PI / 2 - Math.PI / 6)), (float) Math.PI));
        poseStack.translate(-0.5, 0, 0);
        poseStack.mulPose(Axis.YN.rotationDegrees((float) (side * flapAngle)));
        poseStack.translate(0.5, 0, 0);
        itemRenderer.render(displayStack, ItemDisplayContext.NONE, false, poseStack, buffer, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, model);
        poseStack.popPose();
    }
}
