package com.leclowndu93150.modular_angelring.data;

import com.leclowndu93150.modular_angelring.AngelRingMain;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import top.theillusivec4.curios.api.CuriosDataProvider;

import java.util.concurrent.CompletableFuture;

public class ModCuriosProvider extends CuriosDataProvider {
    private static final String SLOT = "angel_ring";

    public ModCuriosProvider(PackOutput output, ExistingFileHelper existingFiles, CompletableFuture<HolderLookup.Provider> registries) {
        super(AngelRingMain.MODID, output, existingFiles, registries);
    }

    @Override
    public void generate(HolderLookup.Provider registries, ExistingFileHelper existingFiles) {
        createSlot(SLOT)
                .size(1)
                .icon(AngelRingMain.id("slot/empty_angel_ring_slot"));
        createEntities(SLOT)
                .addPlayer()
                .addSlots(SLOT);
    }
}
