package org.relaymodding.petcollecting.encounters.process;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.relaymodding.petcollecting.data.Constants;
import org.relaymodding.petcollecting.encounters.EncounterHelper;

public class TickEncounter {

    private static final String LAST_ENCOUNTER_KEY = Constants.namespace("lastEncounter");

    /*
        Processes encounters originated from
            PlayerEncounterEvents.onPlayerTick(TickEvent.PlayerTickEvent e)
     */
    public static boolean processTickEncounter(Level level, Player player, BlockPos blockPos, EncounterHelper.EncounterType encounterType) {

        CompoundTag persistentTag = player.getPersistentData().getCompound(Player.PERSISTED_NBT_TAG);
        boolean shouldEncounter = true;
        if (persistentTag.contains(LAST_ENCOUNTER_KEY)) {
            long lastPositionLong = persistentTag.getLong(LAST_ENCOUNTER_KEY);
            BlockPos lastPosition = BlockPos.of(lastPositionLong);
            if (player.distanceToSqr(Vec3.atCenterOf(lastPosition)) < 1) {
                shouldEncounter = false;
            }
        }

        if (!shouldEncounter) {
            return false;
        }
        persistentTag.putLong(LAST_ENCOUNTER_KEY, blockPos.asLong());
        player.getPersistentData().put(Player.PERSISTED_NBT_TAG, persistentTag);

        return EncounterHelper.dropPetItem(level, player, blockPos, encounterType);
    }
}
