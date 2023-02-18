package org.relaymodding.petcollecting.abilities.pets;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.relaymodding.petcollecting.api.PetAbility;

import com.mojang.math.Vector3f;

public class StonePetAbility implements PetAbility {
    @Override
    public boolean processPetAbility(Level level, Player player, BlockPos clickPos, ItemStack itemStack) {
    	BlockPos current = player.blockPosition();
    	Vector3f step = player.getDirection().step();
    	Vec3i directionStep = new Vec3i(step.x(), step.y(), step.z());
    	for (int i = 0; i < 10; i++) {
    		level.destroyBlock(current, true);
    		level.destroyBlock(current.above(), true);
    		current = current.offset(directionStep);
    	}	    	
        return true;
    }

    @Override
    public String descriptionId() {
        return "stone";
    }

    @Override
    public String foodId() {
        return descriptionId();
    }
}
