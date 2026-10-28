package com.leclowndu93150.modular_angelring.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.leclowndu93150.modular_angelring.api.InstalledModules;
import com.leclowndu93150.modular_angelring.api.RingModule;
import com.leclowndu93150.modular_angelring.legacy.LegacyMigration;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.NeoForgeMod;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public class AngelRingItem extends Item implements ICurioItem {
    public AngelRingItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        if (!level.isClientSide) {
            LegacyMigration.migrate(stack);
        }
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            InstalledModules.of(stack).enabled().forEach(module -> module.tick(player, stack));
        }
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation id, ItemStack stack) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = HashMultimap.create();
        modifiers.put(NeoForgeMod.CREATIVE_FLIGHT, new AttributeModifier(id, 1, AttributeModifier.Operation.ADD_VALUE));
        InstalledModules.of(stack).enabled().forEach(module -> module.addAttributeModifiers(stack, modifiers::put));
        return modifiers;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack ring = player.getItemInHand(hand);
        for (RingModule module : InstalledModules.of(ring).installed()) {
            if (module.onRingUse(level, player, ring)) {
                return InteractionResultHolder.sidedSuccess(ring, level.isClientSide);
            }
        }
        return InteractionResultHolder.fail(ring);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        InstalledModules.of(stack).installed().forEach(module -> module.appendRingTooltip(stack, tooltip));
    }
}
