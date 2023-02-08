package org.relaymodding.petcollecting.util;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class RaytraceFunctions {


    /**
     * Used to raytrace a block from where the player is looking at.
     * @param player The player from which to perform the raytrace.
     * @param radius The maximum radius (in blocks) the raytrace can reach to. Keep in mind render distance also needs to be taken into account.
     * @return A nullable BlockHitResult.
     */
    @Nullable
    public static BlockHitResult blockRaytrace(Player player, double radius) {
        final Vec3 startPos = player.position().add(0, player.getStandingEyeHeight(player.getPose(), player.getDimensions(player.getPose())), 0);
        final BlockHitResult hitresult = player.level.clip(new ClipContext(startPos, startPos.add(player.getLookAngle().multiply(radius, radius, radius)), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));
        if (hitresult.getType() == HitResult.Type.MISS) return null;
        return hitresult;
    }
}
