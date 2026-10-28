package com.leclowndu93150.modular_angelring.item;

import com.leclowndu93150.modular_angelring.api.RingModule;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class ModuleItem extends Item {
    private final Supplier<? extends RingModule> module;

    public ModuleItem(Supplier<? extends RingModule> module, Properties properties) {
        super(properties);
        this.module = module;
    }

    public RingModule getModule() {
        return module.get();
    }
}
