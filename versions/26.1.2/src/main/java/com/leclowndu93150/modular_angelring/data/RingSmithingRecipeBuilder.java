package com.leclowndu93150.modular_angelring.data;

import com.leclowndu93150.modular_angelring.api.RingModule;
import com.leclowndu93150.modular_angelring.api.WingStyle;
import com.leclowndu93150.modular_angelring.recipes.ModuleInstallRecipe;
import com.leclowndu93150.modular_angelring.registry.DataComponentRegistry;
import com.leclowndu93150.modular_angelring.registry.ItemRegistry;
import net.minecraft.advancements.Criterion;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeUnlockAdvancementBuilder;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.SmithingTransformRecipe;

import java.util.Optional;

public class RingSmithingRecipeBuilder {
    private final SmithingTransformRecipe recipe;
    private final RecipeUnlockAdvancementBuilder advancement = new RecipeUnlockAdvancementBuilder();

    private RingSmithingRecipeBuilder(SmithingTransformRecipe recipe) {
        this.recipe = recipe;
    }

    public static RingSmithingRecipeBuilder installModule(Ingredient template, Ingredient ring, Ingredient moduleItem, RingModule module) {
        return new RingSmithingRecipeBuilder(new ModuleInstallRecipe(new Recipe.CommonInfo(true), Optional.of(template), ring, Optional.of(moduleItem), module));
    }

    public static RingSmithingRecipeBuilder applyWings(Ingredient template, Ingredient ring, Ingredient wingsItem, WingStyle style) {
        DataComponentPatch wings = DataComponentPatch.builder().set(DataComponentRegistry.WINGS.get(), style).build();
        ItemStackTemplate result = new ItemStackTemplate(ItemRegistry.ANGEL_RING.get(), wings);
        return new RingSmithingRecipeBuilder(new SmithingTransformRecipe(new Recipe.CommonInfo(true), Optional.of(template), ring, Optional.of(wingsItem), result));
    }

    public RingSmithingRecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        advancement.unlockedBy(name, criterion);
        return this;
    }

    public void save(RecipeOutput output, Identifier id) {
        ResourceKey<Recipe<?>> key = ResourceKey.create(Registries.RECIPE, id);
        output.accept(key, recipe, advancement.build(output, key, RecipeCategory.MISC));
    }
}
