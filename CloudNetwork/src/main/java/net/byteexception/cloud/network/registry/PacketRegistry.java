package net.byteexception.cloud.network.registry;

import lombok.Getter;
import net.byteexception.cloud.network.packet.Packet;

import java.util.HashMap;
import java.util.Map;

public class PacketRegistry{

    public static Packet getPacketById( final int id, final PacketDirection direction) throws IllegalAccessException, InstantiationException {
        return direction.getPackets().get(id).newInstance();
    }

    public static int getIdByPacket(final Packet packet, final PacketDirection direction) {
        return direction.getPackets().entrySet().stream().filter(entry -> entry.getValue() == packet.getClass()).map(Map.Entry::getKey).findFirst().orElse(-1);
    }

    public enum PacketDirection {
        IN,
        OUT;

        @Getter
        private final HashMap<Integer, Class<? extends Packet>> packets = new HashMap<>();

        public void addPacket(final int id, final Class<? extends Packet> packet) {
            this.packets.put(id, packet);
        }
    }
}

