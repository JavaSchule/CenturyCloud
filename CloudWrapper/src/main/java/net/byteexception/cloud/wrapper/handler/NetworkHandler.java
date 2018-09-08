package net.byteexception.cloud.wrapper.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.byteexception.cloud.network.packet.Packet;

public class NetworkHandler extends SimpleChannelInboundHandler<Packet>{

    /**
     * Handle the incoming protocoll
     *
     * @param ctx    ChannelHandler context for handling channel
     * @param packet Packet which you want to handle
     * @throws Exception
     */
    @Override
    protected void channelRead0( ChannelHandlerContext ctx, Packet packet ) throws Exception{
        packet.handle( ctx.channel() );
    }
}