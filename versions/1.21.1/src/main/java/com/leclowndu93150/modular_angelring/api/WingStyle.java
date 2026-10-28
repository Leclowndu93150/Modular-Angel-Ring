package com.leclowndu93150.modular_angelring.api;

import com.leclowndu93150.modular_angelring.AngelRingMain;
import com.leclowndu93150.modular_angelring.registry.DataComponentRegistry;
import com.leclowndu93150.modular_angelring.registry.ItemRegistry;
import com.mojang.serialization.Codec;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.codec.NeoForgeStreamCodecs;

import java.util.Locale;
import java.util.function.Supplier;

public enum WingStyle implements StringRepresentable {
    ANGEL(ItemRegistry.ANGEL_WINGS),
    BAT(ItemRegistry.BAT_WINGS),
    BIG_DRAGON(ItemRegistry.BIG_DRAGON_WINGS),
    BLUE_DRAGON(ItemRegistry.BLUE_DRAGON_WINGS),
    BUTTERFLY(ItemRegistry.BUTTERFLY_WINGS),
    DRAGON(ItemRegistry.DRAGON_WINGS),
    GOLD(ItemRegistry.GOLD_WINGS),
    GOLDEN(ItemRegistry.GOLDEN_WINGS);

    public static final Codec<WingStyle> CODEC = StringRepresentable.fromEnum(WingStyle::values);
    public static final StreamCodec<FriendlyByteBuf, WingStyle> STREAM_CODEC = NeoForgeStreamCodecs.enumCodec(WingStyle.class);
    public static final ResourceLocation MODEL_PROPERTY = AngelRingMain.id("wings");

    private final Supplier<Item> wings;

    WingStyle(Supplier<Item> wings) {
        this.wings = wings;
    }

    public static WingStyle of(ItemStack ring) {
        return ring.getOrDefault(DataComponentRegistry.WINGS, ANGEL);
    }

    @Override
    public String getSerializedName() {
        return name().toLowerCase(Locale.ROOT);
    }

    public Item getWingsItem() {
        return wings.get();
    }

    public ResourceLocation getRingModel() {
        return AngelRingMain.id("item/ring/" + getSerializedName());
    }

    public ResourceLocation getWornModel() {
        return AngelRingMain.id("item/wings/" + getSerializedName());
    }
}
