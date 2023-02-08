package org.relaymodding.petcollecting.abilities.pets;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkDirection;
import org.relaymodding.petcollecting.Main;
import org.relaymodding.petcollecting.api.PetAbility;
import org.relaymodding.petcollecting.network.ClientSetPlayerSpeedPacket;
import org.relaymodding.petcollecting.network.PacketHandler;
import org.relaymodding.petcollecting.util.MessageFunctions;

import java.util.List;
import java.util.stream.IntStream;

public class AirPetAbility implements PetAbility {

    private static final List<MutableComponent> useResponses = IntStream.range(0, 3).mapToObj(value -> "petcollecting.pet.response.air." + value).map(Component::translatable).toList();

    @Override
    public boolean processPetAbility(Level level, Player player, BlockPos clickPos, ItemStack itemStack) {
        if (!player.getItemBySlot(EquipmentSlot.CHEST).getItem().equals(Items.ELYTRA)) return false;
        //Nice naming mojang
        PacketHandler.INSTANCE.sendTo(new ClientSetPlayerSpeedPacket(0d, Main.config.elytraJumpBoost, 0d), ((ServerPlayer)player).connection.connection, NetworkDirection.PLAY_TO_CLIENT);
        ((ServerLevel)level).sendParticles(ParticleTypes.FIREWORK, clickPos.getX(), clickPos.getY(), clickPos.getZ(), 3, level.random.nextGaussian(), level.random.nextGaussian(), level.random.nextGaussian(), 1.0f);
        MessageFunctions.sendClientMessage(player, useResponses.get(level.getRandom().nextInt(useResponses.size())).withStyle(ChatFormatting.WHITE));
        return true;
    }

    @Override
    public String descriptionId() {
        return "air";
    }

    @Override
    public String foodId() {
        return descriptionId();
    }
}
