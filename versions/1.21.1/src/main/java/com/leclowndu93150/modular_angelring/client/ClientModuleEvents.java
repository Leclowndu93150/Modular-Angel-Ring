package com.leclowndu93150.modular_angelring.client;

import com.leclowndu93150.modular_angelring.AngelRingMain;
import com.leclowndu93150.modular_angelring.item.ModuleItem;
import com.leclowndu93150.modular_angelring.registry.ModuleRegistry;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.Input;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.MovementInputUpdateEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

@EventBusSubscriber(modid = AngelRingMain.MODID, value = Dist.CLIENT)
public final class ClientModuleEvents {
    private ClientModuleEvents() {
    }

    @SubscribeEvent
    public static void onMovementInput(MovementInputUpdateEvent event) {
        Input input = event.getInput();
        if (!input.up && !input.down && !input.left && !input.right) {
            ModuleRegistry.INERTIA.get().stopDrift(event.getEntity());
        }
    }

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        if (event.getItemStack().getItem() instanceof ModuleItem item) {
            event.getToolTip().addAll(1, item.getModule().getItemTooltip(Screen.hasShiftDown()));
        }
    }
}
