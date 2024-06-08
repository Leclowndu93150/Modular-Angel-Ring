package com.leclowndu93150.flightutils.registry;

import com.leclowndu93150.flightutils.items.AngelRingItem;
import com.leclowndu93150.flightutils.items.IntertiaRingItem;
import com.leclowndu93150.flightutils.items.MiningRingItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.leclowndu93150.flightutils.FlightUtilsMain.MODID;

public class ItemRegistry {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);

    public static final DeferredItem<Item> ANGEL_RING = ITEMS.register("angel_ring", (ResourceLocation pProperties) -> new AngelRingItem());

    public static final DeferredItem<Item> INERTIA_RING = ITEMS.register("inertia_ring", (ResourceLocation pProperties) -> new IntertiaRingItem());

    public static final DeferredItem<Item> MINING_RING = ITEMS.register("mining_ring", (ResourceLocation pProperties) -> new MiningRingItem());

}
