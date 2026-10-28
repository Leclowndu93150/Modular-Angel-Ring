package com.leclowndu93150.modular_angelring.mixin.client;

import com.leclowndu93150.modular_angelring.registry.ModuleRegistry;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.LightmapRenderStateExtractor;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LightmapRenderStateExtractor.class)
public abstract class LightmapRenderStateExtractorMixin {
    @WrapOperation(method = "extract", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;hasEffect(Lnet/minecraft/core/Holder;)Z"))
    private boolean modularAngelring$ringNightVision(LocalPlayer player, Holder<MobEffect> effect, Operation<Boolean> original) {
        return original.call(player, effect) || ModuleRegistry.NIGHT_VISION.get().grants(player, effect);
    }
}
