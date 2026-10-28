package com.leclowndu93150.modular_angelring.data;

import com.leclowndu93150.modular_angelring.AngelRingMain;
import com.leclowndu93150.modular_angelring.api.WingStyle;
import com.leclowndu93150.modular_angelring.item.ModuleItem;
import com.leclowndu93150.modular_angelring.registry.ItemRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SmithingTransformRecipeBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {
    protected ModRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    @Override
    protected void buildRecipes() {
        shapeless(RecipeCategory.MISC, ItemRegistry.ANGEL_RING)
                .requires(ItemRegistry.ANGEL_WINGS)
                .requires(ItemRegistry.GOLD_RING)
                .unlockedBy(getHasName(ItemRegistry.GOLD_RING), has(ItemRegistry.GOLD_RING))
                .save(output);

        shaped(RecipeCategory.MISC, ItemRegistry.GOLD_RING)
                .pattern(" G ")
                .pattern("GEG")
                .pattern(" G ")
                .define('G', Items.GOLD_INGOT)
                .define('E', Items.ELYTRA)
                .unlockedBy(getHasName(Items.ELYTRA), has(Items.ELYTRA))
                .save(output);

        shaped(RecipeCategory.MISC, ItemRegistry.ANGEL_WINGS)
                .pattern("SWS")
                .pattern("W W")
                .pattern("W W")
                .define('S', Items.STRING)
                .define('W', Items.FEATHER)
                .unlockedBy(getHasName(Items.FEATHER), has(Items.FEATHER))
                .save(output);

        shaped(RecipeCategory.MISC, ItemRegistry.UPGRADE_TEMPLATE)
                .pattern("GFG")
                .pattern("GIG")
                .pattern("GGG")
                .define('G', Items.GOLD_INGOT)
                .define('F', Items.FEATHER)
                .define('I', Items.IRON_BLOCK)
                .unlockedBy(getHasName(Items.IRON_BLOCK), has(Items.IRON_BLOCK))
                .save(output);

        shaped(RecipeCategory.MISC, ItemRegistry.BLANK_MODULE)
                .pattern("GGG")
                .pattern("GBG")
                .pattern("GGG")
                .define('G', Items.GOLD_INGOT)
                .define('B', Items.GOLD_BLOCK)
                .unlockedBy(getHasName(Items.GOLD_BLOCK), has(Items.GOLD_BLOCK))
                .save(output);

        module(ItemRegistry.MINING_MODULE, Ingredient.of(Items.DIAMOND_PICKAXE));
        module(ItemRegistry.INERTIA_MODULE, Ingredient.of(Items.PHANTOM_MEMBRANE));
        module(ItemRegistry.SPEED_MODULE, Ingredient.of(Items.SUGAR));
        module(ItemRegistry.NIGHT_VISION_MODULE, Ingredient.of(Items.GOLDEN_CARROT));
        module(ItemRegistry.MAGNET_MODULE, Ingredient.of(Items.ENDER_EYE));
        module(ItemRegistry.REACH_MODULE, Ingredient.of(Items.ENDER_PEARL));
        module(ItemRegistry.STEP_ASSIST_MODULE, Ingredient.of(Items.RABBIT_FOOT));
        module(ItemRegistry.PHANTOM_WARD_MODULE, tag(ItemTags.BEDS));
        module(ItemRegistry.VOID_GUARDIAN_MODULE, Ingredient.of(Items.SHULKER_SHELL));
        module(ItemRegistry.AQUA_LUNG_MODULE, Ingredient.of(Items.TURTLE_SCUTE));
        module(ItemRegistry.FAST_BUILD_MODULE, Ingredient.of(Items.BRICK));

        wings(WingStyle.BAT, Items.COCOA_BEANS, Items.PHANTOM_MEMBRANE);
        wings(WingStyle.BIG_DRAGON, Items.DRAGON_HEAD, Items.ENDER_EYE);
        wings(WingStyle.BLUE_DRAGON, Items.DRAGON_HEAD, Items.LIGHT_BLUE_DYE);
        wings(WingStyle.BUTTERFLY, Items.GLOWSTONE_DUST, Items.PURPLE_DYE);
        wings(WingStyle.DRAGON, Items.DRAGON_HEAD, Items.RED_DYE, Items.ORANGE_DYE);
        wings(WingStyle.GOLD, Items.GOLD_INGOT);
        wings(WingStyle.GOLDEN, Items.GOLD_INGOT, Items.DIAMOND);
    }

    private static Ingredient template() {
        return Ingredient.of(ItemRegistry.UPGRADE_TEMPLATE);
    }

    private void module(DeferredItem<ModuleItem> moduleItem, Ingredient addition) {
        SmithingTransformRecipeBuilder.smithing(template(), Ingredient.of(ItemRegistry.BLANK_MODULE), addition, RecipeCategory.MISC, moduleItem.get())
                .unlocks(getHasName(ItemRegistry.ANGEL_RING), has(ItemRegistry.ANGEL_RING))
                .save(output, ResourceKey.create(Registries.RECIPE, AngelRingMain.id("smithing/" + moduleItem.getId().getPath())));

        ModuleItem item = moduleItem.get();
        RingSmithingRecipeBuilder.installModule(template(), Ingredient.of(ItemRegistry.ANGEL_RING), Ingredient.of(item), item.getModule())
                .unlockedBy(getHasName(ItemRegistry.ANGEL_RING), has(ItemRegistry.ANGEL_RING))
                .save(output, AngelRingMain.id("smithing/" + item.getModule().getId().getPath() + "_modifier"));
    }

    private void wings(WingStyle style, ItemLike... ingredients) {
        String name = style.getSerializedName();
        ShapelessRecipeBuilder craft = shapeless(RecipeCategory.MISC, style.getWingsItem())
                .requires(ItemRegistry.ANGEL_WINGS);
        for (ItemLike ingredient : ingredients) {
            craft.requires(ingredient);
        }
        craft.unlockedBy(getHasName(ItemRegistry.ANGEL_WINGS), has(ItemRegistry.ANGEL_WINGS))
                .save(output, ResourceKey.create(Registries.RECIPE, AngelRingMain.id("shapeless/" + name + "_wings")));

        RingSmithingRecipeBuilder.applyWings(template(), Ingredient.of(ItemRegistry.ANGEL_RING), Ingredient.of(style.getWingsItem()), style)
                .unlockedBy(getHasName(ItemRegistry.ANGEL_RING), has(ItemRegistry.ANGEL_RING))
                .save(output, AngelRingMain.id("smithing/" + name + "_ring"));
    }

    public static class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
            super(output, registries);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
            return new ModRecipeProvider(registries, output);
        }

        @Override
        public String getName() {
            return "Recipes";
        }
    }
}
