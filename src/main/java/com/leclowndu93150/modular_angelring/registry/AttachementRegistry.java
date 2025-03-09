package com.leclowndu93150.modular_angelring.registry;

import com.leclowndu93150.modular_angelring.AngelRingMain;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class AttachementRegistry {

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, AngelRingMain.MODID);

    public static final Supplier<AttachmentType<Boolean>> HAS_NIGHT_VISION = ATTACHMENT_TYPES.register(
            "has_night_vision", () -> AttachmentType.<Boolean>builder(() -> false)
                    .serialize(Codec.BOOL)
                    .copyOnDeath()
                    .build()
    );

    public static Boolean hasNightVision(Player player){
        return player.getData(HAS_NIGHT_VISION).booleanValue();
    }

    public static void setNightVision(Player player, Boolean bool){
        player.setData(HAS_NIGHT_VISION,bool);
    }
}
