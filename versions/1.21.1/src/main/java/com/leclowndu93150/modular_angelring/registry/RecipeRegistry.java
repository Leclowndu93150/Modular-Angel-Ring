package com.leclowndu93150.modular_angelring.registry;

import com.leclowndu93150.modular_angelring.AngelRingMain;
import com.leclowndu93150.modular_angelring.recipes.ModuleInstallRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class RecipeRegistry {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, AngelRingMain.MODID);

    public static final DeferredHolder<RecipeSerializer<?>, ModuleInstallRecipe.Serializer> MODULE_INSTALL = RECIPE_SERIALIZERS.register("module_install", ModuleInstallRecipe.Serializer::new);

    private RecipeRegistry() {
    }
}
