package com.leclowndu93150.modular_angelring.recipes;

import com.leclowndu93150.modular_angelring.AngelRingMain;
import com.leclowndu93150.modular_angelring.registry.DataComponentRegistry;
import com.leclowndu93150.modular_angelring.registry.ItemRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SmithingTransformRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.awt.*;
import java.util.concurrent.CompletableFuture;

public class ModularRingRecipes extends RecipeProvider {

    public ModularRingRecipes(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pRegistries) {
        super(pOutput, pRegistries);
    }

    @Override
    protected void buildRecipes(RecipeOutput pRecipeOutput) {
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE), Ingredient.of(ItemRegistry.ANGEL_RING), Ingredient.of(ItemRegistry.INERTIA_MODULE), RecipeCategory.MISC, ItemRegistry.ANGEL_RING.asItem())
                .unlocks("criteria", has(ItemRegistry.ANGEL_RING))
                .save(pRecipeOutput, AngelRingMain.MODID+":smithing/inertia_modifier");
    }

    @EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, modid = AngelRingMain.MODID)
    public static class DataGenerator {
        @SubscribeEvent
        public static void gatherData(GatherDataEvent event) {
            event.getGenerator().addProvider(
                    event.includeServer(),
                    new ModularRingRecipes(event.getGenerator().getPackOutput(), event.getLookupProvider())
            );
        }
    }
}
