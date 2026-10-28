package com.leclowndu93150.modular_angelring.data;

import com.leclowndu93150.modular_angelring.AngelRingMain;
import com.leclowndu93150.modular_angelring.registry.ItemRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import top.theillusivec4.curios.api.CuriosDataProvider;

import java.util.concurrent.CompletableFuture;

public class ModCuriosProvider extends CuriosDataProvider {
    private static final String SLOT = "angel_ring";

    public ModCuriosProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(AngelRingMain.MODID, output, registries);
    }

    @Override
    public void generate(HolderLookup.Provider registries) {
        createSlot(SLOT)
                .size(1)
                .icon(AngelRingMain.id("slot/empty_angel_ring_slot"));
        createEntities(SLOT)
                .addPlayer()
                .addSlots(SLOT);
        tag(SLOT).replace().add(ItemRegistry.ANGEL_RING.get());
    }
}
