package org.relaymodding.petcollecting.encounters.process;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.relaymodding.petcollecting.data.Constants;
import org.relaymodding.petcollecting.data.Variables;
import org.relaymodding.petcollecting.encounters.EncounterHelper;
import org.relaymodding.petcollecting.items.PetItem;
import org.relaymodding.petcollecting.items.PetItems;

public class TickEncounter {
    /*
        Processes encounters originated from
            PlayerEncounterEvents.onPlayerTick(TickEvent.PlayerTickEvent e)
     */
    public static boolean processTickEncounter(Level level, Player player, BlockPos blockPos, EncounterHelper.EncounterType encounterType) {
        String playerName = player.getName().getString();

        BlockPos lastPos = Variables.lastPlayerPositions.get(playerName);
        if (lastPos != null) {
            if (lastPos.equals(blockPos)) {
                return false;
            }
        }

        Variables.lastPlayerPositions.put(playerName, blockPos);

        PetItem petItem = null;
        ChatFormatting messageColour = null;

        switch (encounterType) {
            case SWIMMING -> {
                petItem = PetItems.WATER_PET;
                messageColour = Constants.encounterMessageColour;
            }
            case IN_LAVA -> {
                petItem = PetItems.LAVA_PET;
                messageColour = Constants.encounterMessageColour;
            }
            case FLYING -> {
                petItem = PetItems.AIR_PET;
                messageColour = Constants.encounterMessageColour;
            }
            case TALL_GRASS -> {
                petItem = PetItems.GRASS_PET;
                messageColour = Constants.encounterMessageColour;
            }
        }

        return EncounterHelper.dropPetItem(level, player, blockPos, petItem, messageColour);
    }
}
