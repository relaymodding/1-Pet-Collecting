package org.relaymodding.petcollecting.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;
import org.relaymodding.petcollecting.client.ClientUtil;

import java.util.function.Supplier;

public class ClientSetPlayerSpeedPacket {

    private final double x;
    private final double y;
    private final double z;

    public ClientSetPlayerSpeedPacket(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
    }

    public static ClientSetPlayerSpeedPacket decode(FriendlyByteBuf buf) {
        return new ClientSetPlayerSpeedPacket(buf.readDouble(), buf.readDouble(), buf.readDouble());
    }

    /**
     * Speed needs to be set on the client so... here we are!
     * @param netContext
     */
    public void handle(Supplier<NetworkEvent.Context> netContext) {
        final NetworkEvent.Context context = netContext.get();
        context.enqueueWork(() -> ClientUtil.setPlayerSpeed(new Vec3(x, y, z)));
        context.setPacketHandled(true);
    }

}
