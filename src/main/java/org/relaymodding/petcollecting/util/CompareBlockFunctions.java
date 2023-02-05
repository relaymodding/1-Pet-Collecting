package org.relaymodding.petcollecting.util;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;

public class CompareBlockFunctions {
	@SuppressWarnings("deprecation")
	public static boolean blockIsInRegistryHolder(Block block, TagKey<Block> tagKey) {
		return block.builtInRegistryHolder().is(tagKey);
	}

	public static boolean isTreeLog(Block block) {
		return blockIsInRegistryHolder(block, BlockTags.LOGS) || block instanceof RotatedPillarBlock;
	}

	public static boolean isStone(Block block) {
		return blockIsInRegistryHolder(block, BlockTags.BASE_STONE_OVERWORLD);
	}

	public static boolean isDirt(Block block) {
		return blockIsInRegistryHolder(block, BlockTags.DIRT);
	}

	public static boolean isSand(Block block) {
		return blockIsInRegistryHolder(block, BlockTags.SAND);
	}
}
