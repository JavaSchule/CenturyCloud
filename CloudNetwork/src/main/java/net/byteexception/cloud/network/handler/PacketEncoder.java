package net.byteexception.cloud.network.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import net.byteexception.cloud.network.packet.Packet;
import net.byteexception.cloud.network.registry.PacketRegistry;

public class PacketEncoder extends MessageToByteEncoder<Packet>{

    protected void encode( final ChannelHandlerContext ctx, final Packet packet, final ByteBuf byteBuf) throws Exception {
        final int id = PacketRegistry.getIdByPacket(packet, PacketRegistry.PacketDirection.OUT);
        if (id == -1) {
            new NullPointerException("Could not find id from packet " + packet.getClass().getSimpleName() + "!").printStackTrace();
        } else {
            byteBuf.writeInt(id);
            packet.write(new ByteBufOutputStream(byteBuf));
        }
    }
}