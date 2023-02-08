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
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.relaymodding.petcollecting.Main;
import org.relaymodding.petcollecting.api.PetAbility;
import org.relaymodding.petcollecting.util.MessageFunctions;
import org.relaymodding.petcollecting.util.RaytraceFunctions;

import java.util.List;
import java.util.stream.IntStream;

public class EndPetAbility implements PetAbility {

    private static final List<MutableComponent> useResponses = IntStream.range(0, 3).mapToObj(value -> "petcollecting.pet.response.end." + value).map(Component::translatable).toList();

    @Override
    public boolean processPetAbility(Level level, Player player, BlockPos clickPos, ItemStack itemStack) {
        final HitResult result = RaytraceFunctions.blockRaytrace(player, Main.config.endPetRange);
        if (result == null || result.getLocation().y > player.getY()) return false;
        Vec3 position = result.getLocation();
        player.teleportTo(position.x, position.y, position.z);
        //Why is showIcon set to false and yet it still shows (?) Are these ignored in dev or something?
        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 10 * 20, 1, false, false, false));
        MessageFunctions.sendClientMessage(player, useResponses.get(level.getRandom().nextInt(useResponses.size())).withStyle(ChatFormatting.LIGHT_PURPLE));
        return true;
    }

    @Override
    public String descriptionId() {
        return "end";
    }

    @Override
    public String foodId() {
        return descriptionId();
    }
}
