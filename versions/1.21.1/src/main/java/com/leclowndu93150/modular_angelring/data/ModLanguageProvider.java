package com.leclowndu93150.modular_angelring.data;

import com.leclowndu93150.modular_angelring.AngelRingMain;
import com.leclowndu93150.modular_angelring.api.RingModule;
import com.leclowndu93150.modular_angelring.registry.ItemRegistry;
import com.leclowndu93150.modular_angelring.registry.ModuleRegistry;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

import java.util.function.Supplier;

public class ModLanguageProvider extends LanguageProvider {
    public ModLanguageProvider(PackOutput output) {
        super(output, AngelRingMain.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add("itemGroup." + AngelRingMain.MODID, "Modular Angel Ring");
        add("key.categories." + AngelRingMain.MODID, "Modular Angel Ring");
        add("curios.identifier.angel_ring", "Angel Ring");
        add("curios.modifiers.angel_ring", "When worn as angel ring:");

        addItem(ItemRegistry.ANGEL_RING, "Angel Ring");
        addItem(ItemRegistry.GOLD_RING, "Gold Ring");
        addItem(ItemRegistry.BLANK_MODULE, "Blank Module");
        addItem(ItemRegistry.UPGRADE_TEMPLATE, "Angel's Smithing Template");

        addItem(ItemRegistry.MINING_MODULE, "Mining Module");
        addItem(ItemRegistry.INERTIA_MODULE, "Inertia Module");
        addItem(ItemRegistry.SPEED_MODULE, "Speed Module");
        addItem(ItemRegistry.NIGHT_VISION_MODULE, "Night Vision Module");
        addItem(ItemRegistry.MAGNET_MODULE, "Magnet Module");
        addItem(ItemRegistry.REACH_MODULE, "Reach Module");
        addItem(ItemRegistry.STEP_ASSIST_MODULE, "Step Assist Module");
        addItem(ItemRegistry.PHANTOM_WARD_MODULE, "Phantom Ward Module");
        addItem(ItemRegistry.VOID_GUARDIAN_MODULE, "Void Guardian Module");
        addItem(ItemRegistry.AQUA_LUNG_MODULE, "Aqua Lung Module");
        addItem(ItemRegistry.FAST_BUILD_MODULE, "Fast Build Module");

        addItem(ItemRegistry.ANGEL_WINGS, "Angel's Wings");
        addItem(ItemRegistry.BAT_WINGS, "Bat Wings");
        addItem(ItemRegistry.BIG_DRAGON_WINGS, "Big Dragon Wings");
        addItem(ItemRegistry.BLUE_DRAGON_WINGS, "Blue Dragon Wings");
        addItem(ItemRegistry.BUTTERFLY_WINGS, "Butterfly Wings");
        addItem(ItemRegistry.DRAGON_WINGS, "Dragon Wings");
        addItem(ItemRegistry.GOLD_WINGS, "Gold Wings");
        addItem(ItemRegistry.GOLDEN_WINGS, "Golden Wings");

        addModule(ModuleRegistry.MINING, "Mining Module", "Allows you to mine as fast as ground speed");
        addModule(ModuleRegistry.INERTIA, "Inertia Module", "Allows you to cancel flying inertia when stopping");
        addModule(ModuleRegistry.SPEED, "Speed Module", "Allows you to configure your flight speed. (Range: 0%% - %s%%)");
        addModule(ModuleRegistry.NIGHT_VISION, "Night Vision Module", "Gives Night Vision");
        addModule(ModuleRegistry.MAGNET, "Magnet Module", "Attracts items and experience that are far away.");
        addModule(ModuleRegistry.REACH, "Reach Module", "Lets you reach blocks and entities further away");
        addModule(ModuleRegistry.STEP_ASSIST, "Step Assist Module", "Lets you walk up full blocks");
        addModule(ModuleRegistry.PHANTOM_WARD, "Phantom Ward Module", "Phantoms no longer spawn for you, as if you had slept");
        addModule(ModuleRegistry.VOID_GUARDIAN, "Void Guardian Module", "Catches you and lifts you back up if you fall out of the world");
        addModule(ModuleRegistry.AQUA_LUNG, "Aqua Lung Module", "Lets you breathe and mine at full speed underwater");
        addModule(ModuleRegistry.FAST_BUILD, "Fast Build Module", "Removes the delay between placing blocks");

        String speed = ModuleRegistry.SPEED.get().getDescriptionId();
        add(speed + ".more_info", "Shift for Info:");
        add(speed + ".increase", "Right-Click: +10%% Speed");
        add(speed + ".decrease", "Shift + Right-Click: -10%% Speed");

        add("text." + AngelRingMain.MODID + ".module_state", "%s: %s");
        add("text." + AngelRingMain.MODID + ".enabled", "Enabled");
        add("text." + AngelRingMain.MODID + ".disabled", "Disabled");
        add("text." + AngelRingMain.MODID + ".speed", "%s: %s%%");
        add("text." + AngelRingMain.MODID + ".speed_changed", "Speed: %s%%");
        add("text." + AngelRingMain.MODID + ".magnet_radius", "%s (Radius: %s blocks)");
    }

    private void addModule(Supplier<? extends RingModule> holder, String name, String description) {
        RingModule module = holder.get();
        add(module.getDescriptionId(), name);
        add(module.getDescriptionId() + ".desc", description);
        if (module.isToggleable()) {
            add(module.getId().toLanguageKey("key"), name);
        }
    }
}
