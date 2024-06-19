package com.leclowndu93150.modular_angelring.recipes;

import com.leclowndu93150.modular_angelring.AngelRingMain;
import com.leclowndu93150.modular_angelring.registry.DataComponentRegistry;
import com.leclowndu93150.modular_angelring.registry.ItemRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.SmithingTransformRecipe;
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
        ItemStack itemStackInertia = ItemRegistry.ANGEL_RING.asItem().getDefaultInstance();
        itemStackInertia.set(DataComponentRegistry.INERTIA_MODIFIER, true);

        AdvancedSmithingRecipeBuilder.smithing(Ingredient.of(ItemRegistry.UPGRADE_TEMPLATE), Ingredient.of(ItemRegistry.ANGEL_RING), Ingredient.of(ItemRegistry.INERTIA_MODULE), RecipeCategory.MISC, itemStackInertia)
                .unlocks("criteria", has(ItemRegistry.ANGEL_RING))
                .save(pRecipeOutput, AngelRingMain.MODID+":smithing/inertia_modifier");

        ItemStack itemStackMining = ItemRegistry.ANGEL_RING.asItem().getDefaultInstance();
        itemStackMining.set(DataComponentRegistry.MINING_MODIFIER, true);

        AdvancedSmithingRecipeBuilder.smithing(Ingredient.of(ItemRegistry.UPGRADE_TEMPLATE), Ingredient.of(ItemRegistry.ANGEL_RING), Ingredient.of(ItemRegistry.MINING_MODULE), RecipeCategory.MISC, itemStackMining)
                .unlocks("criteria", has(ItemRegistry.ANGEL_RING))
                .save(pRecipeOutput, AngelRingMain.MODID+":smithing/mining_modifier");

        ItemStack itemStackSpeed = ItemRegistry.ANGEL_RING.asItem().getDefaultInstance();
        itemStackSpeed.set(DataComponentRegistry.SPEED_MODIFIER, 0.02F);

        AdvancedSmithingRecipeBuilder.smithing(Ingredient.of(ItemRegistry.UPGRADE_TEMPLATE), Ingredient.of(ItemRegistry.ANGEL_RING), Ingredient.of(ItemRegistry.SPEED_MODULE), RecipeCategory.MISC, itemStackSpeed)
                .unlocks("criteria", has(ItemRegistry.ANGEL_RING))
                .save(pRecipeOutput, AngelRingMain.MODID+":smithing/speed_modifier");

        SmithingTransformRecipeBuilder.smithing(Ingredient.EMPTY, Ingredient.of(ItemRegistry.BLANK_MODULE), Ingredient.of(Items.DIAMOND_PICKAXE), RecipeCategory.MISC, ItemRegistry.MINING_MODULE.asItem())
                .unlocks("criteria", has(ItemRegistry.ANGEL_RING))
                .save(pRecipeOutput, AngelRingMain.MODID+":smithing/mining_module");

        SmithingTransformRecipeBuilder.smithing(Ingredient.EMPTY, Ingredient.of(ItemRegistry.BLANK_MODULE), Ingredient.of(Items.PHANTOM_MEMBRANE), RecipeCategory.MISC, ItemRegistry.INERTIA_MODULE.asItem())
                .unlocks("criteria", has(ItemRegistry.ANGEL_RING))
                .save(pRecipeOutput, AngelRingMain.MODID+":smithing/inertia_module");

        SmithingTransformRecipeBuilder.smithing(Ingredient.EMPTY, Ingredient.of(ItemRegistry.BLANK_MODULE), Ingredient.of(Items.SUGAR), RecipeCategory.MISC, ItemRegistry.SPEED_MODULE.asItem())
                .unlocks("criteria", has(ItemRegistry.ANGEL_RING))
                .save(pRecipeOutput, AngelRingMain.MODID+":smithing/speed_module");
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
