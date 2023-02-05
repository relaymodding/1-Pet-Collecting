package org.relaymodding.petcollecting.abilities.pets;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.relaymodding.petcollecting.data.Constants;
import org.relaymodding.petcollecting.items.PetItem;
import org.relaymodding.petcollecting.util.CompareBlockFunctions;
import org.relaymodding.petcollecting.util.MessageFunctions;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class WoodPetAbility {
    private static final List<String> useResponses = Arrays.asList("I AM GROOT", "Treeppear!", "I bless thou, little sapling.");

    private static final List<Block> saplingBlocks = Arrays.asList(Blocks.OAK_SAPLING, Blocks.ACACIA_SAPLING, Blocks.SPRUCE_SAPLING, Blocks.BIRCH_SAPLING);

    /*
        Plants a sapling and grows a tree from it.
     */
    public static boolean processPetAbility(Level level, Player player, BlockPos clickPos, ItemStack itemStack, PetItem petItem) {
        if (!CompareBlockFunctions.isDirt(level.getBlockState(clickPos).getBlock())) {
            return false;
        }

        SaplingBlock saplingBlock = (SaplingBlock)saplingBlocks.get(Constants.RANDOM.nextInt(saplingBlocks.size()));
        BlockPos saplingPos = clickPos.above().immutable();
        BlockState saplingState = saplingBlock.defaultBlockState().setValue(SaplingBlock.STAGE, 1);

        level.setBlock(saplingPos, saplingState, 3);
        saplingBlock.advanceTree((ServerLevel)level, saplingPos, saplingState, Constants.RANDOM_SOURCE);

        String useResponse = useResponses.get(Constants.RANDOM.nextInt(useResponses.size()));
        MessageFunctions.sendClientMessage(player, useResponse, ChatFormatting.DARK_GREEN);

        return true;
    }
}
