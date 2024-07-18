package com.leclowndu93150.modular_angelring.networking;

import com.leclowndu93150.modular_angelring.AngelRingMain;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record NoKeyPressedPayload(boolean pressed) implements CustomPacketPayload {
    public static final Type<NoKeyPressedPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(AngelRingMain.MODID, "no_keypressed_payload"));
    public static final StreamCodec<RegistryFriendlyByteBuf, NoKeyPressedPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL,
            NoKeyPressedPayload::pressed,
            NoKeyPressedPayload::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
