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
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;

import static com.leclowndu93150.modular_angelring.render.AngelRingCheck.*;

public class AngelRingRenderer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
    protected static final double FLAP_FREQUENCY = 0.5; // flaps per second
    protected static final double MAX_ANGLE = 25.0; // Maximum flap angle

    protected double angle; // Angle of rotation
    private final double dir; //-1 or 1, depending on wing side

    public AngelRingRenderer(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> playerModel, boolean isRightWing) {
        super(playerModel);
        this.angle = 0.;
        this.dir = isRightWing ? 1. : -1.;
    }

    @Override
    public void render(@NotNull PoseStack matrixStack, @NotNull MultiBufferSource buffer, int packedLight, @NotNull AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        assert Minecraft.getInstance().player != null;
        if (!player.isInvisible() && isEquipped(player) && isVisible(player)) {
            matrixStack.pushPose();
            getParentModel().body.translateAndRotate(matrixStack);

            // used to be +-0.2 0 0.2
            matrixStack.translate(dir * -0.5, 0.1, 0.35);
            matrixStack.scale(0.9f, 0.9f, 0.9f);

            matrixStack.mulPose(new Quaternionf().rotateXYZ(
                    .0f,
                    (float) ((Math.PI/2.f) - dir * (Math.PI/2.f - Math.PI /6.f)),
                    (float) Math.PI
            ));

            matrixStack.translate(-0.5,0,0);
            updateWingAngle();

            matrixStack.mulPose(Axis.YN.rotationDegrees((float) angle));
            matrixStack.translate(0.5,0,0);


            switch(getWingType(player)) {
                case "BAT":
                    Minecraft.getInstance().getItemRenderer().renderStatic(player, new ItemStack(ItemRegistry.BAT_WINGS_BOTH.get()), ItemDisplayContext.NONE, false, matrixStack, buffer, player.level(), 0xF000F0, OverlayTexture.NO_OVERLAY, player.getId());
                    break;
                case "BUTTERFLY":
                    Minecraft.getInstance().getItemRenderer().renderStatic(player, new ItemStack(ItemRegistry.BUTTERFLY_WINGS_BOTH.get()), ItemDisplayContext.NONE, false, matrixStack, buffer, player.level(), 0xF000F0, OverlayTexture.NO_OVERLAY, player.getId());
                    break;
                case "DRAGON":
                    Minecraft.getInstance().getItemRenderer().renderStatic(player, new ItemStack(ItemRegistry.DRAGON_WINGS_BOTH.get()), ItemDisplayContext.NONE, false, matrixStack, buffer, player.level(), 0xF000F0, OverlayTexture.NO_OVERLAY, player.getId());
                    break;
                case "BIG_DRAGON":
                    Minecraft.getInstance().getItemRenderer().renderStatic(player, new ItemStack(ItemRegistry.BIG_DRAGON_WINGS_BOTH.get()), ItemDisplayContext.NONE, false, matrixStack, buffer, player.level(), 0xF000F0, OverlayTexture.NO_OVERLAY, player.getId());
                    break;
                case "BLUE_DRAGON":
                    Minecraft.getInstance().getItemRenderer().renderStatic(player, new ItemStack(ItemRegistry.BLUE_DRAGON_WINGS_BOTH.get()), ItemDisplayContext.NONE, false, matrixStack, buffer, player.level(), 0xF000F0, OverlayTexture.NO_OVERLAY, player.getId());
                    break;
                case "GOLD":
                    Minecraft.getInstance().getItemRenderer().renderStatic(player, new ItemStack(ItemRegistry.GOLD_WINGS_BOTH.get()), ItemDisplayContext.NONE, false, matrixStack, buffer, player.level(), 0xF000F0, OverlayTexture.NO_OVERLAY, player.getId());
                    break;
                case "GOLDEN":
                    Minecraft.getInstance().getItemRenderer().renderStatic(player, new ItemStack(ItemRegistry.GOLDEN_WINGS_BOTH.get()), ItemDisplayContext.NONE, false, matrixStack, buffer, player.level(), 0xF000F0, OverlayTexture.NO_OVERLAY, player.getId());
                    break;
                default:
                    Minecraft.getInstance().getItemRenderer().renderStatic(player, new ItemStack(ItemRegistry.ANGEL_WINGS_BOTH.get()), ItemDisplayContext.NONE, false, matrixStack, buffer, player.level(), 0xF000F0, OverlayTexture.NO_OVERLAY, player.getId());
                    break;
            }

            matrixStack.popPose();
        }
    }

    private void updateWingAngle() {
        this.angle = dir * MAX_ANGLE * Math.sin(2*Math.PI * FLAP_FREQUENCY * (System.currentTimeMillis()/1000.));
    }
}
