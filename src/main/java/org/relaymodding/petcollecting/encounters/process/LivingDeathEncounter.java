package org.relaymodding.petcollecting.encounters.process;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.relaymodding.petcollecting.data.Constants;
import org.relaymodding.petcollecting.encounters.EncounterHelper;
import org.relaymodding.petcollecting.items.PetItem;
import org.relaymodding.petcollecting.items.PetItems;

public class LivingDeathEncounter {
    /*
        Processes encounters originated from
            PlayerEncounterEvents.onLivingDeath(LivingDeathEvent e)
     */
    public static boolean processLivingDeathEncounter(Level level, Player player, BlockPos blockPos, EncounterHelper.EncounterType encounterType) {
        PetItem petItem = null;
        ChatFormatting messageColour = null;

        switch (encounterType) {
            case PLAYER_DEATH -> {
                petItem = PetItems.PLAYER_PET;
                messageColour = Constants.encounterMessageColour;
            }
            case MONSTER_DEATH -> {
                petItem = PetItems.MONSTER_PET;
                messageColour = Constants.encounterMessageColour;
            }
            case ANIMAL_DEATH -> {
                petItem = PetItems.ANIMAL_PET;
                messageColour = Constants.encounterMessageColour;
            }
        }

        return EncounterHelper.dropPetItem(level, player, blockPos, petItem, messageColour);
    }
}
