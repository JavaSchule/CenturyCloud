package net.byteexception.cloud.wrapper;

import io.netty.channel.Channel;
import lombok.Getter;
import net.byteexception.cloud.lib.ServiceProvider;
import net.byteexception.cloud.network.Client;
import net.byteexception.cloud.network.handler.PacketDecoder;
import net.byteexception.cloud.network.handler.PacketEncoder;
import net.byteexception.cloud.network.registry.PacketRegistry;
import net.byteexception.cloud.wrapper.handler.NetworkHandler;
import net.byteexception.cloud.wrapper.protocoll.PacketOutRegisterWrapper;

import java.util.UUID;

public class CloudWrapper implements ServiceProvider{

    private final Client client = new Client( 50001, "127.0.0.1" );

    @Getter
    private Channel channel;

    public void start(){
        setUpClient();
        registerPackets();
    }

    public void stop(){

    }

    private CloudWrapper(){
        start();
        Runtime.getRuntime().addShutdownHook( new Thread( this::stop ) );
        getChannel().writeAndFlush( new PacketOutRegisterWrapper( "Wrapper-1", 1 ) );
    }

    public static void main( String[] args ){
        new CloudWrapper();
    }

    private void setUpClient(){
        this.channel = this.client.connect( channel -> channel.pipeline().addLast( new PacketEncoder() ).addLast( new PacketDecoder() ).addLast( new NetworkHandler() ) );
    }

    private void registerPackets(){
        PacketRegistry.PacketDirection.OUT.addPacket( 1000, PacketOutRegisterWrapper.class );
    }
}
