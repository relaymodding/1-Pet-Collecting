package org.relaymodding.petcollecting.abilities.pets;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.relaymodding.petcollecting.api.PetAbility;
import org.relaymodding.petcollecting.util.CompareBlockFunctions;
import org.relaymodding.petcollecting.util.MessageFunctions;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class WoodPetAbility implements PetAbility {
    private static final List<MutableComponent> useResponses = IntStream.range(0, 3).mapToObj(value -> "petcollecting.pet.response.wood." + value).map(Component::translatable).toList();

    private static final List<Block> saplingBlocks = Arrays.asList(Blocks.OAK_SAPLING, Blocks.ACACIA_SAPLING, Blocks.SPRUCE_SAPLING, Blocks.BIRCH_SAPLING);

    /*
        Plants a sapling and grows a tree from it.
     */
    public boolean processPetAbility(Level level, Player player, BlockPos clickPos, ItemStack itemStack) {

        final BlockPos saplingPos = clickPos.above().immutable();

        if (!CompareBlockFunctions.isDirt(level.getBlockState(clickPos).getBlock()) || !level.getBlockState(saplingPos).isAir()) {
            return false;
        }

        SaplingBlock saplingBlock = (SaplingBlock) saplingBlocks.get(level.getRandom().nextInt(saplingBlocks.size()));
        BlockState saplingState = saplingBlock.defaultBlockState().setValue(SaplingBlock.STAGE, 1);

        level.setBlock(saplingPos, saplingState, 3);
        saplingBlock.advanceTree((ServerLevel) level, saplingPos, saplingState, level.getRandom());

        MutableComponent useResponse = useResponses.get(level.getRandom().nextInt(useResponses.size()));
        MessageFunctions.sendClientMessage(player, useResponse.withStyle(ChatFormatting.DARK_GREEN));

        return true;
    }

    @Override
    public String descriptionId() {
        return "wood";
    }

    @Override
    public String foodId() {
        return descriptionId();
    }
}
