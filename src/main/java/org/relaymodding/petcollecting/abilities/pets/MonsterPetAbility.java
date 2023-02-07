package org.relaymodding.petcollecting.abilities.pets;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.relaymodding.petcollecting.api.PetAbility;
import org.relaymodding.petcollecting.util.MessageFunctions;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class MonsterPetAbility implements PetAbility {
    private static final List<MutableComponent> useResponses = IntStream.range(0, 1).mapToObj(value -> "petcollecting.pet.response.monster." + value).map(Component::translatable).toList();

    @Override
    public boolean processPetAbility(Level level, Player player, BlockPos clickPos, ItemStack itemStack) {
        level.getEntities(player, player.getBoundingBox().inflate(5),
                        entity -> entity instanceof Mob mob && Optional.ofNullable(mob.getTarget())
                                .map(livingEntity -> livingEntity.is(player))
                                .orElse(false))
                .stream()
                .map(Mob.class::cast)
                .forEach(mob -> mob.setTarget(null)); // Only kills their motivation for a single tick...

        MutableComponent useResponse = useResponses.get(level.getRandom().nextInt(useResponses.size()));
        MessageFunctions.sendClientMessage(player, useResponse.withStyle(ChatFormatting.DARK_PURPLE));
        return true;
    }

    @Override
    public String descriptionId() {
        return "monster";
    }
}
