package com.leclowndu93150.modular_angelring.recipes;

import com.leclowndu93150.modular_angelring.AngelRingMain;
import com.leclowndu93150.modular_angelring.registry.DataComponentRegistry;
import com.leclowndu93150.modular_angelring.registry.ItemRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomModelData;
import net.minecraft.world.item.crafting.CraftingRecipe;
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
        itemStackSpeed.set(DataComponentRegistry.SPEED_MODIFIER, 0.05F);

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

        ItemStack itemStackBat = ItemRegistry.ANGEL_RING.asItem().getDefaultInstance();
        itemStackBat.set(DataComponentRegistry.WING, "BAT");
        itemStackBat.set(DataComponents.CUSTOM_MODEL_DATA,new CustomModelData(1));

        AdvancedSmithingRecipeBuilder.smithing(Ingredient.of(ItemRegistry.UPGRADE_TEMPLATE), Ingredient.of(ItemRegistry.ANGEL_RING), Ingredient.of(ItemRegistry.BAT_WINGS), RecipeCategory.MISC, itemStackBat)
                .unlocks("criteria", has(ItemRegistry.ANGEL_RING))
                .save(pRecipeOutput, AngelRingMain.MODID+":smithing/bat_ring");

        ItemStack itemStackBig = ItemRegistry.ANGEL_RING.asItem().getDefaultInstance();
        itemStackBig.set(DataComponentRegistry.WING, "BIG_DRAGON");
        itemStackBig.set(DataComponents.CUSTOM_MODEL_DATA,new CustomModelData(2));

        AdvancedSmithingRecipeBuilder.smithing(Ingredient.of(ItemRegistry.UPGRADE_TEMPLATE), Ingredient.of(ItemRegistry.ANGEL_RING), Ingredient.of(ItemRegistry.BIG_DRAGON_WINGS), RecipeCategory.MISC, itemStackBig)
                .unlocks("criteria", has(ItemRegistry.ANGEL_RING))
                .save(pRecipeOutput, AngelRingMain.MODID+":smithing/big_dragon_ring");

        ItemStack itemStackBlue = ItemRegistry.ANGEL_RING.asItem().getDefaultInstance();
        itemStackBlue.set(DataComponentRegistry.WING, "BLUE_DRAGON");
        itemStackBlue.set(DataComponents.CUSTOM_MODEL_DATA,new CustomModelData(3));

        AdvancedSmithingRecipeBuilder.smithing(Ingredient.of(ItemRegistry.UPGRADE_TEMPLATE), Ingredient.of(ItemRegistry.ANGEL_RING), Ingredient.of(ItemRegistry.BLUE_DRAGON_WINGS), RecipeCategory.MISC, itemStackBlue)
                .unlocks("criteria", has(ItemRegistry.ANGEL_RING))
                .save(pRecipeOutput, AngelRingMain.MODID+":smithing/blue_dragon_ring");

        ItemStack itemStackButterfly = ItemRegistry.ANGEL_RING.asItem().getDefaultInstance();
        itemStackButterfly.set(DataComponentRegistry.WING, "BUTTERFLY");
        itemStackButterfly.set(DataComponents.CUSTOM_MODEL_DATA,new CustomModelData(4));

        AdvancedSmithingRecipeBuilder.smithing(Ingredient.of(ItemRegistry.UPGRADE_TEMPLATE), Ingredient.of(ItemRegistry.ANGEL_RING), Ingredient.of(ItemRegistry.BUTTERFLY_WINGS), RecipeCategory.MISC, itemStackButterfly)
                .unlocks("criteria", has(ItemRegistry.ANGEL_RING))
                .save(pRecipeOutput, AngelRingMain.MODID+":smithing/butterfly_ring");

        ItemStack itemStackDragon = ItemRegistry.ANGEL_RING.asItem().getDefaultInstance();
        itemStackDragon.set(DataComponentRegistry.WING, "DRAGON");
        itemStackDragon.set(DataComponents.CUSTOM_MODEL_DATA,new CustomModelData(5));

        AdvancedSmithingRecipeBuilder.smithing(Ingredient.of(ItemRegistry.UPGRADE_TEMPLATE), Ingredient.of(ItemRegistry.ANGEL_RING), Ingredient.of(ItemRegistry.DRAGON_WINGS), RecipeCategory.MISC, itemStackDragon)
                .unlocks("criteria", has(ItemRegistry.ANGEL_RING))
                .save(pRecipeOutput, AngelRingMain.MODID+":smithing/dragon_ring");

        ItemStack itemStackGold = ItemRegistry.ANGEL_RING.asItem().getDefaultInstance();
        itemStackGold.set(DataComponentRegistry.WING, "GOLD");
        itemStackGold.set(DataComponents.CUSTOM_MODEL_DATA,new CustomModelData(6));

        AdvancedSmithingRecipeBuilder.smithing(Ingredient.of(ItemRegistry.UPGRADE_TEMPLATE), Ingredient.of(ItemRegistry.ANGEL_RING), Ingredient.of(ItemRegistry.GOLD_WINGS), RecipeCategory.MISC, itemStackGold)
                .unlocks("criteria", has(ItemRegistry.ANGEL_RING))
                .save(pRecipeOutput, AngelRingMain.MODID+":smithing/gold_ring");

        ItemStack itemStackGolden = ItemRegistry.ANGEL_RING.asItem().getDefaultInstance();
        itemStackGolden.set(DataComponentRegistry.WING, "GOLDEN");
        itemStackGolden.set(DataComponents.CUSTOM_MODEL_DATA,new CustomModelData(7));

        AdvancedSmithingRecipeBuilder.smithing(Ingredient.of(ItemRegistry.UPGRADE_TEMPLATE), Ingredient.of(ItemRegistry.ANGEL_RING), Ingredient.of(ItemRegistry.GOLDEN_WINGS), RecipeCategory.MISC, itemStackGolden)
                .unlocks("criteria", has(ItemRegistry.ANGEL_RING))
                .save(pRecipeOutput, AngelRingMain.MODID+":smithing/golden_ring");

        ItemStack itemStackFishron = ItemRegistry.ANGEL_RING.asItem().getDefaultInstance();
        itemStackFishron.set(DataComponentRegistry.WING, "FISHRON");
        itemStackFishron.set(DataComponents.CUSTOM_MODEL_DATA,new CustomModelData(8));

        AdvancedSmithingRecipeBuilder.smithing(Ingredient.of(ItemRegistry.UPGRADE_TEMPLATE), Ingredient.of(ItemRegistry.ANGEL_RING), Ingredient.of(ItemRegistry.FISHRON_WINGS), RecipeCategory.MISC, itemStackFishron)
                .unlocks("criteria", has(ItemRegistry.ANGEL_RING))
                .save(pRecipeOutput, AngelRingMain.MODID+":smithing/fishron_ring");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,ItemRegistry.BAT_WINGS)
                .requires(ItemRegistry.ANGEL_WINGS)
                .requires(Items.COCOA_BEANS)
                .requires(Items.PHANTOM_MEMBRANE)
                .unlockedBy("criteria", has(ItemRegistry.ANGEL_WINGS))
                .save(pRecipeOutput,AngelRingMain.MODID+":shapeless/bat_wings");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,ItemRegistry.BIG_DRAGON_WINGS)
                .requires(ItemRegistry.ANGEL_WINGS)
                .requires(Items.DRAGON_HEAD)
                .requires(Items.ENDER_EYE)
                .unlockedBy("criteria", has(ItemRegistry.ANGEL_WINGS))
                .save(pRecipeOutput,AngelRingMain.MODID+":shapeless/big_dragon_wings");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,ItemRegistry.BLUE_DRAGON_WINGS)
                .requires(ItemRegistry.ANGEL_WINGS)
                .requires(Items.DRAGON_HEAD)
                .requires(Items.LIGHT_BLUE_DYE)
                .unlockedBy("criteria", has(ItemRegistry.ANGEL_WINGS))
                .save(pRecipeOutput,AngelRingMain.MODID+":shapeless/blue_dragon_wings");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,ItemRegistry.BUTTERFLY_WINGS)
                .requires(ItemRegistry.ANGEL_WINGS)
                .requires(Items.GLOWSTONE_DUST)
                .requires(Items.PURPLE_DYE)
                .unlockedBy("criteria", has(ItemRegistry.ANGEL_WINGS))
                .save(pRecipeOutput,AngelRingMain.MODID+":shapeless/butterfly_wings");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,ItemRegistry.DRAGON_WINGS)
                .requires(ItemRegistry.ANGEL_WINGS)
                .requires(Items.DRAGON_HEAD)
                .requires(Items.RED_DYE)
                .requires(Items.ORANGE_DYE)
                .unlockedBy("criteria", has(ItemRegistry.ANGEL_WINGS))
                .save(pRecipeOutput,AngelRingMain.MODID+":shapeless/dragon_wings");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,ItemRegistry.GOLD_WINGS)
                .requires(ItemRegistry.ANGEL_WINGS)
                .requires(Items.GOLD_INGOT)
                .unlockedBy("criteria", has(ItemRegistry.ANGEL_WINGS))
                .save(pRecipeOutput,AngelRingMain.MODID+":shapeless/gold_wings");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,ItemRegistry.GOLDEN_WINGS)
                .requires(ItemRegistry.ANGEL_WINGS)
                .requires(Items.GOLD_INGOT)
                .requires(Items.DIAMOND)
                .unlockedBy("criteria", has(ItemRegistry.ANGEL_WINGS))
                .save(pRecipeOutput,AngelRingMain.MODID+":shapeless/golden_wings");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,ItemRegistry.FISHRON_WINGS)
                .requires(ItemRegistry.ANGEL_WINGS)
                .requires(Items.PRISMARINE_SHARD)
                .requires(Items.PRISMARINE_CRYSTALS)
                .unlockedBy("criteria", has(ItemRegistry.ANGEL_WINGS))
                .save(pRecipeOutput,AngelRingMain.MODID+":shapeless/fishron_wings");
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
