package org.relaymodding.petcollecting.abilities.pets;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.relaymodding.petcollecting.api.PetAbility;
import org.relaymodding.petcollecting.util.MessageFunctions;

import java.util.List;
import java.util.stream.IntStream;

public class GrassPetAbility implements PetAbility {
    private static final List<MutableComponent> useResponses = IntStream.range(0, 2).mapToObj(value -> "petcollecting.pet.response.grass." + value).map(Component::translatable).toList();

    /*
        Simulates a bone meal use.
     */
    public boolean processPetAbility(Level level, Player player, BlockPos clickPos, ItemStack itemStack) {
        if (!applyBonemeal(level, clickPos)) {
            return false;
        }

        level.levelEvent(2005, clickPos, 0);
        player.swing(InteractionHand.MAIN_HAND);

        MutableComponent useResponse = useResponses.get(level.getRandom().nextInt(useResponses.size()));
        MessageFunctions.sendClientMessage(player, useResponse.withStyle(ChatFormatting.DARK_GREEN));

        return true;
    }

    public static boolean applyBonemeal(Level world, BlockPos pos) {
        BlockState blockstate = world.getBlockState(pos);

        if (blockstate.getBlock() instanceof BonemealableBlock bonemealableblock) {
            if (bonemealableblock.isValidBonemealTarget(world, pos, blockstate, world.isClientSide)) {
                if (world instanceof ServerLevel level) {
                    if (bonemealableblock.isBonemealSuccess(level, level.random, pos, blockstate)) {
                        bonemealableblock.performBonemeal(level, level.random, pos, blockstate);
                    }
                }

                return true;
            }
        }

        return false;
    }

    @Override
    public String descriptionId() {
        return "grass";
    }

    @Override
    public String foodId() {
        return descriptionId();
    }
}
