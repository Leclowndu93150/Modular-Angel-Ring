package com.leclowndu93150.modular_angelring.mixin.client;

import com.leclowndu93150.modular_angelring.registry.ModuleRegistry;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    @Inject(method = "getNightVisionScale", at = @At("HEAD"), cancellable = true)
    private static void modularAngelring$ringNightVisionScale(LivingEntity entity, float partialTick, CallbackInfoReturnable<Float> cir) {
        if (ModuleRegistry.NIGHT_VISION.get().isActive(entity)) {
            cir.setReturnValue(1.0F);
        }
    }
}
