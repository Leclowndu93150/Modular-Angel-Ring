package com.leclowndu93150.modular_angelring.modules;

import com.leclowndu93150.modular_angelring.api.RingModule;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import org.lwjgl.glfw.GLFW;

public class NightVisionModule extends RingModule {
    public NightVisionModule() {
        super(Properties.toggleable(GLFW.GLFW_KEY_N));
    }

    public boolean grants(LivingEntity entity, Holder<MobEffect> effect) {
        return effect.value() == MobEffects.NIGHT_VISION.value() && isActive(entity);
    }
}
