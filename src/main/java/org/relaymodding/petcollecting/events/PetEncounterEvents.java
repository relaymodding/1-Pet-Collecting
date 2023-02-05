package org.relaymodding.petcollecting.events;

import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TallGrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.relaymodding.petcollecting.encounters.EncounterHelper;
import org.relaymodding.petcollecting.util.CompareBlockFunctions;

@Mod.EventBusSubscriber
public class PetEncounterEvents {
	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent e) {
		Player player = e.player;
		Level level = player.getCommandSenderWorld();
		if (level.isClientSide || !e.phase.equals(TickEvent.Phase.START)) {
			return;
		}

		BlockPos blockPos = player.blockPosition();
		BlockState blockState = level.getBlockState(blockPos);
		Block block = blockState.getBlock();

		if (player.isSwimming()) {
			EncounterHelper.checkForEncounter(level, player, blockPos, EncounterHelper.EncounterType.SWIMMING, EncounterHelper.OriginType.TICK);
		}
		else if (player.isInLava()) {
			EncounterHelper.checkForEncounter(level, player, blockPos, EncounterHelper.EncounterType.IN_LAVA, EncounterHelper.OriginType.TICK);
		}
		else if (player.isFallFlying()) {
			ItemStack chestStack = player.getItemBySlot(EquipmentSlot.CHEST);
			if (chestStack.getItem() instanceof ElytraItem) {
				EncounterHelper.checkForEncounter(level, player, blockPos, EncounterHelper.EncounterType.FLYING, EncounterHelper.OriginType.TICK);
			}
		}
		else if (block instanceof TallGrassBlock) {
			EncounterHelper.checkForEncounter(level, player, blockPos, EncounterHelper.EncounterType.TALL_GRASS, EncounterHelper.OriginType.TICK);
		}

	}

	@SubscribeEvent
	public void onBlockBreak(BlockEvent.BreakEvent e) {
		Player player = e.getPlayer();
		Level level = player.level;
		if (level.isClientSide) {
			return;
		}

		BlockPos blockPos = e.getPos();
		BlockState blockState = e.getState();
		Block block = blockState.getBlock();

		if (CompareBlockFunctions.isTreeLog(block)) {
			EncounterHelper.checkForEncounter(level, player, blockPos, EncounterHelper.EncounterType.TREE_CHOP, EncounterHelper.OriginType.BLOCK_BREAK);
		}
		else if (CompareBlockFunctions.isStone(block)) {
			EncounterHelper.checkForEncounter(level, player, blockPos, EncounterHelper.EncounterType.STONE_BREAK, EncounterHelper.OriginType.BLOCK_BREAK);
		}
		else if (CompareBlockFunctions.isDirt(block)) {
			EncounterHelper.checkForEncounter(level, player, blockPos, EncounterHelper.EncounterType.DIRT_DIG, EncounterHelper.OriginType.BLOCK_BREAK);
		}
		else if (CompareBlockFunctions.isSand(block)) {
			EncounterHelper.checkForEncounter(level, player, blockPos, EncounterHelper.EncounterType.SAND_DIG, EncounterHelper.OriginType.BLOCK_BREAK);
		}
	}

	@SubscribeEvent
	public void onLivingDeath(LivingDeathEvent e) {
		LivingEntity livingEntity = e.getEntity();
		Level level = livingEntity.level;
		if (level.isClientSide) {
			return;
		}

		DamageSource damageSource = e.getSource();
		Entity sourceDirectEntity = damageSource.getDirectEntity();

		if (!(sourceDirectEntity instanceof Player)) {
			return;
		}

		Player player = (Player)sourceDirectEntity;
		BlockPos entityPos = livingEntity.blockPosition();

		if (livingEntity instanceof Player) {
			EncounterHelper.checkForEncounter(level, player, entityPos, EncounterHelper.EncounterType.PLAYER_DEATH, EncounterHelper.OriginType.LIVING_DEATH);
		}
		else if (livingEntity instanceof Monster) {
			EncounterHelper.checkForEncounter(level, player, entityPos, EncounterHelper.EncounterType.MONSTER_DEATH, EncounterHelper.OriginType.LIVING_DEATH);
		}
		else if (livingEntity instanceof Animal) {
			EncounterHelper.checkForEncounter(level, player, entityPos, EncounterHelper.EncounterType.ANIMAL_DEATH, EncounterHelper.OriginType.LIVING_DEATH);
		}
	}
}