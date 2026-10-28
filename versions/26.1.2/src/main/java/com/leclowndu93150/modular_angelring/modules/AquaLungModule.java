package com.leclowndu93150.modular_angelring.modules;

import com.leclowndu93150.modular_angelring.AngelRingMain;
import com.leclowndu93150.modular_angelring.api.RingModule;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.function.BiConsumer;

public class AquaLungModule extends RingModule {
    private static final Identifier SUBMERGED_MINING = AngelRingMain.id("aqua_lung");
    private static final double AQUA_AFFINITY_BONUS = 4.0;

    public AquaLungModule() {
        super(Properties.passive());
    }

    @Override
    public void tick(Player player, ItemStack ring) {
        if (player.isUnderWater()) {
            player.setAirSupply(player.getMaxAirSupply());
        }
    }

    @Override
    public void addAttributeModifiers(ItemStack ring, BiConsumer<Holder<Attribute>, AttributeModifier> modifiers) {
        modifiers.accept(Attributes.SUBMERGED_MINING_SPEED, new AttributeModifier(SUBMERGED_MINING, AQUA_AFFINITY_BONUS, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
    }
}
