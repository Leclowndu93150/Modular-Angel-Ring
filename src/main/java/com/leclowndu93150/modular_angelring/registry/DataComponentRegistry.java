package com.leclowndu93150.modular_angelring.registry;

import com.leclowndu93150.modular_angelring.common.EnabledModifiersComponent;
import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static com.leclowndu93150.modular_angelring.AngelRingMain.MODID;

public class DataComponentRegistry {

    public static final DeferredRegister.DataComponents COMPONENTS = DeferredRegister.createDataComponents(MODID);

    public static final Supplier<DataComponentType<Boolean>> INERTIA_MODIFIER = COMPONENTS.registerComponentType("inertia_modifier",
            builder -> builder.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL));

    public static final Supplier<DataComponentType<Boolean>> MINING_MODIFIER = COMPONENTS.registerComponentType("mining_modifier",
            builder -> builder.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL));

    public static final Supplier<DataComponentType<Float>> SPEED_MODIFIER = COMPONENTS.registerComponentType("speed_modifier",
            builder -> builder.persistent(Codec.FLOAT).networkSynchronized(ByteBufCodecs.FLOAT));

    public static final Supplier<DataComponentType<Boolean>> NIGHT_VISION_MODIFIER = COMPONENTS.registerComponentType("night_vision_modifier",
            builder -> builder.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL));

    public static final Supplier<DataComponentType<Boolean>> MAGNET_MODIFIER = COMPONENTS.registerComponentType("magnet_modifier",
            builder -> builder.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL));

    public static final Supplier<DataComponentType<EnabledModifiersComponent>> MODIFIERS_ENABLED = COMPONENTS.registerComponentType("modifiers_enabled",
            builder -> builder.persistent(EnabledModifiersComponent.CODEC.codec()).networkSynchronized(EnabledModifiersComponent.STREAM_CODEC));

    public static final Supplier<DataComponentType<String>> WING = COMPONENTS.registerComponentType("wing_component",
            builder -> builder.persistent(Codec.STRING).networkSynchronized(ByteBufCodecs.STRING_UTF8));
}
