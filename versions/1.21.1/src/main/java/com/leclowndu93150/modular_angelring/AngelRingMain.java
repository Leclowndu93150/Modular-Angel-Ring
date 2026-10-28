package com.leclowndu93150.modular_angelring;

import com.leclowndu93150.modular_angelring.legacy.LegacyMigration;
import com.leclowndu93150.modular_angelring.registry.CreativeTabRegistry;
import com.leclowndu93150.modular_angelring.registry.DataComponentRegistry;
import com.leclowndu93150.modular_angelring.registry.ItemRegistry;
import com.leclowndu93150.modular_angelring.registry.ModuleRegistry;
import com.leclowndu93150.modular_angelring.registry.RecipeRegistry;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;

@Mod(AngelRingMain.MODID)
public class AngelRingMain {
    public static final String MODID = "modular_angelring";

    public AngelRingMain(IEventBus modEventBus, ModContainer modContainer) {
        ModuleRegistry.MODULES.register(modEventBus);
        ItemRegistry.ITEMS.register(modEventBus);
        DataComponentRegistry.COMPONENTS.register(modEventBus);
        RecipeRegistry.RECIPE_SERIALIZERS.register(modEventBus);
        CreativeTabRegistry.CREATIVE_MODE_TABS.register(modEventBus);
        LegacyMigration.register(modEventBus);
        modContainer.registerConfig(ModConfig.Type.COMMON, AngelRingConfig.SPEC);
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}
