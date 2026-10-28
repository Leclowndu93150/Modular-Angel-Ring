package com.leclowndu93150.modular_angelring.data;

import com.leclowndu93150.modular_angelring.AngelRingMain;
import com.leclowndu93150.modular_angelring.registry.ItemRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends ItemTagsProvider {
    private static final TagKey<Item> CURIOS_ANGEL_RING = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(CuriosApi.MODID, "angel_ring"));

    public ModItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFiles) {
        super(output, registries, CompletableFuture.completedFuture(TagsProvider.TagLookup.empty()), AngelRingMain.MODID, existingFiles);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        tag(CURIOS_ANGEL_RING).replace().add(ItemRegistry.ANGEL_RING.get());
    }
}
