package com.leclowndu93150.modular_angelring.api;

import com.leclowndu93150.modular_angelring.registry.DataComponentRegistry;
import com.leclowndu93150.modular_angelring.registry.ModuleRegistry;
import com.mojang.serialization.Codec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record InstalledModules(Map<RingModule, Boolean> modules) {
    public static final InstalledModules EMPTY = new InstalledModules(Map.of());

    public static final Codec<InstalledModules> CODEC = Codec.unboundedMap(ModuleRegistry.REGISTRY.byNameCodec(), Codec.BOOL)
            .xmap(InstalledModules::new, InstalledModules::modules);

    public static final StreamCodec<RegistryFriendlyByteBuf, InstalledModules> STREAM_CODEC = ByteBufCodecs
            .map(HashMap<RingModule, Boolean>::new, ByteBufCodecs.registry(ModuleRegistry.KEY), ByteBufCodecs.BOOL)
            .map(InstalledModules::new, installed -> new HashMap<>(installed.modules()));

    public InstalledModules {
        modules = Map.copyOf(modules);
    }

    public static InstalledModules of(ItemStack ring) {
        return ring.getOrDefault(DataComponentRegistry.MODULES, EMPTY);
    }

    public boolean has(RingModule module) {
        return modules.containsKey(module);
    }

    public boolean isEnabled(RingModule module) {
        return modules.getOrDefault(module, false);
    }

    public List<RingModule> installed() {
        return ModuleRegistry.REGISTRY.stream().filter(this::has).toList();
    }

    public List<RingModule> enabled() {
        return ModuleRegistry.REGISTRY.stream().filter(this::isEnabled).toList();
    }

    public InstalledModules with(RingModule module, boolean enabled) {
        Map<RingModule, Boolean> updated = new HashMap<>(modules);
        updated.put(module, enabled);
        return new InstalledModules(updated);
    }
}
