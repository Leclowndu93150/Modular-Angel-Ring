package com.leclowndu93150.modular_angelring.item;

import com.leclowndu93150.modular_angelring.AngelRingMain;
import com.leclowndu93150.modular_angelring.api.InstalledModules;
import com.leclowndu93150.modular_angelring.api.RingModule;
import com.leclowndu93150.modular_angelring.legacy.LegacyMigration;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.NeoForgeMod;
import org.jspecify.annotations.Nullable;
import top.theillusivec4.curios.api.CurioAttributeModifiers;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class AngelRingItem extends Item implements ICurioItem {
    private static final AttributeModifier FLIGHT = new AttributeModifier(AngelRingMain.id("flight"), 1, AttributeModifier.Operation.ADD_VALUE);

    public AngelRingItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }

    @Override
    public void inventoryTick(ItemStack stack, ServerLevel level, Entity entity, @Nullable EquipmentSlot slot) {
        LegacyMigration.migrate(stack);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            InstalledModules.of(stack).enabled().forEach(module -> module.tick(player, stack));
        }
    }

    @Override
    public CurioAttributeModifiers getDefaultCurioAttributeModifiers(ItemStack stack) {
        CurioAttributeModifiers.Builder modifiers = CurioAttributeModifiers.builder().addModifier(NeoForgeMod.CREATIVE_FLIGHT, FLIGHT);
        InstalledModules.of(stack).enabled().forEach(module -> module.addAttributeModifiers(stack, modifiers::addModifier));
        return modifiers.build();
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack ring = player.getItemInHand(hand);
        for (RingModule module : InstalledModules.of(ring).installed()) {
            if (module.onRingUse(level, player, ring)) {
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.FAIL;
    }
}
