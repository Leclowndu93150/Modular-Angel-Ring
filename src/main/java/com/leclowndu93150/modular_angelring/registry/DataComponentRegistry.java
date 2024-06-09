package com.leclowndu93150.modular_angelring.registry;

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
}
