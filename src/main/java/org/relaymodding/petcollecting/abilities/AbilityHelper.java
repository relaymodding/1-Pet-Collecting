package org.relaymodding.petcollecting.abilities;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.relaymodding.petcollecting.Main;
import org.relaymodding.petcollecting.abilities.pets.AnimalPetAbility;
import org.relaymodding.petcollecting.abilities.pets.GrassPetAbility;
import org.relaymodding.petcollecting.abilities.pets.WoodPetAbility;
import org.relaymodding.petcollecting.data.Variables;
import org.relaymodding.petcollecting.items.PetItem;
import org.relaymodding.petcollecting.items.PetItems;
import org.relaymodding.petcollecting.util.MessageFunctions;
import org.relaymodding.petcollecting.util.NumberFunctions;

import java.util.Date;

public class AbilityHelper {
    /*
        Attempts to run the pet ability. Returns true if ran.
     */
    public static boolean abilityCheck(Level level, Player player, BlockPos clickPos, ItemStack itemStack, PetItem petItem) {
        if (level.isClientSide) {
            return false;
        }

        String playerName = player.getName().getString();

        Date now = new Date();
        Date previousAbilityUseDate = Variables.petUseCooldowns.get(playerName);

        if (previousAbilityUseDate != null) {
            long ms = (now.getTime() - previousAbilityUseDate.getTime());
            if (ms < Main.config.petUseAbilityCooldownMs) {
                if (ms > 200) {
                    double secondsLeft = NumberFunctions.round(((Main.config.petUseAbilityCooldownMs - ms)/1000F), 2);
                    MessageFunctions.sendClientMessage(player, "Your pets are still on cooldown for another " + secondsLeft + " seconds.", ChatFormatting.GRAY);
                }
                return false;
            }
        }

        /*
            TODO: remove/add/change pet abilities
         */
        boolean abilityResult = false;
        if (petItem.equals(PetItems.WATER_PET)) {

        }
        else if (petItem.equals(PetItems.AIR_PET)) {

        }
        else if (petItem.equals(PetItems.GRASS_PET)) {
            abilityResult = GrassPetAbility.processPetAbility(level, player, clickPos, itemStack, petItem);
        }
        else if (petItem.equals(PetItems.LAVA_PET)) {

        }
        else if (petItem.equals(PetItems.WOOD_PET)) {
            abilityResult = WoodPetAbility.processPetAbility(level, player, clickPos, itemStack, petItem);
        }
        else if (petItem.equals(PetItems.STONE_PET)) {

        }
        else if (petItem.equals(PetItems.DIRT_PET)) {

        }
        else if (petItem.equals(PetItems.SAND_PET)) {

        }
        else if (petItem.equals(PetItems.PLAYER_PET)) {

        }
        else if (petItem.equals(PetItems.MONSTER_PET)) {

        }
        else if (petItem.equals(PetItems.ANIMAL_PET)) {
            abilityResult = AnimalPetAbility.processPetAbility(level, player, clickPos, itemStack, petItem);
        }

        if (abilityResult) {
            Variables.petUseCooldowns.put(playerName, now);
        }

        return abilityResult;
    }
}
