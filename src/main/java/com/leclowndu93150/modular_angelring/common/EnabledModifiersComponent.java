package com.leclowndu93150.modular_angelring.common;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.Objects;

public record EnabledModifiersComponent(boolean inertiaEnabled, boolean speedModifierEnabled, boolean miningEnabled, boolean nightVisionEnabled, boolean magnetEnabled) {
    public static final EnabledModifiersComponent EMPTY = new EnabledModifiersComponent(false, false, false, false,false);

    public static final MapCodec<EnabledModifiersComponent> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
            Codec.BOOL.optionalFieldOf("inertia_enabled", false).forGetter(EnabledModifiersComponent::inertiaEnabled),
            Codec.BOOL.optionalFieldOf("speed_enabled", false).forGetter(EnabledModifiersComponent::speedModifierEnabled),
            Codec.BOOL.optionalFieldOf("mining_enabled", false).forGetter(EnabledModifiersComponent::miningEnabled),
            Codec.BOOL.optionalFieldOf("night_vision_enabled", false).forGetter(EnabledModifiersComponent::nightVisionEnabled),
            Codec.BOOL.optionalFieldOf("magnet_enabled",false).forGetter(EnabledModifiersComponent::magnetEnabled)
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
            ByteBufCodecs.BOOL,
            EnabledModifiersComponent::magnetEnabled,
            EnabledModifiersComponent::new
    );

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EnabledModifiersComponent that)) return false;
        return miningEnabled == that.miningEnabled && inertiaEnabled == that.inertiaEnabled && speedModifierEnabled == that.speedModifierEnabled && nightVisionEnabled == that.nightVisionEnabled && magnetEnabled == that.magnetEnabled;
    }

    @Override
    public int hashCode() {
        return Objects.hash(inertiaEnabled, speedModifierEnabled, miningEnabled, nightVisionEnabled, magnetEnabled);
    }
}
