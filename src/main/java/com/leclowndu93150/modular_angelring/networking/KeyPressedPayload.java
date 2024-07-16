package com.leclowndu93150.modular_angelring.networking;

import com.leclowndu93150.modular_angelring.AngelRingMain;
import com.leclowndu93150.modular_angelring.render.AngelRingRenderer;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record KeyPressedPayload(int key) implements CustomPacketPayload {
    public static final Type<KeyPressedPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(AngelRingMain.MODID, "keypressed_payload"));
    public static final StreamCodec<RegistryFriendlyByteBuf, KeyPressedPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            KeyPressedPayload::key,
            KeyPressedPayload::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
