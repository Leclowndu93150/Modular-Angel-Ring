package com.leclowndu93150.modular_angelring.data;

import com.leclowndu93150.modular_angelring.AngelRingMain;
import com.leclowndu93150.modular_angelring.api.WingStyle;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.renderer.item.ClientItem;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.JsonCodecProvider;

import java.util.concurrent.CompletableFuture;

public class WornWingsItemProvider extends JsonCodecProvider<ClientItem> {
    public WornWingsItemProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, PackOutput.Target.RESOURCE_PACK, "items", ClientItem.CODEC, registries, AngelRingMain.MODID);
    }

    @Override
    protected void gather() {
        for (WingStyle style : WingStyle.values()) {
            unconditional(style.getWornItemModel(), new ClientItem(ItemModelUtils.plainModel(style.getWornModel()), ClientItem.Properties.DEFAULT));
        }
    }
}
