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
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;

import static com.leclowndu93150.modular_angelring.render.AngelRingCheck.isEquipped;

public abstract class AbstractAngelRingRenderer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {

    protected static final double FLAP_FREQUENCY = 0.5; // Degrees per flap
    protected static final double MAX_ANGLE = 25.0; // Maximum flap angle

    protected double angle; // Angle of rotation
    private final double dir; //-1 or 1, depending on wing side

    public AbstractAngelRingRenderer(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> playerModel, boolean isRightWing) {
        super(playerModel);
        this.angle = 0.;
        this.dir = isRightWing ? 1. : -1.;
    }

    @Override
    public void render(@NotNull PoseStack matrixStack, @NotNull MultiBufferSource buffer, int packedLight, AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        assert Minecraft.getInstance().player != null;

        if (Minecraft.getInstance().player.getSkin().capeTexture() == null
                && !player.isInvisible()
                && player.isModelPartShown(PlayerModelPart.CAPE)
                && isEquipped(player)) {
            matrixStack.pushPose();
            getParentModel().body.translateAndRotate(matrixStack);

            // used to be +-0.2 0 0.2
            matrixStack.translate(dir * -0.5, 0.2, 0.35);
            matrixStack.scale((float)dir * 0.9f, 0.9f, 0.9f);
            matrixStack.mulPose(new Quaternionf().rotateY((float) (Math.PI / 6)));
            matrixStack.scale(-1, -1, -1);

            matrixStack.translate(-0.5,0,0);
            if (player.getAbilities().flying) {
                updateWingAngle();
            }
            matrixStack.mulPose(Axis.YN.rotationDegrees((float) angle));
            matrixStack.translate(0.5,0,0);

            //ItemRegistry.ANGEL_WINGS_RIGHT in both cases?
            Minecraft.getInstance().getItemRenderer().renderStatic(player, new ItemStack(ItemRegistry.ANGEL_WINGS_RIGHT.get()), ItemDisplayContext.NONE, false, matrixStack, buffer, player.level(), 0xF000F0, OverlayTexture.NO_OVERLAY, player.getId());
            matrixStack.popPose();
        }
    }

    private void updateWingAngle() {
        angle = MAX_ANGLE * Math.sin(2*Math.PI * FLAP_FREQUENCY * (System.currentTimeMillis()/1000.));
    }
}
