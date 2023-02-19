package org.relaymodding.petcollecting.util;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public class PlayerFunctions {
    public static void applyCooldown(Player player, int cooldown, Item item) {
        if (!player.isCreative()) {
            player.getCooldowns().addCooldown(item, cooldown);
        }
    }
}
