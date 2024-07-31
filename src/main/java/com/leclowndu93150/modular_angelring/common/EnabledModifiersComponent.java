package com.leclowndu93150.modular_angelring.common;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.Objects;

public record EnabledModifiersComponent(boolean inertiaEnabled, boolean speedModifierEnabled, boolean miningEnabled, boolean nightVisionEnabled) {
    public static final EnabledModifiersComponent EMPTY = new EnabledModifiersComponent(false, false, false, false);

    public static final MapCodec<EnabledModifiersComponent> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
            Codec.BOOL.fieldOf("inertia_enabled").forGetter(EnabledModifiersComponent::inertiaEnabled),
            Codec.BOOL.fieldOf("speed_enabled").forGetter(EnabledModifiersComponent::speedModifierEnabled),
            Codec.BOOL.fieldOf("mining_enabled").forGetter(EnabledModifiersComponent::miningEnabled),
            Codec.BOOL.fieldOf("night_vision_enabled").forGetter(EnabledModifiersComponent::nightVisionEnabled)
    ).apply(builder, EnabledModifiersComponent::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, EnabledModifiersComponent> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL,
            EnabledModifiersComponent::inertiaEnabled,
            ByteBufCodecs.BOOL,
            EnabledModifiersComponent::speedModifierEnabled,
            ByteBufCodecs.BOOL,
            EnabledModifiersComponent::miningEnabled,
            ByteBufCodecs.BOOL,
            EnabledModifiersComponent::nightVisionEnabled,
            EnabledModifiersComponent::new
    );

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EnabledModifiersComponent that)) return false;
        return miningEnabled == that.miningEnabled && inertiaEnabled == that.inertiaEnabled && speedModifierEnabled == that.speedModifierEnabled && nightVisionEnabled == that.nightVisionEnabled;
    }

    @Override
    public int hashCode() {
        return Objects.hash(inertiaEnabled, speedModifierEnabled, miningEnabled, nightVisionEnabled);
    }
}
