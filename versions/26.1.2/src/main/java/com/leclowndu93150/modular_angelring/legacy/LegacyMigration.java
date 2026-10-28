package com.leclowndu93150.modular_angelring.legacy;

import com.leclowndu93150.modular_angelring.AngelRingMain;
import com.leclowndu93150.modular_angelring.api.InstalledModules;
import com.leclowndu93150.modular_angelring.api.RingModule;
import com.leclowndu93150.modular_angelring.api.WingStyle;
import com.leclowndu93150.modular_angelring.modules.SpeedModule;
import com.leclowndu93150.modular_angelring.registry.DataComponentRegistry;
import com.leclowndu93150.modular_angelring.registry.ModuleRegistry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Stream;

@EventBusSubscriber(modid = AngelRingMain.MODID)
public final class LegacyMigration {
    private static final String LEGACY_NO_KEYS_PRESSED = "no_keys_pressed";

    private static final DeferredRegister.DataComponents COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, AngelRingMain.MODID);
    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, AngelRingMain.MODID);

    private static final Supplier<DataComponentType<Boolean>> INERTIA = registerFlag("inertia_modifier");
    private static final Supplier<DataComponentType<Boolean>> MINING = registerFlag("mining_modifier");
    private static final Supplier<DataComponentType<Boolean>> NIGHT_VISION = registerFlag("night_vision_modifier");
    private static final Supplier<DataComponentType<Boolean>> MAGNET = registerFlag("magnet_modifier");
    private static final Supplier<DataComponentType<Float>> SPEED = COMPONENTS.registerComponentType("speed_modifier",
            builder -> builder.persistent(Codec.FLOAT));
    private static final Supplier<DataComponentType<EnabledModules>> ENABLED = COMPONENTS.registerComponentType("modifiers_enabled",
            builder -> builder.persistent(EnabledModules.CODEC.codec()));
    private static final Supplier<DataComponentType<String>> WING = COMPONENTS.registerComponentType("wing_component",
            builder -> builder.persistent(Codec.STRING));

    private static final Supplier<AttachmentType<Boolean>> PLAYER_MIGRATED = ATTACHMENT_TYPES.register("legacy_migrated",
            () -> AttachmentType.builder(() -> false).serialize(Codec.BOOL.fieldOf("migrated")).copyOnDeath().build());

    private LegacyMigration() {
    }

    public static void register(IEventBus modEventBus) {
        COMPONENTS.register(modEventBus);
        ATTACHMENT_TYPES.register(modEventBus);
    }

    private static Supplier<DataComponentType<Boolean>> registerFlag(String name) {
        return COMPONENTS.registerComponentType(name, builder -> builder.persistent(Codec.BOOL));
    }

    public static void migrate(ItemStack ring) {
        if (Stream.of(INERTIA, MINING, NIGHT_VISION, MAGNET, SPEED, ENABLED, WING).noneMatch(type -> ring.has(type.get()))) return;

        EnabledModules enabled = Objects.requireNonNullElse(ring.remove(ENABLED.get()), EnabledModules.NONE);
        InstalledModules modules = InstalledModules.of(ring);
        modules = migrateFlag(ring, modules, MINING.get(), ModuleRegistry.MINING.get(), true);
        modules = migrateFlag(ring, modules, INERTIA.get(), ModuleRegistry.INERTIA.get(), enabled.inertia());
        modules = migrateFlag(ring, modules, NIGHT_VISION.get(), ModuleRegistry.NIGHT_VISION.get(), enabled.nightVision());
        modules = migrateFlag(ring, modules, MAGNET.get(), ModuleRegistry.MAGNET.get(), enabled.magnet());

        Float speed = ring.remove(SPEED.get());
        if (speed != null) {
            modules = modules.with(ModuleRegistry.SPEED.get(), enabled.speed());
            ring.set(DataComponentRegistry.FLIGHT_SPEED, speed);
        }
        ring.set(DataComponentRegistry.MODULES, modules);

        String wing = ring.remove(WING.get());
        if (wing != null) {
            ring.remove(DataComponents.CUSTOM_MODEL_DATA);
            Arrays.stream(WingStyle.values())
                    .filter(style -> style.name().equals(wing))
                    .findFirst()
                    .ifPresent(style -> ring.set(DataComponentRegistry.WINGS, style));
        }
    }

    private static InstalledModules migrateFlag(ItemStack ring, InstalledModules modules, DataComponentType<Boolean> flag, RingModule module, boolean enabled) {
        return Boolean.TRUE.equals(ring.remove(flag)) ? modules.with(module, enabled) : modules;
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        if (player.getData(PLAYER_MIGRATED)) return;

        player.getAttribute(NeoForgeMod.CREATIVE_FLIGHT).setBaseValue(0);
        player.getAbilities().setFlyingSpeed(SpeedModule.DEFAULT_SPEED);
        player.onUpdateAbilities();
        player.getPersistentData().remove(LEGACY_NO_KEYS_PRESSED);
        player.setData(PLAYER_MIGRATED, true);
    }

    private record EnabledModules(boolean inertia, boolean speed, boolean mining, boolean nightVision, boolean magnet) {
        private static final EnabledModules NONE = new EnabledModules(false, false, false, false, false);

        private static final MapCodec<EnabledModules> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Codec.BOOL.optionalFieldOf("inertia_enabled", false).forGetter(EnabledModules::inertia),
                Codec.BOOL.optionalFieldOf("speed_enabled", false).forGetter(EnabledModules::speed),
                Codec.BOOL.optionalFieldOf("mining_enabled", false).forGetter(EnabledModules::mining),
                Codec.BOOL.optionalFieldOf("night_vision_enabled", false).forGetter(EnabledModules::nightVision),
                Codec.BOOL.optionalFieldOf("magnet_enabled", false).forGetter(EnabledModules::magnet)
        ).apply(instance, EnabledModules::new));
    }
}
