package org.relaymodding.petcollecting.api;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public interface PetAbility {

    /**
     * <p> Always called on the serverside. </p>
     *
     *
     * <p> Check {@link org.relaymodding.petcollecting.items.PetItem#use(Level, Player, InteractionHand)} and {@link org.relaymodding.petcollecting.items.PetItem#useOn(UseOnContext)} <p>
     *
     * @param level The serverside level where the action is executing.
     * @param player The player that used the ability.
     * @param clickPos The clicked block position
     * @param itemStack The ItemStack holding the ability.
     * @return A boolean representing whether the action was carried successfully. Used to apply cooldowns.
     */
    boolean processPetAbility(Level level, Player player, BlockPos clickPos, ItemStack itemStack);

    String descriptionId();


    /**
     * If the pet item should have durability, override this and set the Ingredient in the Item constructor.
     * @return A string that is appended to a language key that is constructed in {@link PetAbility#getFood()} that specifies what needs to be fed to the pet.
     */
    default String foodId() {
        return "";
    }

    default MutableComponent getFood() {
        final String foodId = foodId();
        if (foodId.isEmpty() || foodId.isBlank()) {
            return null;
        }
        else {
            return Component.translatable("petcollecting.pet.food.base").withStyle(ChatFormatting.DARK_GRAY).append(Component.translatable("petcollecting.pet.food." + foodId)).withStyle(ChatFormatting.GRAY);
        }
    }

    default MutableComponent getDisplayName() {
        return Component.translatable("petcollecting.pet." + descriptionId());
    }

    default MutableComponent getHoverText() {
        return Component.translatable("petcollecting.pet.desc." + descriptionId()).withStyle(ChatFormatting.DARK_GRAY);
    }
}
