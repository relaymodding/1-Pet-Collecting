package org.relaymodding.petcollecting.abilities.pets;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.relaymodding.petcollecting.api.PetAbility;
import org.relaymodding.petcollecting.items.PetItem;
import org.relaymodding.petcollecting.util.MessageFunctions;
import org.relaymodding.petcollecting.util.RaytraceFunctions;

import java.util.List;
import java.util.stream.IntStream;


public class SandPetAbility implements PetAbility {
    private static final List<MutableComponent> useResponses = IntStream.range(0, 2).mapToObj(value -> "petcollecting.pet.response.sand." + value).map(Component::translatable).toList();

    @Override
    public boolean processPetAbility(Level level, Player player, BlockPos clickPos, ItemStack itemStack) {
        final BlockHitResult result = RaytraceFunctions.blockRaytrace(player, 10);
        if (result == null) return false;
        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, AABB.ofSize(result.getLocation(), 3, 3, 3), entity -> entity != player);
        entities.forEach(livingEntity -> {
            final BlockPos rootPos = livingEntity.getOnPos();
            final BlockPos.MutableBlockPos blockPos = new BlockPos.MutableBlockPos();
            livingEntity.setDeltaMovement(Vec3.ZERO);
            //hurtMarked is here so that in the event that we hit a player, we tell the client to sync speed from the server.
            //see line 181 in ServerEntity.
            livingEntity.hurtMarked = true;
            for (int i = 2; i > -3; --i) {
                for (int j = 4; j > 0; --j) {
                    for (int k = 2; k > -3; --k) {
                        blockPos.setWithOffset(rootPos, i, j, k);
                        if (!((i <= 1 && i >= -1) && (k <= 1 && k >= -1)) && !player.isShiftKeyDown()) {
                            //generates a square with empty spaces in the middle to trap the target.
                            FallingBlockEntity.fall(level, blockPos, Blocks.SAND.defaultBlockState());
                        }
                    }
                }
            }
        });
        boolean used = !entities.isEmpty();
        float unbreakingChance = itemStack.getOrCreateTag().getFloat(PetItem.UNBREAKING_CHANCE);
        if (used && level.random.nextFloat() > unbreakingChance) {
            itemStack.hurt(3, level.getRandom(), (ServerPlayer) player);
            MessageFunctions.sendClientMessage(player, useResponses.get(level.getRandom().nextInt(useResponses.size())).withStyle(ChatFormatting.DARK_PURPLE));
        }
        return used;
    }

    @Override
    public String descriptionId() {
        return "sand";
    }

    @Override
    public String foodId() {
        return descriptionId();
    }
}
