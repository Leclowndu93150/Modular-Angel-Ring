package com.leclowndu93150.modular_angelring;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = AngelRingMain.MODID, bus = EventBusSubscriber.Bus.MOD)
public final class AngelRingConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.DoubleValue SLOWDOWN_FACTOR = BUILDER
            .comment("Inertia Module: Change this value for slower or faster slowdown (closer to 1 for slower).")
            .defineInRange("slowdownFactor", 0.75, 0, 1);

    private static final ModConfigSpec.DoubleValue MAGNET_RADIUS = BUILDER
            .comment("Magnet Module: Effect radius.")
            .defineInRange("magnetRadius", 5.0, 1, 32);

    private static final ModConfigSpec.DoubleValue MAGNET_PULL_SPEED = BUILDER
            .comment("Magnet Module: Pull speed.")
            .defineInRange("magnetPullSpeed", 0.2, 0, 1);

    private static final ModConfigSpec.DoubleValue MAX_FLIGHT_SPEED = BUILDER
            .comment("Speed Module: Max speed")
            .defineInRange("maxFlightSpeed", 0.15, 0, 1);


    static final ModConfigSpec SPEC = BUILDER.build();

    public static double slowdownFactor;
    public static double magnetRadius;
    public static double magnetPullSpeed;
    public static double maxFlightSpeed;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        slowdownFactor = SLOWDOWN_FACTOR.get();
        magnetRadius = MAGNET_RADIUS.get();
        magnetPullSpeed = MAGNET_PULL_SPEED.get();
        maxFlightSpeed = MAX_FLIGHT_SPEED.get();
    }
}