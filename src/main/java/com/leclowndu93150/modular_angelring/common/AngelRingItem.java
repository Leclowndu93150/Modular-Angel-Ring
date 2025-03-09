package com.leclowndu93150.modular_angelring.common;

import com.leclowndu93150.modular_angelring.AngelRingConfig;
import com.leclowndu93150.modular_angelring.AngelRingMain;
import com.leclowndu93150.modular_angelring.events.AngelRingEvents;
import com.leclowndu93150.modular_angelring.registry.AttachementRegistry;
import com.leclowndu93150.modular_angelring.registry.DataComponentRegistry;
import com.leclowndu93150.modular_angelring.registry.ItemRegistry;
import com.leclowndu93150.modular_angelring.utils.FlightSpeedPercentage;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundRemoveMobEffectPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Interaction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.NeoForgeMod;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.List;
import java.util.Optional;

import static com.leclowndu93150.modular_angelring.common.AngelRingModules.getSpeedModifier;
import static com.leclowndu93150.modular_angelring.common.AngelRingModules.setSpeedModifier;

public class AngelRingItem extends Item {

    public AngelRingItem(Properties pProperties) {
        super(pProperties);
    }

    public static void registerCapabilities(final RegisterCapabilitiesEvent evt) {
        evt.registerItem(
                CuriosCapability.ITEM,
                (stack, context) -> new ICurio() {

                    @Override
                    public ItemStack getStack() {
                        return ItemRegistry.ANGEL_RING.toStack();
                    }

                    @Override
                    public void curioTick(SlotContext slotContext) {
                        Player player = (Player) slotContext.entity();
                        Optional<SlotResult> slotResult = CuriosApi.getCuriosInventory(player).flatMap(handler -> handler.findFirstCurio(ItemRegistry.ANGEL_RING.get()));

                        if (slotResult.isPresent()) {
                            ItemStack angelRingStack = slotResult.get().stack();
                            EnabledModifiersComponent data = angelRingStack.getOrDefault(DataComponentRegistry.MODIFIERS_ENABLED, EnabledModifiersComponent.EMPTY);

                            if (angelRingStack.has(DataComponentRegistry.SPEED_MODIFIER) &&
                                    (player.getAbilities().getFlyingSpeed() != getSpeedModifier(angelRingStack) || !data.speedModifierEnabled())) {

                                if (data.speedModifierEnabled()) {
                                    if (player.getAbilities().flying) {
                                        player.getAbilities().setFlyingSpeed(getSpeedModifier(angelRingStack));
                                    }
                                } else {
                                    player.getAbilities().setFlyingSpeed(0.05F);
                                }
                            }

                            if (angelRingStack.has(DataComponentRegistry.NIGHT_VISION_MODIFIER)) {
                                if (data.nightVisionEnabled()) {
                                    MobEffectInstance effect = player.getEffect(MobEffects.NIGHT_VISION);
                                    if (effect == null || effect.getDuration() < 219) {
                                        player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 400, 0, true, false));
                                    }
                                }
                            }
                        }
                    }


                    @Override
                    public void onEquip(SlotContext slotContext, ItemStack prevStack) {
                        Player player = (Player) slotContext.entity();
                        startFlight(player);
                    }

                    @Override
                    public void onUnequip(SlotContext slotContext, ItemStack newStack) {
                        Player player = (Player) slotContext.entity();
                        stopFlight(player);
                        player.getAbilities().setFlyingSpeed(0.05F);

                        player.removeEffect(MobEffects.NIGHT_VISION);

                        AttachementRegistry.setNightVision(player, false);
                    }
                },  ItemRegistry.ANGEL_RING.get());
    }

    @Override
    public int getMaxStackSize(@NotNull ItemStack stack) {
        return 1;
    }

    @Override
    public boolean isFoil(@NotNull ItemStack pStack) {
        return true;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player player, InteractionHand pUsedHand) {

        final float MAX_FLY_SPEED = (float) AngelRingConfig.maxFlightSpeed;
        final float MIN_FLY_SPEED = 0.0F;
        final float SPEED_INCREMENT = 0.005F;
        ItemStack item = player.getItemInHand(pUsedHand);

        if (item.is(this) && item.has(DataComponentRegistry.SPEED_MODIFIER)) {
            float currentFlySpeed = getSpeedModifier(item);

            if (player.isShiftKeyDown()) {
                currentFlySpeed = Math.max(currentFlySpeed - SPEED_INCREMENT, MIN_FLY_SPEED); // Ensure it doesn't go below the min
            } else if (currentFlySpeed < MAX_FLY_SPEED) {
                currentFlySpeed = Math.min(currentFlySpeed + SPEED_INCREMENT, MAX_FLY_SPEED); // Ensure it doesn't exceed the max
            }

            setSpeedModifier(item, currentFlySpeed);

            int percentage = FlightSpeedPercentage.speedToPercentage(currentFlySpeed);
            player.displayClientMessage(Component.literal("Speed: ")
                    .append(String.valueOf(percentage))
                    .append("%")
                    .withStyle(ChatFormatting.WHITE), true);

            player.level().playSound(player, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.NOTE_BLOCK_BELL.value(), SoundSource.PLAYERS, 0.4f, 0.01f);
            return InteractionResultHolder.success(item);
        }

        return InteractionResultHolder.fail(item);
    }


    private static void startFlight(Player player) {
        player.getAttribute(NeoForgeMod.CREATIVE_FLIGHT).setBaseValue(1);
    }

    private static void stopFlight(Player player) {
        player.getAttribute(NeoForgeMod.CREATIVE_FLIGHT).setBaseValue(0);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext pContext, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pTooltipFlag) {
        EnabledModifiersComponent data = stack.getOrDefault(DataComponentRegistry.MODIFIERS_ENABLED, EnabledModifiersComponent.EMPTY);

        if (AngelRingModules.getMiningSpeedModifier(stack) /* && KeyBindRegistry.miningEnabled */) {
            pTooltipComponents.add(Component.literal("Mining Module")/*.append("Enabled")*/.withStyle(ChatFormatting.GRAY));
        }
        if (AngelRingModules.getInertiaModifier(stack) && data.inertiaEnabled()) {
            pTooltipComponents.add(Component.literal("Inertia Module: ").append("Enabled").withStyle(ChatFormatting.GREEN));
        }
        if (AngelRingModules.getInertiaModifier(stack) && !data.inertiaEnabled()) {
            pTooltipComponents.add(Component.literal("Inertia Module: ").append("Disabled").withStyle(ChatFormatting.RED));
        }
        if (stack.has(DataComponentRegistry.SPEED_MODIFIER) && data.speedModifierEnabled()) {
            pTooltipComponents.add(Component.literal("Speed Module: ").append(String.valueOf(FlightSpeedPercentage.speedToPercentage(AngelRingModules.getSpeedModifier(stack)))).append("%").withStyle(ChatFormatting.GREEN));
        }
        if (stack.has(DataComponentRegistry.SPEED_MODIFIER) && !data.speedModifierEnabled()) {
            pTooltipComponents.add(Component.literal("Speed Module: ").append(String.valueOf(FlightSpeedPercentage.speedToPercentage(AngelRingModules.getSpeedModifier(stack)))).append("%").withStyle(ChatFormatting.RED));
        }
        if (AngelRingModules.getNightVisionModifier(stack) && data.nightVisionEnabled()) {
            pTooltipComponents.add(Component.literal("Night Vision Module: ").append("Enabled").withStyle(ChatFormatting.GREEN));
        }
        if (AngelRingModules.getNightVisionModifier(stack) && !data.nightVisionEnabled()) {
            pTooltipComponents.add(Component.literal("Night Vision Module: ").append("Disabled").withStyle(ChatFormatting.RED));
        }
        if (AngelRingModules.getMagnetModifier(stack) && data.magnetEnabled()) {
            pTooltipComponents.add(Component.literal("Magnet Module: ").append("Enabled").withStyle(ChatFormatting.GREEN).append(" (Radius: ").append(String.valueOf((int) AngelRingConfig.magnetRadius)).append(" blocks)").withStyle(ChatFormatting.GREEN));
        }
        if (AngelRingModules.getMagnetModifier(stack) && !data.magnetEnabled()) {
            pTooltipComponents.add(Component.literal("Magnet Module: ").append("Disabled").withStyle(ChatFormatting.RED));
        }


        super.appendHoverText(stack, pContext, pTooltipComponents, pTooltipFlag);
    }
}
