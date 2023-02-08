package org.relaymodding.petcollecting.data;

import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Constants {
    public static final String MOD_ID = "petcollecting";
    public static final String NAME = "Pet Collecting WIP Name";

    public static final Logger LOG = LogManager.getLogger(NAME);

    public static String namespace(String path) {
        return MOD_ID + ":" + path;
    }

    public static ResourceLocation rl(String path) {
        return new ResourceLocation(namespace(path));
    }
}
