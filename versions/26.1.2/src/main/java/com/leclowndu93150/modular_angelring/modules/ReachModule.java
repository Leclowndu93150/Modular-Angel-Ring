package com.leclowndu93150.modular_angelring.modules;

import com.leclowndu93150.modular_angelring.AngelRingConfig;
import com.leclowndu93150.modular_angelring.AngelRingMain;
import com.leclowndu93150.modular_angelring.api.RingModule;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;

import java.util.function.BiConsumer;

public class ReachModule extends RingModule {
    private static final Identifier BLOCK_REACH = AngelRingMain.id("reach_block_range");
    private static final Identifier ENTITY_REACH = AngelRingMain.id("reach_entity_range");

    public ReachModule() {
        super(Properties.passive());
    }

    @Override
    public void addAttributeModifiers(ItemStack ring, BiConsumer<Holder<Attribute>, AttributeModifier> modifiers) {
        double bonus = AngelRingConfig.REACH_BONUS.get();
        modifiers.accept(Attributes.BLOCK_INTERACTION_RANGE, new AttributeModifier(BLOCK_REACH, bonus, AttributeModifier.Operation.ADD_VALUE));
        modifiers.accept(Attributes.ENTITY_INTERACTION_RANGE, new AttributeModifier(ENTITY_REACH, bonus, AttributeModifier.Operation.ADD_VALUE));
    }
}
