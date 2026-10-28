package com.leclowndu93150.modular_angelring.registry;

import com.leclowndu93150.modular_angelring.AngelRingMain;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class CreativeTabRegistry {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, AngelRingMain.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> ANGEL_RING_TAB = CREATIVE_MODE_TABS.register("angelring_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + AngelRingMain.MODID))
            .icon(ItemRegistry.ANGEL_RING::toStack)
            .displayItems((parameters, output) -> {
                output.accept(ItemRegistry.ANGEL_RING);
                output.accept(ItemRegistry.GOLD_RING);
                output.accept(ItemRegistry.ANGEL_WINGS);
                output.accept(ItemRegistry.INERTIA_MODULE);
                output.accept(ItemRegistry.MINING_MODULE);
                output.accept(ItemRegistry.SPEED_MODULE);
                output.accept(ItemRegistry.NIGHT_VISION_MODULE);
                output.accept(ItemRegistry.MAGNET_MODULE);
                output.accept(ItemRegistry.REACH_MODULE);
                output.accept(ItemRegistry.STEP_ASSIST_MODULE);
                output.accept(ItemRegistry.PHANTOM_WARD_MODULE);
                output.accept(ItemRegistry.VOID_GUARDIAN_MODULE);
                output.accept(ItemRegistry.AQUA_LUNG_MODULE);
                output.accept(ItemRegistry.FAST_BUILD_MODULE);
                output.accept(ItemRegistry.UPGRADE_TEMPLATE);
                output.accept(ItemRegistry.BLANK_MODULE);
                output.accept(ItemRegistry.BLUE_DRAGON_WINGS);
                output.accept(ItemRegistry.BIG_DRAGON_WINGS);
                output.accept(ItemRegistry.DRAGON_WINGS);
                output.accept(ItemRegistry.BUTTERFLY_WINGS);
                output.accept(ItemRegistry.GOLDEN_WINGS);
                output.accept(ItemRegistry.GOLD_WINGS);
                output.accept(ItemRegistry.BAT_WINGS);
            })
            .build());

    private CreativeTabRegistry() {
    }
}
