package org.relaymodding.petcollecting.data;

import net.minecraft.core.BlockPos;

import java.util.Date;
import java.util.HashMap;

public class Variables {
    public static final HashMap<String, Date> petEncounterCooldowns = new HashMap<String, Date>();
    public static final HashMap<String, Date> petUseCooldowns = new HashMap<String, Date>();

    public static final HashMap<String, BlockPos> lastPlayerPositions = new HashMap<String, BlockPos>();
}
