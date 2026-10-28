package com.leclowndu93150.modular_angelring.registry;

import com.leclowndu93150.modular_angelring.AngelRingMain;
import com.leclowndu93150.modular_angelring.api.InstalledModules;
import com.leclowndu93150.modular_angelring.api.WingStyle;
import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class DataComponentRegistry {
    public static final DeferredRegister.DataComponents COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, AngelRingMain.MODID);

    public static final Supplier<DataComponentType<InstalledModules>> MODULES = COMPONENTS.registerComponentType("modules",
            builder -> builder.persistent(InstalledModules.CODEC).networkSynchronized(InstalledModules.STREAM_CODEC));

    public static final Supplier<DataComponentType<Float>> FLIGHT_SPEED = COMPONENTS.registerComponentType("flight_speed",
            builder -> builder.persistent(Codec.FLOAT).networkSynchronized(ByteBufCodecs.FLOAT));

    public static final Supplier<DataComponentType<WingStyle>> WINGS = COMPONENTS.registerComponentType("wings",
            builder -> builder.persistent(WingStyle.CODEC).networkSynchronized(WingStyle.STREAM_CODEC));

    private DataComponentRegistry() {
    }
}
