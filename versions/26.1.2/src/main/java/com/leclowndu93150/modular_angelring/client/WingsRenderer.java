package com.leclowndu93150.modular_angelring.client;

import com.leclowndu93150.modular_angelring.api.WingStyle;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.LightCoordsUtil;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.joml.Quaternionf;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class WingsRenderer implements ICurioRenderer {
    private static final double FLAPS_PER_SECOND = 0.5;
    private static final double MAX_FLAP_ANGLE = 25.0;

    @Override
    public <S extends LivingEntityRenderState, M extends EntityModel<? super S>> void render(
            ItemStack stack, SlotContext slotContext, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int packedLight,
            S renderState, RenderLayerParent<S, M> renderLayerParent, EntityRendererProvider.Context context, float yRotation, float xRotation) {
        if (renderState.isInvisible || !(renderLayerParent.getModel() instanceof HumanoidModel<?> model)) return;

        WingStyle style = WingStyle.of(stack);
        ItemStack displayStack = style.getWingsItem().getDefaultInstance();
        displayStack.set(DataComponents.ITEM_MODEL, style.getWornItemModel());
        ItemStackRenderState wings = new ItemStackRenderState();
        Minecraft.getInstance().getItemModelResolver().updateForLiving(wings, displayStack, ItemDisplayContext.NONE, slotContext.entity());
        double flapAngle = MAX_FLAP_ANGLE * Math.sin(2 * Math.PI * FLAPS_PER_SECOND * (System.currentTimeMillis() / 1000.0));

        submitWing(poseStack, submitNodeCollector, model, wings, renderState.outlineColor, -1, flapAngle);
        submitWing(poseStack, submitNodeCollector, model, wings, renderState.outlineColor, 1, flapAngle);
    }

    private void submitWing(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, HumanoidModel<?> model,
                            ItemStackRenderState wings, int outlineColor, int side, double flapAngle) {
        poseStack.pushPose();
        model.body.translateAndRotate(poseStack);
        poseStack.translate(side * -0.5, 0.1, 0.35);
        poseStack.scale(0.9F, 0.9F, 0.9F);
        poseStack.mulPose(new Quaternionf().rotateXYZ(0F, (float) (Math.PI / 2 - side * (Math.PI / 2 - Math.PI / 6)), (float) Math.PI));
        poseStack.translate(-0.5, 0, 0);
        poseStack.mulPose(Axis.YN.rotationDegrees((float) (side * flapAngle)));
        poseStack.translate(0.5, 0, 0);
        wings.submit(poseStack, submitNodeCollector, LightCoordsUtil.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, outlineColor);
        poseStack.popPose();
    }
}
