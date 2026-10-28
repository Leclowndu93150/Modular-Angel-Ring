package com.leclowndu93150.modular_angelring.recipes;

import com.leclowndu93150.modular_angelring.api.InstalledModules;
import com.leclowndu93150.modular_angelring.api.RingModule;
import com.leclowndu93150.modular_angelring.registry.ItemRegistry;
import com.leclowndu93150.modular_angelring.registry.ModuleRegistry;
import com.leclowndu93150.modular_angelring.registry.RecipeRegistry;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmithingRecipeInput;
import net.minecraft.world.item.crafting.SmithingTransformRecipe;
import net.minecraft.world.level.Level;

public class ModuleInstallRecipe extends SmithingTransformRecipe {
    private final Ingredient template;
    private final Ingredient base;
    private final Ingredient addition;
    private final RingModule module;

    public ModuleInstallRecipe(Ingredient template, Ingredient base, Ingredient addition, RingModule module) {
        super(template, base, addition, installedOn(ItemRegistry.ANGEL_RING.toStack(), module));
        this.template = template;
        this.base = base;
        this.addition = addition;
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
    public ItemStack assemble(SmithingRecipeInput input, HolderLookup.Provider registries) {
        return installedOn(input.base().copyWithCount(1), module);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeRegistry.MODULE_INSTALL.get();
    }

    public static class Serializer implements RecipeSerializer<ModuleInstallRecipe> {
        private static final MapCodec<ModuleInstallRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Ingredient.CODEC.fieldOf("template").forGetter(recipe -> recipe.template),
                Ingredient.CODEC.fieldOf("base").forGetter(recipe -> recipe.base),
                Ingredient.CODEC.fieldOf("addition").forGetter(recipe -> recipe.addition),
                ModuleRegistry.REGISTRY.byNameCodec().fieldOf("module").forGetter(recipe -> recipe.module)
        ).apply(instance, ModuleInstallRecipe::new));

        private static final StreamCodec<RegistryFriendlyByteBuf, ModuleInstallRecipe> STREAM_CODEC = StreamCodec.composite(
                Ingredient.CONTENTS_STREAM_CODEC, recipe -> recipe.template,
                Ingredient.CONTENTS_STREAM_CODEC, recipe -> recipe.base,
                Ingredient.CONTENTS_STREAM_CODEC, recipe -> recipe.addition,
                ByteBufCodecs.registry(ModuleRegistry.KEY), recipe -> recipe.module,
                ModuleInstallRecipe::new
        );

        @Override
        public MapCodec<ModuleInstallRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, ModuleInstallRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
