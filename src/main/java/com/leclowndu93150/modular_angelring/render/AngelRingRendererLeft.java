package com.leclowndu93150.modular_angelring.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;

import static com.leclowndu93150.modular_angelring.render.AngelRingCheck.isBaseEquipped;
import static com.leclowndu93150.modular_angelring.render.AngelRingCheck.isEquipped;

@OnlyIn(Dist.CLIENT)
public class AngelRingRendererLeft extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {

    public AngelRingRendererLeft(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> playerModel) {
        super(playerModel);
    }

    @Override
    public void render(@NotNull PoseStack matrixStack, @NotNull MultiBufferSource buffer, int packedLight, AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        Item item = ItemStack.EMPTY.getItem();
        if (player.isCapeLoaded() && !player.isInvisible() && player.isModelPartShown(PlayerModelPart.CAPE) && isEquipped(player)) {
            if (isBaseEquipped(player)) {item = net.minecraft.world.item.ItemStack.EMPTY.getItem();}
            matrixStack.pushPose();
            getParentModel().body.translateAndRotate(matrixStack);
            matrixStack.translate(0.4, 0.2, 0.4);
            matrixStack.scale(-0.9f, 0.9f, 0.9f);
            matrixStack.mulPose(new Quaternionf().rotateY((float) (Math.PI / 6)));
            matrixStack.scale(-1, -1, -1);
            Minecraft.getInstance().getItemRenderer().renderStatic(player, new ItemStack(item), ItemDisplayContext.NONE, false, matrixStack, buffer, player.level(), 0xF000F0, OverlayTexture.NO_OVERLAY, player.getId());
            matrixStack.popPose();
        }
    }
}