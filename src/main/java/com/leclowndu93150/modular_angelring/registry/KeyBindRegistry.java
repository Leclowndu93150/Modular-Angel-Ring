package com.leclowndu93150.modular_angelring.registry;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.util.Lazy;
import org.lwjgl.glfw.GLFW;

import static com.leclowndu93150.modular_angelring.AngelRingMain.MODID;

@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD)
public class KeyBindRegistry {

    public static final Lazy<KeyMapping> MINING_MODULE = Lazy.of(() ->new KeyMapping(
            "key." + MODID + ".mining_module", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_M, "key.modular_angelring.misc"));

    public static final Lazy<KeyMapping> INERTIA_MODULE = Lazy.of(() ->new KeyMapping(
            "key." + MODID + ".inertia_module", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_L, "key.modular_angelring.misc"));


    @SubscribeEvent
    public static void registerBindings(RegisterKeyMappingsEvent event) {
        event.register(MINING_MODULE.get());
        event.register(INERTIA_MODULE.get());
        NeoForge.EVENT_BUS.addListener(KeyBindRegistry::onKey);
    }

    public static boolean miningEnabled = true;
    public static boolean inertiaEnabled = true;
    public static void onKey(InputEvent.Key event){
        if(MINING_MODULE.get().consumeClick()){
            miningEnabled = !miningEnabled;
        }
        if (INERTIA_MODULE.get().consumeClick()){
            inertiaEnabled = !inertiaEnabled;
        }
    }


}
