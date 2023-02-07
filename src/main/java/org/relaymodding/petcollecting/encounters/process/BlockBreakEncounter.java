package org.relaymodding.petcollecting.encounters.process;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.relaymodding.petcollecting.encounters.EncounterHelper;

public class BlockBreakEncounter {
    /*
        Processes encounters originated from
            PlayerEncounterEvents.onBlockBreak(BlockEvent.BreakEvent e)
     */
    public static boolean processBlockBreakEncounter(Level level, Player player, BlockPos blockPos, EncounterHelper.EncounterType encounterType) {
        return EncounterHelper.dropPetItem(level, player, blockPos, encounterType);
    }
}
