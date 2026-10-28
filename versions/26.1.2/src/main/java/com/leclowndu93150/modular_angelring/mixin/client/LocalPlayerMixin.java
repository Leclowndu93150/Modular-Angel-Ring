package com.leclowndu93150.modular_angelring.mixin.client;

import com.leclowndu93150.modular_angelring.registry.ModuleRegistry;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LocalPlayer.class)
public abstract class LocalPlayerMixin {
    @ModifyExpressionValue(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Abilities;getFlyingSpeed()F"))
    private float modularAngelring$applyRingVerticalFlightSpeed(float abilitySpeed) {
        return ModuleRegistry.SPEED.get().getFlyingSpeed((LocalPlayer) (Object) this, abilitySpeed);
    }
}
