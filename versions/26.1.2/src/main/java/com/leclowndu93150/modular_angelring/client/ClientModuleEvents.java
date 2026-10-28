package com.leclowndu93150.modular_angelring.client;

import com.leclowndu93150.modular_angelring.AngelRingMain;
import com.leclowndu93150.modular_angelring.api.InstalledModules;
import com.leclowndu93150.modular_angelring.item.AngelRingItem;
import com.leclowndu93150.modular_angelring.item.ModuleItem;
import com.leclowndu93150.modular_angelring.registry.ModuleRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Input;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.MovementInputUpdateEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid = AngelRingMain.MODID, value = Dist.CLIENT)
public final class ClientModuleEvents {
    private ClientModuleEvents() {
    }

    @SubscribeEvent
    public static void onMovementInput(MovementInputUpdateEvent event) {
        Input keys = event.getInput().keyPresses;
        if (!keys.forward() && !keys.backward() && !keys.left() && !keys.right()) {
            ModuleRegistry.INERTIA.get().stopDrift(event.getEntity());
        }
    }

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        List<Component> lines = new ArrayList<>();
        if (stack.getItem() instanceof ModuleItem item) {
            lines.addAll(item.getModule().getItemTooltip(Minecraft.getInstance().hasShiftDown()));
        } else if (stack.getItem() instanceof AngelRingItem) {
            InstalledModules.of(stack).installed().forEach(module -> module.appendRingTooltip(stack, lines));
        }
        event.getToolTip().addAll(1, lines);
    }
}
