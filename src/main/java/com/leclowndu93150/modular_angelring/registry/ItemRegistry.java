package com.leclowndu93150.modular_angelring.registry;

import com.leclowndu93150.modular_angelring.common.AngelRingItem;
import com.leclowndu93150.modular_angelring.common.InertiaModuleItem;
import com.leclowndu93150.modular_angelring.common.MiningModuleItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.leclowndu93150.modular_angelring.AngelRingMain.MODID;

public class ItemRegistry {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);

    public static final DeferredItem<Item> ANGEL_RING = ITEMS.register("angel_ring", () -> new AngelRingItem(new Item.Properties().component(DataComponentRegistry.INERTIA_MODIFIER, false).component(DataComponentRegistry.MINING_MODIFIER, false)));

    public static final DeferredItem<Item> INERTIA_MODULE = ITEMS.register("inertia_module", () -> new  InertiaModuleItem(new Item.Properties()));

    public static final DeferredItem<Item> MINING_MODULE = ITEMS.register("mining_module",() -> new MiningModuleItem(new Item.Properties()));

    public static final DeferredItem<Item> SPEED_MODULE = ITEMS.registerSimpleItem("speed_module");

    public static final DeferredItem<Item> GOLD_RING = ITEMS.registerSimpleItem("gold_ring");

    public static final DeferredItem<Item> BLANK_MODULE = ITEMS.registerSimpleItem("blank_module");

    public static final DeferredItem<Item> ANGEL_WINGS = ITEMS.registerSimpleItem("angel_wings");
    public static final DeferredItem<Item> ANGEL_WINGS_BOTH = ITEMS.registerSimpleItem("angel_wings_both");
}
