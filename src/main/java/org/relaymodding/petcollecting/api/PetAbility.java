package org.relaymodding.petcollecting.api;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public interface PetAbility {
    boolean processPetAbility(Level level, Player player, BlockPos clickPos, ItemStack itemStack);

    String descriptionId();

    default MutableComponent getDisplayName() {
        return Component.translatable("petcollecting.pet." + descriptionId());
    }

    default MutableComponent getHoverText() {
        return Component.translatable("petcollecting.pet.desc." + descriptionId()).withStyle(ChatFormatting.DARK_GRAY);
    }
}
