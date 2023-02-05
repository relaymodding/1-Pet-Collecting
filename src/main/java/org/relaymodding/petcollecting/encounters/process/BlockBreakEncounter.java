package org.relaymodding.petcollecting.encounters.process;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.relaymodding.petcollecting.data.Constants;
import org.relaymodding.petcollecting.encounters.EncounterHelper;
import org.relaymodding.petcollecting.items.PetItem;
import org.relaymodding.petcollecting.items.PetItems;

public class BlockBreakEncounter {
    /*
        Processes encounters originated from
            PlayerEncounterEvents.onBlockBreak(BlockEvent.BreakEvent e)
     */
    public static boolean processBlockBreakEncounter(Level level, Player player, BlockPos blockPos, EncounterHelper.EncounterType encounterType) {
        PetItem petItem = null;
        ChatFormatting messageColour = null;

        switch (encounterType) {
            case TREE_CHOP -> {
                petItem = PetItems.WOOD_PET;
                messageColour = Constants.encounterMessageColour;
            }
            case STONE_BREAK -> {
                petItem = PetItems.STONE_PET;
                messageColour = Constants.encounterMessageColour;
            }
            case DIRT_DIG -> {
                petItem = PetItems.DIRT_PET;
                messageColour = Constants.encounterMessageColour;
            }
            case SAND_DIG -> {
                petItem = PetItems.SAND_PET;
                messageColour = Constants.encounterMessageColour;
            }
        }

        return EncounterHelper.dropPetItem(level, player, blockPos, petItem, messageColour);
    }
}
