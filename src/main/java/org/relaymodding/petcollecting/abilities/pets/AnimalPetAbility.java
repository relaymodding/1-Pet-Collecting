package org.relaymodding.petcollecting.abilities.pets;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.relaymodding.petcollecting.data.Constants;
import org.relaymodding.petcollecting.items.PetItem;
import org.relaymodding.petcollecting.util.MessageFunctions;

import java.util.Arrays;
import java.util.List;

public class AnimalPetAbility {
    private static final List<String> useResponses = Arrays.asList("I create life.", "Hello there little friend.", "I did not expect you!");

    private static final List<EntityType<?>> surfaceAnimalEntityTypes = Arrays.asList(EntityType.BEE, EntityType.CAT, EntityType.CHICKEN, EntityType.COW, EntityType.FOX, EntityType.MOOSHROOM, EntityType.OCELOT, EntityType.PANDA, EntityType.PARROT, EntityType.PIG, EntityType.POLAR_BEAR, EntityType.RABBIT, EntityType.SHEEP, EntityType.WOLF, EntityType.GOAT, EntityType.HORSE);
    private static final List<EntityType<?>> waterAnimalEntityTypes = Arrays.asList(EntityType.AXOLOTL, EntityType.TURTLE, EntityType.TROPICAL_FISH, EntityType.PUFFERFISH, EntityType.COD, EntityType.DOLPHIN, EntityType.SALMON, EntityType.SQUID, EntityType.GLOW_SQUID);

    /*
        Creates a random animal entity on click position.
     */
    public static boolean processPetAbility(Level level, Player player, BlockPos clickPos, ItemStack itemStack, PetItem petItem) {
        BlockState blockState = level.getBlockState(clickPos);
        Block block = blockState.getBlock();

        EntityType<?> animalEntityType;
        if (block.equals(Blocks.WATER)) {
            animalEntityType = waterAnimalEntityTypes.get(Constants.RANDOM.nextInt(waterAnimalEntityTypes.size()));
        }
        else {
            animalEntityType = surfaceAnimalEntityTypes.get(Constants.RANDOM.nextInt(surfaceAnimalEntityTypes.size()));
        }

        LivingEntity livingEntity = (LivingEntity)animalEntityType.create(level);
        if (livingEntity == null) {
            return false;
        }

        livingEntity.setPos(clickPos.getX(), clickPos.getY()+1, clickPos.getZ());
        level.addFreshEntity(livingEntity);

        String playerName = player.getName().getString();

        String useResponse;
        if (playerName.equals("Serilum")) {
            useResponse = "Rick, spawning animals again?";
        }
        else {
            useResponse = useResponses.get(Constants.RANDOM.nextInt(useResponses.size()));
        }

        MessageFunctions.sendClientMessage(player, useResponse, ChatFormatting.GOLD);

        return true;
    }
}
