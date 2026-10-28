package com.leclowndu93150.modular_angelring.registry;

import com.leclowndu93150.modular_angelring.AngelRingMain;
import com.leclowndu93150.modular_angelring.api.RingModule;
import com.leclowndu93150.modular_angelring.modules.AquaLungModule;
import com.leclowndu93150.modular_angelring.modules.FastBuildModule;
import com.leclowndu93150.modular_angelring.modules.InertiaModule;
import com.leclowndu93150.modular_angelring.modules.MagnetModule;
import com.leclowndu93150.modular_angelring.modules.MiningModule;
import com.leclowndu93150.modular_angelring.modules.NightVisionModule;
import com.leclowndu93150.modular_angelring.modules.PhantomWardModule;
import com.leclowndu93150.modular_angelring.modules.ReachModule;
import com.leclowndu93150.modular_angelring.modules.SpeedModule;
import com.leclowndu93150.modular_angelring.modules.StepAssistModule;
import com.leclowndu93150.modular_angelring.modules.VoidGuardianModule;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModuleRegistry {
    public static final ResourceKey<Registry<RingModule>> KEY = ResourceKey.createRegistryKey(AngelRingMain.id("module"));
    public static final DeferredRegister<RingModule> MODULES = DeferredRegister.create(KEY, AngelRingMain.MODID);
    public static final Registry<RingModule> REGISTRY = MODULES.makeRegistry(builder -> builder.sync(true));

    public static final DeferredHolder<RingModule, MiningModule> MINING = MODULES.register("mining", MiningModule::new);
    public static final DeferredHolder<RingModule, InertiaModule> INERTIA = MODULES.register("inertia", InertiaModule::new);
    public static final DeferredHolder<RingModule, SpeedModule> SPEED = MODULES.register("speed", SpeedModule::new);
    public static final DeferredHolder<RingModule, NightVisionModule> NIGHT_VISION = MODULES.register("night_vision", NightVisionModule::new);
    public static final DeferredHolder<RingModule, MagnetModule> MAGNET = MODULES.register("magnet", MagnetModule::new);
    public static final DeferredHolder<RingModule, ReachModule> REACH = MODULES.register("reach", ReachModule::new);
    public static final DeferredHolder<RingModule, StepAssistModule> STEP_ASSIST = MODULES.register("step_assist", StepAssistModule::new);
    public static final DeferredHolder<RingModule, PhantomWardModule> PHANTOM_WARD = MODULES.register("phantom_ward", PhantomWardModule::new);
    public static final DeferredHolder<RingModule, VoidGuardianModule> VOID_GUARDIAN = MODULES.register("void_guardian", VoidGuardianModule::new);
    public static final DeferredHolder<RingModule, AquaLungModule> AQUA_LUNG = MODULES.register("aqua_lung", AquaLungModule::new);
    public static final DeferredHolder<RingModule, FastBuildModule> FAST_BUILD = MODULES.register("fast_build", FastBuildModule::new);

    private ModuleRegistry() {
    }
}
