package org.relaymodding.petcollecting.events;

import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.relaymodding.petcollecting.data.Constants;
import org.relaymodding.petcollecting.encounters.EncounterHelper;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID)
public class PetEncounterEvents {
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent e) {
        Player player = e.player;
        Level level = player.getCommandSenderWorld();
        if (level.isClientSide || !e.phase.equals(TickEvent.Phase.START)) {
            return;
        }

        EncounterHelper.checkForEncounter(level, player, player.blockPosition(), EncounterHelper.EncounterType.fromTick(player), EncounterHelper.OriginType.TICK);
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent e) {
        Player player = e.getPlayer();
        Level level = player.level;
        if (level.isClientSide) {
            return;
        }

        BlockPos blockPos = e.getPos();
        BlockState blockState = e.getState();
        Block block = blockState.getBlock();

        EncounterHelper.checkForEncounter(level, player, blockPos, EncounterHelper.EncounterType.fromBreak(block), EncounterHelper.OriginType.BLOCK_BREAK);
    }

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent e) {
        LivingEntity livingEntity = e.getEntity();
        Level level = livingEntity.level;
        if (level.isClientSide) {
            return;
        }

        DamageSource damageSource = e.getSource();
        Entity sourceEntity = damageSource.getEntity();

        if (!(sourceEntity instanceof Player player)) {
            return;
        }

        BlockPos entityPos = livingEntity.blockPosition();

        EncounterHelper.checkForEncounter(level, player, entityPos, EncounterHelper.EncounterType.fromDeath(livingEntity), EncounterHelper.OriginType.LIVING_DEATH);
    }
}