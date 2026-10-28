package com.leclowndu93150.modular_angelring;

import net.neoforged.neoforge.common.ModConfigSpec;

public final class AngelRingConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.DoubleValue SLOWDOWN_FACTOR = BUILDER
            .comment("Inertia Module: Change this value for slower or faster slowdown (closer to 1 for slower).")
            .defineInRange("slowdownFactor", 0.75, 0, 1);

    public static final ModConfigSpec.DoubleValue MAGNET_RADIUS = BUILDER
            .comment("Magnet Module: Effect radius.")
            .defineInRange("magnetRadius", 5.0, 1, 32);

    public static final ModConfigSpec.DoubleValue MAGNET_PULL_SPEED = BUILDER
            .comment("Magnet Module: Pull speed.")
            .defineInRange("magnetPullSpeed", 0.2, 0, 1);

    public static final ModConfigSpec.DoubleValue MAX_FLIGHT_SPEED = BUILDER
            .comment("Speed Module: Max speed")
            .defineInRange("maxFlightSpeed", 0.15, 0, 1);

    public static final ModConfigSpec.DoubleValue REACH_BONUS = BUILDER
            .comment("Reach Module: Extra block and entity reach.")
            .defineInRange("reachBonus", 2.0, 0, 16);

    public static final ModConfigSpec.IntValue FAST_BUILD_DELAY = BUILDER
            .comment("Fast Build Module: Ticks between block placements (vanilla is 4).")
            .defineInRange("fastBuildDelay", 0, 0, 4);

    static final ModConfigSpec SPEC = BUILDER.build();
}
