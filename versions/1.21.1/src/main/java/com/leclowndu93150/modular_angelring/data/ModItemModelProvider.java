package com.leclowndu93150.modular_angelring.data;

import com.leclowndu93150.modular_angelring.AngelRingMain;
import com.leclowndu93150.modular_angelring.api.WingStyle;
import com.leclowndu93150.modular_angelring.registry.ItemRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFiles) {
        super(output, AngelRingMain.MODID, existingFiles);
    }

    @Override
    protected void registerModels() {
        for (DeferredHolder<Item, ? extends Item> item : ItemRegistry.ITEMS.getEntries()) {
            if (item != ItemRegistry.ANGEL_RING) {
                basicItem(item.get());
            }
        }

        ItemModelBuilder ring = generated(ItemRegistry.ANGEL_RING.getId().withPrefix("item/"), WingStyle.ANGEL.getRingModel());
        for (WingStyle style : WingStyle.values()) {
            generated(style.getWornModel(), style.getWornModel());
            if (style != WingStyle.ANGEL) {
                ring.override()
                        .predicate(WingStyle.MODEL_PROPERTY, style.ordinal())
                        .model(generated(style.getRingModel(), style.getRingModel()))
                        .end();
            }
        }
    }

    private ItemModelBuilder generated(ResourceLocation model, ResourceLocation texture) {
        return withExistingParent(model.toString(), "item/generated").texture("layer0", texture);
    }
}
