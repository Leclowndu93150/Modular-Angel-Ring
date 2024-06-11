package com.leclowndu93150.modular_angelring.recipes;

import net.minecraft.data.recipes.SpecialRecipeBuilder;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Recipe;

import java.util.function.Function;

public class ModularRingRecipes extends SpecialRecipeBuilder {
    public ModularRingRecipes(Function<CraftingBookCategory, Recipe<?>> p_312708_) {
        super(p_312708_);
    }
}
