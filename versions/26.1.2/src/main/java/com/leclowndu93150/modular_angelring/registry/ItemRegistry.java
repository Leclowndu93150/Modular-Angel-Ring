package com.leclowndu93150.modular_angelring.registry;

import com.leclowndu93150.modular_angelring.AngelRingMain;
import com.leclowndu93150.modular_angelring.api.RingModule;
import com.leclowndu93150.modular_angelring.item.AngelRingItem;
import com.leclowndu93150.modular_angelring.item.ModuleItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ItemRegistry {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(AngelRingMain.MODID);

    public static final DeferredItem<AngelRingItem> ANGEL_RING = ITEMS.registerItem("angel_ring", AngelRingItem::new, properties -> properties.stacksTo(1));

    public static final DeferredItem<ModuleItem> MINING_MODULE = registerModule("mining_module", ModuleRegistry.MINING);
    public static final DeferredItem<ModuleItem> INERTIA_MODULE = registerModule("inertia_module", ModuleRegistry.INERTIA);
    public static final DeferredItem<ModuleItem> SPEED_MODULE = registerModule("speed_module", ModuleRegistry.SPEED);
    public static final DeferredItem<ModuleItem> NIGHT_VISION_MODULE = registerModule("night_vision_module", ModuleRegistry.NIGHT_VISION);
    public static final DeferredItem<ModuleItem> MAGNET_MODULE = registerModule("magnet_module", ModuleRegistry.MAGNET);
    public static final DeferredItem<ModuleItem> REACH_MODULE = registerModule("reach_module", ModuleRegistry.REACH);
    public static final DeferredItem<ModuleItem> STEP_ASSIST_MODULE = registerModule("step_assist_module", ModuleRegistry.STEP_ASSIST);
    public static final DeferredItem<ModuleItem> PHANTOM_WARD_MODULE = registerModule("phantom_ward_module", ModuleRegistry.PHANTOM_WARD);
    public static final DeferredItem<ModuleItem> VOID_GUARDIAN_MODULE = registerModule("void_guardian_module", ModuleRegistry.VOID_GUARDIAN);
    public static final DeferredItem<ModuleItem> AQUA_LUNG_MODULE = registerModule("aqua_lung_module", ModuleRegistry.AQUA_LUNG);
    public static final DeferredItem<ModuleItem> FAST_BUILD_MODULE = registerModule("fast_build_module", ModuleRegistry.FAST_BUILD);

    public static final DeferredItem<Item> UPGRADE_TEMPLATE = ITEMS.registerSimpleItem("angel_ring_upgrade_smithing_template");
    public static final DeferredItem<Item> GOLD_RING = ITEMS.registerSimpleItem("gold_ring");
    public static final DeferredItem<Item> BLANK_MODULE = ITEMS.registerSimpleItem("blank_module");

    public static final DeferredItem<Item> ANGEL_WINGS = ITEMS.registerSimpleItem("angel_wings");
    public static final DeferredItem<Item> BAT_WINGS = ITEMS.registerSimpleItem("bat_wings");
    public static final DeferredItem<Item> BIG_DRAGON_WINGS = ITEMS.registerSimpleItem("big_dragon_wings");
    public static final DeferredItem<Item> BLUE_DRAGON_WINGS = ITEMS.registerSimpleItem("blue_dragon_wings");
    public static final DeferredItem<Item> BUTTERFLY_WINGS = ITEMS.registerSimpleItem("butterfly_wings");
    public static final DeferredItem<Item> DRAGON_WINGS = ITEMS.registerSimpleItem("dragon_wings");
    public static final DeferredItem<Item> GOLD_WINGS = ITEMS.registerSimpleItem("gold_wings");
    public static final DeferredItem<Item> GOLDEN_WINGS = ITEMS.registerSimpleItem("golden_wings");

    private ItemRegistry() {
    }

    private static DeferredItem<ModuleItem> registerModule(String name, DeferredHolder<RingModule, ? extends RingModule> module) {
        return ITEMS.registerItem(name, properties -> new ModuleItem(module, properties), properties -> properties.stacksTo(1));
    }
}
