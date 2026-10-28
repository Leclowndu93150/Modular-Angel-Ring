package com.leclowndu93150.modular_angelring.recipes;

import com.leclowndu93150.modular_angelring.api.InstalledModules;
import com.leclowndu93150.modular_angelring.api.RingModule;
import com.leclowndu93150.modular_angelring.registry.DataComponentRegistry;
import com.leclowndu93150.modular_angelring.registry.ItemRegistry;
import com.leclowndu93150.modular_angelring.registry.ModuleRegistry;
import com.leclowndu93150.modular_angelring.registry.RecipeRegistry;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmithingRecipeInput;
import net.minecraft.world.item.crafting.SmithingTransformRecipe;
import net.minecraft.world.level.Level;

import java.util.Optional;

public class ModuleInstallRecipe extends SmithingTransformRecipe {
    private static final MapCodec<ModuleInstallRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Recipe.CommonInfo.MAP_CODEC.forGetter(recipe -> recipe.commonInfo),
            Ingredient.CODEC.optionalFieldOf("template").forGetter(ModuleInstallRecipe::templateIngredient),
            Ingredient.CODEC.fieldOf("base").forGetter(ModuleInstallRecipe::baseIngredient),
            Ingredient.CODEC.optionalFieldOf("addition").forGetter(ModuleInstallRecipe::additionIngredient),
            ModuleRegistry.REGISTRY.byNameCodec().fieldOf("module").forGetter(recipe -> recipe.module)
    ).apply(instance, ModuleInstallRecipe::new));

    private static final StreamCodec<RegistryFriendlyByteBuf, ModuleInstallRecipe> STREAM_CODEC = StreamCodec.composite(
            Recipe.CommonInfo.STREAM_CODEC, recipe -> recipe.commonInfo,
            Ingredient.OPTIONAL_CONTENTS_STREAM_CODEC, ModuleInstallRecipe::templateIngredient,
            Ingredient.CONTENTS_STREAM_CODEC, ModuleInstallRecipe::baseIngredient,
            Ingredient.OPTIONAL_CONTENTS_STREAM_CODEC, ModuleInstallRecipe::additionIngredient,
            ByteBufCodecs.registry(ModuleRegistry.KEY), recipe -> recipe.module,
            ModuleInstallRecipe::new
    );

    public static final RecipeSerializer<SmithingTransformRecipe> SERIALIZER = new RecipeSerializer<>(
            MAP_CODEC.xmap(recipe -> recipe, recipe -> (ModuleInstallRecipe) recipe),
            STREAM_CODEC.map(recipe -> recipe, recipe -> (ModuleInstallRecipe) recipe)
    );

    private final RingModule module;

    public ModuleInstallRecipe(Recipe.CommonInfo commonInfo, Optional<Ingredient> template, Ingredient base, Optional<Ingredient> addition, RingModule module) {
        super(commonInfo, template, base, addition, new ItemStackTemplate(ItemRegistry.ANGEL_RING.get(), DataComponentPatch.builder()
                .set(DataComponentRegistry.MODULES.get(), module.installInto(InstalledModules.EMPTY))
                .build()));
        this.module = module;
    }

    private static ItemStack installedOn(ItemStack ring, RingModule module) {
        module.install(ring);
        return ring;
    }

    @Override
    public boolean matches(SmithingRecipeInput input, Level level) {
        return super.matches(input, level) && !InstalledModules.of(input.base()).has(module);
    }

    @Override
    public ItemStack assemble(SmithingRecipeInput input) {
        return installedOn(input.base().copyWithCount(1), module);
    }

    @Override
    public RecipeSerializer<SmithingTransformRecipe> getSerializer() {
        return RecipeRegistry.MODULE_INSTALL.get();
    }
}
