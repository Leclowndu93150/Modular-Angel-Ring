package com.leclowndu93150.modular_angelring.mixin;

import com.mojang.serialization.Codec;
import net.minecraft.client.OptionInstance;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@OnlyIn(Dist.CLIENT)
@Mixin(OptionInstance.class)
public class OptionInstanceMixin<T> {

    @Shadow
    @Mutable
    T value;

    @Shadow
    @Final
    public Component caption;

    /**
     * Mixin to allow saving "invalid" gamma values into the options file
     */
    @SuppressWarnings("unchecked")
    @Inject(method = "codec", at = @At("HEAD"), cancellable = true)
    private void returnFakeCodec(CallbackInfoReturnable<Codec<T>> info) {
        if (caption != null && caption.equals(Component.translatable("options.gamma"))) {
            // Cast is necessary to ensure the type matches
            info.setReturnValue((Codec<T>) Codec.DOUBLE);
        }
    }


    /**
     * Mixin to allow setting "invalid" gamma values
     */
    @Inject(method = "set", at = @At("HEAD"), cancellable = true)
    private void setRealValue(T value, CallbackInfo info) {
        if (caption != null && caption.equals(Component.translatable("options.gamma"))) {
            if (value instanceof Double) {
                double doubleValue = (Double) value;
                if (doubleValue <= 9999.0) {
                    this.value = value;
                    info.cancel();
                }
            }
        }
    }
}
