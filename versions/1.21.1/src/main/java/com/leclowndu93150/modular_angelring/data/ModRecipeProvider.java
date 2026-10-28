package com.leclowndu93150.modular_angelring.data;

import com.leclowndu93150.modular_angelring.AngelRingMain;
import com.leclowndu93150.modular_angelring.api.WingStyle;
import com.leclowndu93150.modular_angelring.item.ModuleItem;
import com.leclowndu93150.modular_angelring.registry.ItemRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SmithingTransformRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.ANGEL_RING)
                .requires(ItemRegistry.ANGEL_WINGS)
                .requires(ItemRegistry.GOLD_RING)
                .unlockedBy(getHasName(ItemRegistry.GOLD_RING), has(ItemRegistry.GOLD_RING))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.GOLD_RING)
                .pattern(" G ")
                .pattern("GEG")
                .pattern(" G ")
                .define('G', Items.GOLD_INGOT)
                .define('E', Items.ELYTRA)
                .unlockedBy(getHasName(Items.ELYTRA), has(Items.ELYTRA))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.ANGEL_WINGS)
                .pattern("SWS")
                .pattern("W W")
                .pattern("W W")
                .define('S', Items.STRING)
                .define('W', Items.FEATHER)
                .unlockedBy(getHasName(Items.FEATHER), has(Items.FEATHER))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.UPGRADE_TEMPLATE)
                .pattern("GFG")
                .pattern("GIG")
                .pattern("GGG")
                .define('G', Items.GOLD_INGOT)
                .define('F', Items.FEATHER)
                .define('I', Items.IRON_BLOCK)
                .unlockedBy(getHasName(Items.IRON_BLOCK), has(Items.IRON_BLOCK))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.BLANK_MODULE)
                .pattern("GGG")
                .pattern("GBG")
                .pattern("GGG")
                .define('G', Items.GOLD_INGOT)
                .define('B', Items.GOLD_BLOCK)
                .unlockedBy(getHasName(Items.GOLD_BLOCK), has(Items.GOLD_BLOCK))
                .save(output);

        module(output, ItemRegistry.MINING_MODULE, Ingredient.of(Items.DIAMOND_PICKAXE));
        module(output, ItemRegistry.INERTIA_MODULE, Ingredient.of(Items.PHANTOM_MEMBRANE));
        module(output, ItemRegistry.SPEED_MODULE, Ingredient.of(Items.SUGAR));
        module(output, ItemRegistry.NIGHT_VISION_MODULE, Ingredient.of(Items.GOLDEN_CARROT));
        module(output, ItemRegistry.MAGNET_MODULE, Ingredient.of(Items.ENDER_EYE));
        module(output, ItemRegistry.REACH_MODULE, Ingredient.of(Items.ENDER_PEARL));
        module(output, ItemRegistry.STEP_ASSIST_MODULE, Ingredient.of(Items.RABBIT_FOOT));
        module(output, ItemRegistry.PHANTOM_WARD_MODULE, Ingredient.of(ItemTags.BEDS));
        module(output, ItemRegistry.VOID_GUARDIAN_MODULE, Ingredient.of(Items.SHULKER_SHELL));
        module(output, ItemRegistry.AQUA_LUNG_MODULE, Ingredient.of(Items.TURTLE_SCUTE));
        module(output, ItemRegistry.FAST_BUILD_MODULE, Ingredient.of(Items.BRICK));

        wings(output, WingStyle.BAT, Items.COCOA_BEANS, Items.PHANTOM_MEMBRANE);
        wings(output, WingStyle.BIG_DRAGON, Items.DRAGON_HEAD, Items.ENDER_EYE);
        wings(output, WingStyle.BLUE_DRAGON, Items.DRAGON_HEAD, Items.LIGHT_BLUE_DYE);
        wings(output, WingStyle.BUTTERFLY, Items.GLOWSTONE_DUST, Items.PURPLE_DYE);
        wings(output, WingStyle.DRAGON, Items.DRAGON_HEAD, Items.RED_DYE, Items.ORANGE_DYE);
        wings(output, WingStyle.GOLD, Items.GOLD_INGOT);
        wings(output, WingStyle.GOLDEN, Items.GOLD_INGOT, Items.DIAMOND);
    }

    private static Ingredient template() {
        return Ingredient.of(ItemRegistry.UPGRADE_TEMPLATE);
    }

    private void module(RecipeOutput output, DeferredItem<ModuleItem> moduleItem, Ingredient addition) {
        SmithingTransformRecipeBuilder.smithing(template(), Ingredient.of(ItemRegistry.BLANK_MODULE), addition, RecipeCategory.MISC, moduleItem.get())
                .unlocks(getHasName(ItemRegistry.ANGEL_RING), has(ItemRegistry.ANGEL_RING))
                .save(output, AngelRingMain.id("smithing/" + moduleItem.getId().getPath()));

        ModuleItem item = moduleItem.get();
        RingSmithingRecipeBuilder.installModule(template(), Ingredient.of(ItemRegistry.ANGEL_RING), Ingredient.of(item), item.getModule())
                .unlockedBy(getHasName(ItemRegistry.ANGEL_RING), has(ItemRegistry.ANGEL_RING))
                .save(output, AngelRingMain.id("smithing/" + item.getModule().getId().getPath() + "_modifier"));
    }

    private void wings(RecipeOutput output, WingStyle style, ItemLike... ingredients) {
        String name = style.getSerializedName();
        ShapelessRecipeBuilder craft = ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, style.getWingsItem())
                .requires(ItemRegistry.ANGEL_WINGS);
        for (ItemLike ingredient : ingredients) {
            craft.requires(ingredient);
        }
        craft.unlockedBy(getHasName(ItemRegistry.ANGEL_WINGS), has(ItemRegistry.ANGEL_WINGS))
                .save(output, AngelRingMain.id("shapeless/" + name + "_wings"));

        RingSmithingRecipeBuilder.applyWings(template(), Ingredient.of(ItemRegistry.ANGEL_RING), Ingredient.of(style.getWingsItem()), style)
                .unlockedBy(getHasName(ItemRegistry.ANGEL_RING), has(ItemRegistry.ANGEL_RING))
                .save(output, AngelRingMain.id("smithing/" + name + "_ring"));
    }
}
