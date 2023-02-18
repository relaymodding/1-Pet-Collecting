package org.relaymodding.petcollecting.events;

import org.relaymodding.petcollecting.Main;
import org.relaymodding.petcollecting.blocks.PCBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;

public class EffectEvent {
	public static void onPlayerTick(PlayerTickEvent event) {
		if (event.player.hasEffect(Main.LAVA_WALKER.get())) {
			Player pLiving = event.player;
			Level pLevel = event.player.level;
			if (pLiving.isOnGround()) {
				BlockState blockstate = PCBlocks.COOLED_LAVA.get().defaultBlockState();
				float f = (float) Math.min(16, 3);
				BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
				BlockPos pPos = pLiving.blockPosition();
				for (BlockPos blockpos : BlockPos.betweenClosed(pPos.offset((double) (-f), -1.0D, (double) (-f)),
						pPos.offset((double) f, -1.0D, (double) f))) {
					if (blockpos.closerToCenterThan(pLiving.position(), (double) f)) {
						blockpos$mutableblockpos.set(blockpos.getX(), blockpos.getY() + 1, blockpos.getZ());
						BlockState blockstate1 = pLevel.getBlockState(blockpos$mutableblockpos);
						if (blockstate1.isAir()) {
							BlockState blockstate2 = pLevel.getBlockState(blockpos);
							boolean isFull = blockstate2.getBlock() == Blocks.LAVA
									&& blockstate2.getValue(LiquidBlock.LEVEL) == 0;
							if (blockstate2.getMaterial() == Material.LAVA && isFull
									&& blockstate.canSurvive(pLevel, blockpos)
									&& pLevel.isUnobstructed(blockstate, blockpos, CollisionContext.empty())
									&& !net.minecraftforge.event.ForgeEventFactory.onBlockPlace(
											pLiving, net.minecraftforge.common.util.BlockSnapshot
													.create(pLevel.dimension(), pLevel, blockpos),
											net.minecraft.core.Direction.UP)) {
								pLevel.setBlockAndUpdate(blockpos, blockstate);
								pLevel.scheduleTick(blockpos, PCBlocks.COOLED_LAVA.get(),
										Mth.nextInt(pLiving.getRandom(), 60, 120));
							}
						}
					}
				}

			}
		}
	}
}
