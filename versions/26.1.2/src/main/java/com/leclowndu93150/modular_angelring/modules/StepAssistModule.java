package com.leclowndu93150.modular_angelring.modules;

import com.leclowndu93150.modular_angelring.AngelRingMain;
import com.leclowndu93150.modular_angelring.api.RingModule;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import org.lwjgl.glfw.GLFW;

import java.util.function.BiConsumer;

public class StepAssistModule extends RingModule {
    private static final Identifier STEP_HEIGHT = AngelRingMain.id("step_assist");
    private static final double STEP_BONUS = 0.5;

    public StepAssistModule() {
        super(Properties.toggleable(GLFW.GLFW_KEY_UNKNOWN));
    }

    @Override
    public void addAttributeModifiers(ItemStack ring, BiConsumer<Holder<Attribute>, AttributeModifier> modifiers) {
        modifiers.accept(Attributes.STEP_HEIGHT, new AttributeModifier(STEP_HEIGHT, STEP_BONUS, AttributeModifier.Operation.ADD_VALUE));
    }
}
