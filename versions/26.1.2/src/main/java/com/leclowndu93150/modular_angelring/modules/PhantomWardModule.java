package com.leclowndu93150.modular_angelring.modules;

import com.leclowndu93150.modular_angelring.api.RingModule;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class PhantomWardModule extends RingModule {
    private static final int RESET_INTERVAL = 20;

    public PhantomWardModule() {
        super(Properties.passive());
    }

    @Override
    public void tick(Player player, ItemStack ring) {
        if (player.tickCount % RESET_INTERVAL == 0) {
            player.resetStat(Stats.CUSTOM.get(Stats.TIME_SINCE_REST));
        }
    }
}
