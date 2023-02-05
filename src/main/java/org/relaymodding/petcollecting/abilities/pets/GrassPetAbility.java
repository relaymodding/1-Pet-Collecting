package org.relaymodding.petcollecting.abilities.pets;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.relaymodding.petcollecting.data.Constants;
import org.relaymodding.petcollecting.items.PetItem;
import org.relaymodding.petcollecting.util.MessageFunctions;

import java.util.Arrays;
import java.util.List;

public class GrassPetAbility {
    private static final List<String> useResponses = Arrays.asList("Fertilized.", "Grow!");

    /*
        Simulates a bone meal use.
     */
    public static boolean processPetAbility(Level level, Player player, BlockPos clickPos, ItemStack itemStack, PetItem petItem) {
        if (!applyBonemeal(new ItemStack(Items.BONE_MEAL), level, clickPos, player)) {
            return false;
        }

        level.levelEvent(2005, clickPos, 0);
        player.swing(InteractionHand.MAIN_HAND);

        String useResponse = useResponses.get(Constants.RANDOM.nextInt(useResponses.size()));
        MessageFunctions.sendClientMessage(player, useResponse, ChatFormatting.DARK_GREEN);

        return true;
    }

	public static boolean applyBonemeal(ItemStack itemstack, Level world, BlockPos pos, Player player) {
		BlockState blockstate = world.getBlockState(pos);

		if (blockstate.getBlock() instanceof BonemealableBlock) {
			BonemealableBlock bonemealableblock = (BonemealableBlock)blockstate.getBlock();
			if (bonemealableblock.isValidBonemealTarget(world, pos, blockstate, world.isClientSide)) {
				if (world instanceof ServerLevel) {
					if (bonemealableblock.isBonemealSuccess(world, world.random, pos, blockstate)) {
						bonemealableblock.performBonemeal((ServerLevel)world, world.random, pos, blockstate);
					}

					if (!player.isCreative()) {
						itemstack.shrink(1);
					}
				}

				return true;
			}
		}

		return false;
	}
}
