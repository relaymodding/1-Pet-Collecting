package org.relaymodding.petcollecting.encounters;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.relaymodding.petcollecting.Main;
import org.relaymodding.petcollecting.data.Constants;
import org.relaymodding.petcollecting.data.Variables;
import org.relaymodding.petcollecting.encounters.process.BlockBreakEncounter;
import org.relaymodding.petcollecting.encounters.process.LivingDeathEncounter;
import org.relaymodding.petcollecting.encounters.process.TickEncounter;
import org.relaymodding.petcollecting.items.PetItem;
import org.relaymodding.petcollecting.util.InventoryFunctions;
import org.relaymodding.petcollecting.util.MessageFunctions;

import java.util.Date;

public class EncounterHelper {
    /*
        Checks whether the player is encountering a pet, and drops it.
     */
    public static void checkForEncounter(Level level, Player player, BlockPos blockPos, EncounterType encounterType, OriginType originType) {
        String playerName = player.getName().getString();

        Date now = new Date();
        Date previousEncounterDate = Variables.petEncounterCooldowns.get(playerName);

        if (previousEncounterDate != null) {
            long ms = (now.getTime() - previousEncounterDate.getTime());
            if (ms < Main.config.petEncounterCooldownMs) {
                return;
            }
        }

        double chance = 0.0;
        double roll = Constants.RANDOM.nextDouble();

        switch (originType) {
            case TICK -> chance = Main.config.tickEncounterChance;
            case BLOCK_BREAK -> chance = Main.config.blockBreakEncounterChance;
            case LIVING_DEATH -> chance = Main.config.livingDeathEncounterChance;
        }

        if (roll > chance) {
            return;
        }

        boolean encounterResult = false;
        switch (originType) {
            case TICK -> {
                encounterResult = TickEncounter.processTickEncounter(level, player, blockPos, encounterType);
            }
            case BLOCK_BREAK -> {
                encounterResult = BlockBreakEncounter.processBlockBreakEncounter(level, player, blockPos, encounterType);
            }
            case LIVING_DEATH -> {
                encounterResult = LivingDeathEncounter.processLivingDeathEncounter(level, player, blockPos, encounterType);
            }
        }

        if (!encounterResult) {
            return;
        }

        BlockPos encounterPos = blockPos.immutable();
        if (originType.equals(OriginType.TICK)) {
            encounterPos = encounterPos.relative(player.getDirection(), 3);
        }

        showEncounterParticles(level, player, encounterPos, originType);
        playEncounterSound(level, player);

        Variables.petEncounterCooldowns.put(playerName, now);
    }

    public static boolean dropPetItem(Level level, Player player, BlockPos blockPos, PetItem petItem, ChatFormatting messageColour) {
        if (petItem == null) {
            return false;
        }

        if (InventoryFunctions.doesInventoryContainPet(player, petItem)) {
            return false;
        }

        String petName = petItem.toString().replace("_", " ");

        level.addFreshEntity(new ItemEntity(level, blockPos.getX(), blockPos.getY()+0.5, blockPos.getZ(), new ItemStack(petItem)));
        MessageFunctions.sendMessage(player, "You encountered a " + petName + "!", messageColour);
        return true;
    }

    private static void showEncounterParticles(Level level, Player player, BlockPos encounterPos, OriginType originType) {
        level.levelEvent(2003, encounterPos.above(), 0); //TODO: placeholder? Currently same as eye of ender used up
    }

    private static void playEncounterSound(Level level, Player player) {
        player.playSound(SoundEvents.ENDER_EYE_DEATH, 1.0F, 1.0F); //TODO: placeholder
    }

    public enum EncounterType {
        SWIMMING,
        FLYING,
        TALL_GRASS,
        IN_LAVA,

        TREE_CHOP,
        STONE_BREAK,
        DIRT_DIG,
        SAND_DIG,

        PLAYER_DEATH,
        MONSTER_DEATH,
        ANIMAL_DEATH
    }

    public enum OriginType {
        TICK,
        BLOCK_BREAK,
        LIVING_DEATH
    }
}
