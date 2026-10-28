package com.leclowndu93150.modular_angelring.api;

import com.leclowndu93150.modular_angelring.registry.DataComponentRegistry;
import com.leclowndu93150.modular_angelring.registry.ModuleRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.lwjgl.glfw.GLFW;

import java.util.List;
import java.util.function.BiConsumer;

public abstract class RingModule {
    private final boolean toggleable;
    private final int defaultKey;

    protected RingModule(Properties properties) {
        this.toggleable = properties.toggleable();
        this.defaultKey = properties.defaultKey();
    }

    public boolean isToggleable() {
        return toggleable;
    }

    public int getDefaultKey() {
        return defaultKey;
    }

    public Identifier getId() {
        return ModuleRegistry.REGISTRY.getKey(this);
    }

    public String getDescriptionId() {
        return getId().toLanguageKey("module");
    }

    public MutableComponent getName() {
        return Component.translatable(getDescriptionId());
    }

    public boolean isEnabled(ItemStack ring) {
        return InstalledModules.of(ring).isEnabled(this);
    }

    public boolean isActive(LivingEntity entity) {
        return isEnabled(AngelRing.find(entity));
    }

    public InstalledModules installInto(InstalledModules modules) {
        return modules.with(this, !toggleable);
    }

    public void install(ItemStack ring) {
        ring.set(DataComponentRegistry.MODULES, installInto(InstalledModules.of(ring)));
        onInstall(ring);
    }

    public boolean toggle(ItemStack ring) {
        boolean enabled = !isEnabled(ring);
        ring.set(DataComponentRegistry.MODULES, InstalledModules.of(ring).with(this, enabled));
        return enabled;
    }

    protected void onInstall(ItemStack ring) {
    }

    public void tick(Player player, ItemStack ring) {
    }

    public void addAttributeModifiers(ItemStack ring, BiConsumer<Holder<Attribute>, AttributeModifier> modifiers) {
    }

    public boolean onRingUse(Level level, Player player, ItemStack ring) {
        return false;
    }

    public MutableComponent getStateText(boolean enabled) {
        Component state = Component.translatable(enabled ? "text.modular_angelring.enabled" : "text.modular_angelring.disabled");
        return Component.translatable("text.modular_angelring.module_state", getName(), state)
                .withStyle(enabled ? ChatFormatting.GREEN : ChatFormatting.RED);
    }

    public void appendRingTooltip(ItemStack ring, List<Component> tooltip) {
        tooltip.add(toggleable ? getStateText(isEnabled(ring)) : getName().withStyle(ChatFormatting.GRAY));
    }

    public List<Component> getItemTooltip(boolean detailed) {
        return List.of(Component.translatable(getDescriptionId() + ".desc").withStyle(ChatFormatting.GRAY));
    }

    public record Properties(boolean toggleable, int defaultKey) {
        public static Properties passive() {
            return new Properties(false, GLFW.GLFW_KEY_UNKNOWN);
        }

        public static Properties toggleable(int defaultKey) {
            return new Properties(true, defaultKey);
        }
    }
}
