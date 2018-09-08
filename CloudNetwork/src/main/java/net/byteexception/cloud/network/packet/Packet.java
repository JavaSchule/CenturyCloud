package net.byteexception.cloud.network.packet;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.Channel;

import java.io.IOException;

public interface Packet{

    default void read( final ByteBufInputStream byteBuf ) throws IOException{
    }

    default void write( final ByteBufOutputStream byteBuf ) throws IOException{
    }

    default Packet handle( final Channel channel ) throws IOException{
        return new SuccesPacket();
    }

}
