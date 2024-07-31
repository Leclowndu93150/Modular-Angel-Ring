package com.leclowndu93150.modular_angelring.registry;

import com.leclowndu93150.modular_angelring.common.*;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.leclowndu93150.modular_angelring.AngelRingMain.MODID;

public class ItemRegistry {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);

    public static final DeferredItem<Item> ANGEL_RING = ITEMS.register("angel_ring", () -> new AngelRingItem(new Item.Properties()
            .component(DataComponentRegistry.INERTIA_MODIFIER, false)
            .component(DataComponentRegistry.MINING_MODIFIER, false)
            .component(DataComponentRegistry.MODIFIERS_ENABLED, EnabledModifiersComponent.EMPTY)));

    public static final DeferredItem<Item> INERTIA_MODULE = ITEMS.register("inertia_module", () -> new  InertiaModuleItem(new Item.Properties()));
    public static final DeferredItem<Item> MINING_MODULE = ITEMS.register("mining_module",() -> new MiningModuleItem(new Item.Properties()));
    public static final DeferredItem<Item> SPEED_MODULE = ITEMS.register("speed_module",() -> new SpeedModuleItem(new Item.Properties()));
    public static final DeferredItem<Item> NIGHT_VISION_MODULE = ITEMS.register("night_vision_module",() -> new NightVisionModuleItem(new Item.Properties()));

    public static final DeferredItem<Item> UPGRADE_TEMPLATE = ITEMS.registerSimpleItem("angel_ring_upgrade_smithing_template");

    public static final DeferredItem<Item> GOLD_RING = ITEMS.registerSimpleItem("gold_ring");

    public static final DeferredItem<Item> BLANK_MODULE = ITEMS.registerSimpleItem("blank_module");

    public static final DeferredItem<Item> ANGEL_WINGS = ITEMS.registerSimpleItem("angel_wings");
    public static final DeferredItem<Item> BLUE_DRAGON_WINGS = ITEMS.registerSimpleItem("blue_dragon_wings");
    public static final DeferredItem<Item> BIG_DRAGON_WINGS = ITEMS.registerSimpleItem("big_dragon_wings");
    public static final DeferredItem<Item> DRAGON_WINGS = ITEMS.registerSimpleItem("dragon_wings");
    public static final DeferredItem<Item> BUTTERFLY_WINGS = ITEMS.registerSimpleItem("butterfly_wings");
    public static final DeferredItem<Item> BAT_WINGS = ITEMS.registerSimpleItem("bat_wings");
    public static final DeferredItem<Item> GOLDEN_WINGS = ITEMS.registerSimpleItem("golden_wings");
    public static final DeferredItem<Item> GOLD_WINGS = ITEMS.registerSimpleItem("gold_wings");

    public static final DeferredItem<Item> ANGEL_WINGS_BOTH = ITEMS.registerSimpleItem("angel_wings_both");
    public static final DeferredItem<Item> BIG_DRAGON_WINGS_BOTH = ITEMS.registerSimpleItem("big_dragon_wings_both");
    public static final DeferredItem<Item> DRAGON_WINGS_BOTH = ITEMS.registerSimpleItem("dragon_wings_both");
    public static final DeferredItem<Item> BUTTERFLY_WINGS_BOTH = ITEMS.registerSimpleItem("butterfly_wings_both");
    public static final DeferredItem<Item> BAT_WINGS_BOTH = ITEMS.registerSimpleItem("bat_wings_both");
    public static final DeferredItem<Item> GOLDEN_WINGS_BOTH = ITEMS.registerSimpleItem("golden_wings_both");
    public static final DeferredItem<Item> GOLD_WINGS_BOTH = ITEMS.registerSimpleItem("gold_wings_both");
    public static final DeferredItem<Item> BLUE_DRAGON_WINGS_BOTH = ITEMS.registerSimpleItem("blue_dragon_wings_both");

}
