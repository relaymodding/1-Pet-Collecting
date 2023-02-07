package org.relaymodding.petcollecting.abilities.pets;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.relaymodding.petcollecting.api.PetAbility;
import org.relaymodding.petcollecting.util.MessageFunctions;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

public class AnimalPetAbility implements PetAbility {
    private static final UUID SERILUM_UUID = UUID.fromString("8f385a05-25c9-4b85-9b14-f7dc7cfc1d4f");
    private static final MutableComponent SERILUM_RESPONSE = Component.translatable("petcollecting.pet.response.animal.serilum");
    private static final List<MutableComponent> useResponses = IntStream.range(0, 3).mapToObj(value -> "petcollecting.pet.response.animal." + value).map(Component::translatable).toList();
    private static final List<EntityType<?>> surfaceAnimalEntityTypes = Arrays.asList(EntityType.BEE, EntityType.CAT, EntityType.CHICKEN, EntityType.COW, EntityType.FOX, EntityType.MOOSHROOM, EntityType.OCELOT, EntityType.PANDA, EntityType.PARROT, EntityType.PIG, EntityType.POLAR_BEAR, EntityType.RABBIT, EntityType.SHEEP, EntityType.WOLF, EntityType.GOAT, EntityType.HORSE);
    private static final List<EntityType<?>> waterAnimalEntityTypes = Arrays.asList(EntityType.AXOLOTL, EntityType.TURTLE, EntityType.TROPICAL_FISH, EntityType.PUFFERFISH, EntityType.COD, EntityType.DOLPHIN, EntityType.SALMON, EntityType.SQUID, EntityType.GLOW_SQUID);

    /*
        Creates a random animal entity on click position.
     */
    public boolean processPetAbility(Level level, Player player, BlockPos clickPos, ItemStack itemStack) {
        BlockState blockState = level.getBlockState(clickPos);
        Block block = blockState.getBlock();

        EntityType<?> animalEntityType;
        if (block.equals(Blocks.WATER)) {
            animalEntityType = waterAnimalEntityTypes.get(level.getRandom().nextInt(waterAnimalEntityTypes.size()));
        } else {
            animalEntityType = surfaceAnimalEntityTypes.get(level.getRandom().nextInt(surfaceAnimalEntityTypes.size()));
        }

        LivingEntity livingEntity = (LivingEntity) animalEntityType.create(level);
        if (livingEntity == null) {
            return false;
        }

        livingEntity.setPos(clickPos.getX(), clickPos.getY() + 1, clickPos.getZ());
        level.addFreshEntity(livingEntity);

        MutableComponent useResponse = useResponses.get(level.getRandom().nextInt(useResponses.size()));
        if (player.getGameProfile().getId().equals(SERILUM_UUID)) {
            useResponse = SERILUM_RESPONSE;
        }

        MessageFunctions.sendClientMessage(player, useResponse.withStyle(ChatFormatting.GOLD));

        return true;
    }

    @Override
    public String descriptionId() {
        return "animal";
    }
}
