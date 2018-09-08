package net.byteexception.cloud.network;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

import javax.net.ssl.SSLException;
import java.net.InetSocketAddress;
import java.util.function.Consumer;

public class Client{


    private EventLoopGroup workerGroup;

    private SslContext sslContext;
    private final int port;
    private final String hostName;

    private final boolean EPOLL = Epoll.isAvailable();

    public Client( int port, String hostName ){
        this.port = port;
        this.hostName = hostName;

        try{
            sslContext = SslContext.newClientContext( InsecureTrustManagerFactory.INSTANCE );
        } catch (SSLException e){
            e.printStackTrace();
        }
    }

    public Channel connect( Consumer<Channel> init ){
        this.workerGroup = this.EPOLL ? new EpollEventLoopGroup() : new NioEventLoopGroup();

        final ChannelFuture future = new Bootstrap()
                .group( this.workerGroup )
                .channel( this.EPOLL ? EpollSocketChannel.class : NioSocketChannel.class )
                .handler( new ChannelInitializer<Channel>(){
                    @Override
                    protected void initChannel( Channel channel ) throws Exception{
                        if ( sslContext != null ){
                            channel.pipeline().addLast( sslContext.newHandler( channel.alloc(), hostName, port ) );
                            init.accept( channel );
                        }
                    }
                } ).connect( new InetSocketAddress( this.hostName, this.port ) ).syncUninterruptibly();
        return future.channel();
    }

    public void disconnect(){
        workerGroup.shutdownGracefully();
    }

}
