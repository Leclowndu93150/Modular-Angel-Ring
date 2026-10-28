package com.leclowndu93150.modular_angelring.data;

import com.leclowndu93150.modular_angelring.api.RingModule;
import com.leclowndu93150.modular_angelring.api.WingStyle;
import com.leclowndu93150.modular_angelring.recipes.ModuleInstallRecipe;
import com.leclowndu93150.modular_angelring.registry.DataComponentRegistry;
import com.leclowndu93150.modular_angelring.registry.ItemRegistry;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.SmithingTransformRecipe;

import java.util.LinkedHashMap;
import java.util.Map;

public class RingSmithingRecipeBuilder {
    private final SmithingTransformRecipe recipe;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();

    private RingSmithingRecipeBuilder(SmithingTransformRecipe recipe) {
        this.recipe = recipe;
    }

    public static RingSmithingRecipeBuilder installModule(Ingredient template, Ingredient ring, Ingredient moduleItem, RingModule module) {
        return new RingSmithingRecipeBuilder(new ModuleInstallRecipe(template, ring, moduleItem, module));
    }

    public static RingSmithingRecipeBuilder applyWings(Ingredient template, Ingredient ring, Ingredient wingsItem, WingStyle style) {
        ItemStack result = ItemRegistry.ANGEL_RING.toStack();
        result.set(DataComponentRegistry.WINGS, style);
        return new RingSmithingRecipeBuilder(new SmithingTransformRecipe(template, ring, wingsItem, result));
    }

    public RingSmithingRecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        criteria.put(name, criterion);
        return this;
    }

    public void save(RecipeOutput output, ResourceLocation id) {
        Advancement.Builder advancement = output.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(AdvancementRequirements.Strategy.OR);
        criteria.forEach(advancement::addCriterion);
        output.accept(id, recipe, advancement.build(id.withPrefix("recipes/" + RecipeCategory.MISC.getFolderName() + "/")));
    }
}
