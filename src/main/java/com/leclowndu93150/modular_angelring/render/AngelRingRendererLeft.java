package com.leclowndu93150.modular_angelring.render;

import com.leclowndu93150.modular_angelring.registry.ItemRegistry;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;

import static com.leclowndu93150.modular_angelring.render.AngelRingCheck.isEquipped;

@OnlyIn(Dist.CLIENT)
public class AngelRingRendererLeft extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
    private static final double FLAP_SPEED = 3.0; // Degrees per flap
    private static final double MAX_ANGLE = 25.0; // Maximum flap angle
    private double angle = 0.0; // Angle of rotation
    private boolean flappingUp = true; // Direction of flapping

    public AngelRingRendererLeft(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> playerModel) {
        super(playerModel);
    }

    @Override
    public void render(@NotNull PoseStack matrixStack, @NotNull MultiBufferSource buffer, int packedLight, AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (Minecraft.getInstance().player.getSkin().capeTexture() == null && !player.isInvisible() && player.isModelPartShown(PlayerModelPart.CAPE) && isEquipped(player)) {
            matrixStack.pushPose();
            getParentModel().body.translateAndRotate(matrixStack);
            matrixStack.translate(0.15, 0.2, 0.15);
            matrixStack.scale(-0.9f, 0.9f, 0.9f);
            matrixStack.mulPose(new Quaternionf().rotateY((float) (Math.PI / 6)));
            matrixStack.scale(-1, -1, -1);
            updateWingAngle();
            matrixStack.mulPose(Axis.YN.rotationDegrees((float) angle));
            Minecraft.getInstance().getItemRenderer().renderStatic(player, new ItemStack(ItemRegistry.ANGEL_WINGS_RIGHT.get()), ItemDisplayContext.NONE, false, matrixStack, buffer, player.level(), 0xF000F0, OverlayTexture.NO_OVERLAY, player.getId());
            matrixStack.popPose();
        }
    }

    private void updateWingAngle() {
        if (flappingUp) {
            angle += FLAP_SPEED;
            if (angle >= MAX_ANGLE) {
                angle = MAX_ANGLE;
                flappingUp = false;
            }
        } else {
            angle -= FLAP_SPEED;
            if (angle <= -MAX_ANGLE) {
                angle = -MAX_ANGLE;
                flappingUp = true;
            }
        }
    }
}