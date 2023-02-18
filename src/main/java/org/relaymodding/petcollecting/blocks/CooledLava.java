package org.relaymodding.petcollecting.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FrostedIceBlock;
import net.minecraft.world.level.block.state.BlockState;

public class CooledLava extends FrostedIceBlock {

	public CooledLava(Properties pProperties) {
		super(pProperties);
	}

	@Override
	protected void melt(BlockState pState, Level pLevel, BlockPos pPos) {
		pLevel.setBlockAndUpdate(pPos, Blocks.LAVA.defaultBlockState());
		pLevel.neighborChanged(pPos, Blocks.LAVA, pPos);
	}
}
