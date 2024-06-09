package com.leclowndu93150.flightutils.registry;

import com.leclowndu93150.flightutils.items.AngelRingItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.leclowndu93150.flightutils.FlightUtilsMain.MODID;

public class ItemRegistry {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);

    public static final DeferredItem<Item> ANGEL_RING = ITEMS.register("angel_ring", () -> new AngelRingItem(new Item.Properties()));

    public static final DeferredItem<Item> INERTIA_MODULE = ITEMS.registerSimpleItem("inertia_module");

    public static final DeferredItem<Item> MINING_MODULE = ITEMS.registerSimpleItem("mining_module");

}
