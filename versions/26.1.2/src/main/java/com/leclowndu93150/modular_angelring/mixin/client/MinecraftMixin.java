package com.leclowndu93150.modular_angelring.mixin.client;

import com.leclowndu93150.modular_angelring.registry.ModuleRegistry;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
    @ModifyExpressionValue(method = "startUseItem", at = @At(value = "CONSTANT", args = "intValue=4"))
    private int modularAngelring$fastBuildDelay(int delay) {
        return ModuleRegistry.FAST_BUILD.get().getPlaceDelay(((Minecraft) (Object) this).player, delay);
    }
}
