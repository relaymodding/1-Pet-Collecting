package org.relaymodding.petcollecting.client;

import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec3;

public class ClientUtil {

	public static void setPlayerSpeed(Vec3 vec) {
        Minecraft.getInstance().player.setDeltaMovement(vec.add(Minecraft.getInstance().player.getDeltaMovement()));
    }
}
