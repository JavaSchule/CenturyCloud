package net.byteexception.cloud.network;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.util.SelfSignedCertificate;

import javax.net.ssl.SSLException;
import java.security.cert.CertificateException;
import java.util.function.Consumer;

public class Server{

    private int port;

    private final boolean EPOLL = Epoll.isAvailable();

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private SslContext sslCtx;

    public Server( int port ){
        this.port = port;
        try{
            SelfSignedCertificate ssc = new SelfSignedCertificate();
            sslCtx = SslContext.newServerContext( ssc.certificate(), ssc.privateKey() );
            ssc.delete();
        } catch (CertificateException | SSLException e){
            e.printStackTrace();
        }
    }

    public void bind( Runnable ready, Consumer<Channel> init ){
        new Thread( () -> {
            this.bossGroup = this.EPOLL ? new EpollEventLoopGroup() : new NioEventLoopGroup();
            this.workerGroup = this.EPOLL ? new EpollEventLoopGroup() : new NioEventLoopGroup();

            final ChannelFuture future = new ServerBootstrap().group( this.bossGroup, this.workerGroup ).channel( this.EPOLL ? EpollServerSocketChannel.class : NioServerSocketChannel.class ).childHandler( new ChannelInitializer<Channel>(){
                @Override
                protected void initChannel( Channel channel ) throws Exception{
                    if ( sslCtx != null ){
                        channel.pipeline().addLast( sslCtx.newHandler( channel.alloc() ) );
                    }
                    init.accept( channel );
                }
            } ).bind( this.port ).syncUninterruptibly();
            ready.run();
            future.channel().closeFuture().syncUninterruptibly();
        } ).start();
    }

    public void shutdown(){
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

}
