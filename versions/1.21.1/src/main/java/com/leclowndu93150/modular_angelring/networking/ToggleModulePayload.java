package com.leclowndu93150.modular_angelring.networking;

import com.leclowndu93150.modular_angelring.AngelRingMain;
import com.leclowndu93150.modular_angelring.api.AngelRing;
import com.leclowndu93150.modular_angelring.api.InstalledModules;
import com.leclowndu93150.modular_angelring.api.RingModule;
import com.leclowndu93150.modular_angelring.registry.ModuleRegistry;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record ToggleModulePayload(RingModule module) implements CustomPacketPayload {
    public static final Type<ToggleModulePayload> TYPE = new Type<>(AngelRingMain.id("toggle_module"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ToggleModulePayload> STREAM_CODEC = ByteBufCodecs.registry(ModuleRegistry.KEY)
            .map(ToggleModulePayload::new, ToggleModulePayload::module);

    @Override
    public Type<ToggleModulePayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        Player player = context.player();
        ItemStack ring = AngelRing.find(player);
        if (!module.isToggleable() || !InstalledModules.of(ring).has(module)) return;

        boolean enabled = module.toggle(ring);
        player.displayClientMessage(module.getStateText(enabled), true);
        player.playNotifySound(SoundEvents.NOTE_BLOCK_BELL.value(), SoundSource.PLAYERS, 0.4F, enabled ? 0.01F : 0.09F);
    }
}
