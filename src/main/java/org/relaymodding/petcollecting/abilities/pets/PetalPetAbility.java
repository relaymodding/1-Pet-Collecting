package org.relaymodding.petcollecting.abilities.pets;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.relaymodding.petcollecting.api.PetAbility;
import org.relaymodding.petcollecting.util.CompareBlockFunctions;

import java.util.Arrays;
import java.util.List;

public class PetalPetAbility implements PetAbility {

    private static final List<Block> blocks = Arrays.asList(Blocks.FLOWERING_AZALEA, Blocks.CORNFLOWER, Blocks.ORANGE_TULIP, Blocks.PINK_TULIP, Blocks.RED_TULIP, Blocks.WHITE_TULIP, Blocks.CORNFLOWER, Blocks.ALLIUM, Blocks.BLUE_ORCHID, Blocks.DANDELION, Blocks.LILY_OF_THE_VALLEY, Blocks.LILAC);

    /*
        Plants a sapling and grows a tree from it.
     */
    public boolean processPetAbility(Level level, Player player, BlockPos clickPos, ItemStack itemStack) {

        if (CompareBlockFunctions.isFlowerPlantable(level.getBlockState(clickPos).getBlock())) {

            final BlockPos flowerPos = clickPos.above();

            if (level.getBlockState(flowerPos).isAir()) {

                final Block flower = blocks.get(level.getRandom().nextInt(blocks.size()));
                level.setBlock(flowerPos, flower.defaultBlockState(), 3);

                if (!level.isClientSide) {
                    level.levelEvent(1505, flowerPos, 0);
                }

                return true;
            }
        }

        return false;
    }

    @Override
    public String descriptionId() {
        return "petal";
    }

    @Override
    public String foodId() {
        return descriptionId();
    }
}
