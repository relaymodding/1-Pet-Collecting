package org.relaymodding.petcollecting.network;

import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import org.relaymodding.petcollecting.data.Constants;

public class PacketHandler {

    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            Constants.rl("main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );
    private static int counter = 0;

    public static void init() {
        INSTANCE.registerMessage(counter++, ClientSetPlayerSpeedPacket.class, ClientSetPlayerSpeedPacket::encode, ClientSetPlayerSpeedPacket::decode, ClientSetPlayerSpeedPacket::handle);
    }
}
