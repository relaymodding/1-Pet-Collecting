package org.relaymodding.petcollecting.abilities.pets;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.relaymodding.petcollecting.api.PetAbility;
import org.relaymodding.petcollecting.util.MessageFunctions;

import java.util.List;
import java.util.stream.IntStream;

public class WaterPetAbility implements PetAbility {
    private static final List<MutableComponent> useResponses = IntStream.range(0, 2).mapToObj(value -> "petcollecting.pet.response.water." + value).map(Component::translatable).toList();

    @Override
    public boolean processPetAbility(Level level, Player player, BlockPos clickPos, ItemStack itemStack) {

        player.addEffect(new MobEffectInstance(MobEffects.DOLPHINS_GRACE, 60));
        player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 60));
        MutableComponent useResponse = useResponses.get(level.getRandom().nextInt(useResponses.size()));
        MessageFunctions.sendClientMessage(player, useResponse.withStyle(ChatFormatting.DARK_AQUA));
        return true;
    }

    @Override
    public String descriptionId() {
        return "water";
    }
}
