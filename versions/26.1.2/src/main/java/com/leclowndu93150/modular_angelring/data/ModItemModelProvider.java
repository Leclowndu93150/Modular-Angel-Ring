package com.leclowndu93150.modular_angelring.data;

import com.leclowndu93150.modular_angelring.AngelRingMain;
import com.leclowndu93150.modular_angelring.api.WingStyle;
import com.leclowndu93150.modular_angelring.registry.DataComponentRegistry;
import com.leclowndu93150.modular_angelring.registry.ItemRegistry;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.renderer.item.SelectItemModel;
import net.minecraft.client.renderer.item.properties.select.ComponentContents;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ModItemModelProvider extends ModelProvider {
    public ModItemModelProvider(PackOutput output) {
        super(output, AngelRingMain.MODID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        for (DeferredHolder<Item, ? extends Item> item : ItemRegistry.ITEMS.getEntries()) {
            if (item != ItemRegistry.ANGEL_RING) {
                itemModels.generateFlatItem(item.get(), ModelTemplates.FLAT_ITEM);
            }
        }

        List<SelectItemModel.SwitchCase<WingStyle>> variants = new ArrayList<>();
        for (WingStyle style : WingStyle.values()) {
            flatModel(itemModels, style.getWornModel(), style.getWornModel());
            if (style != WingStyle.ANGEL) {
                variants.add(ItemModelUtils.when(style, ItemModelUtils.plainModel(flatModel(itemModels, style.getRingModel(), style.getRingModel()))));
            }
        }

        Item ring = ItemRegistry.ANGEL_RING.get();
        Identifier ringModel = flatModel(itemModels, ModelLocationUtils.getModelLocation(ring), WingStyle.ANGEL.getRingModel());
        itemModels.itemModelOutput.accept(ring, ItemModelUtils.select(new ComponentContents<>(DataComponentRegistry.WINGS.get()), ItemModelUtils.plainModel(ringModel), variants));
    }

    private Identifier flatModel(ItemModelGenerators itemModels, Identifier model, Identifier texture) {
        return ModelTemplates.FLAT_ITEM.create(model, TextureMapping.layer0(new Material(texture)), itemModels.modelOutput);
    }

    @Override
    protected Stream<? extends Holder<Block>> getKnownBlocks() {
        return Stream.empty();
    }
}
