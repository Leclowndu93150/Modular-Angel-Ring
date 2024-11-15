package com.leclowndu93150.modular_angelring.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.leclowndu93150.modular_angelring.AngelRingMain.MODID;
import static com.leclowndu93150.modular_angelring.registry.ItemRegistry.*;

public class CreativeTabRegistry {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> UTILS_TAB = CREATIVE_MODE_TABS.register("angelring_tab", () -> CreativeModeTab.builder()
            .title(Component.literal("Modular Angel Ring")) //The language key for the title of your CreativeModeTab
            .icon(() -> ANGEL_RING.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(ANGEL_RING.get());
                output.accept(GOLD_RING.get());
                output.accept(ANGEL_WINGS.get());
                output.accept(INERTIA_MODULE.get());
                output.accept(MINING_MODULE.get());
                output.accept(SPEED_MODULE.get());
                output.accept(NIGHT_VISION_MODULE.get());
                output.accept(MAGNET_MODULE.get());
                output.accept(UPGRADE_TEMPLATE.get());
                output.accept(BLANK_MODULE.get());

                output.accept(BLUE_DRAGON_WINGS.get());
                output.accept(BIG_DRAGON_WINGS.get());
                output.accept(DRAGON_WINGS.get());
                output.accept(BUTTERFLY_WINGS.get());
                output.accept(GOLDEN_WINGS.get());
                output.accept(GOLD_WINGS.get());
                output.accept(BAT_WINGS.get());

            }).build());

}


