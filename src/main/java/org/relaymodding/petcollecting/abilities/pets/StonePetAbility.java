package org.relaymodding.petcollecting.abilities.pets;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.relaymodding.petcollecting.api.PetAbility;

public class StonePetAbility implements PetAbility {
    @Override
    public boolean processPetAbility(Level level, Player player, BlockPos clickPos, ItemStack itemStack) {
        return false;
    }

    @Override
    public String descriptionId() {
        return "stone";
    }
}
