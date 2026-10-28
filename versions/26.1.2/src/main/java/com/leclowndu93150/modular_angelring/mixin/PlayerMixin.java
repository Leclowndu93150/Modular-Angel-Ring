package com.leclowndu93150.modular_angelring.mixin;

import com.leclowndu93150.modular_angelring.registry.ModuleRegistry;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Player.class)
public abstract class PlayerMixin {
    @ModifyExpressionValue(method = "getFlyingSpeed", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Abilities;getFlyingSpeed()F"))
    private float modularAngelring$applyRingFlightSpeed(float abilitySpeed) {
        return ModuleRegistry.SPEED.get().getFlyingSpeed((Player) (Object) this, abilitySpeed);
    }
}
