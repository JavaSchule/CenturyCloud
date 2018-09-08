package net.byteexception.cloud.master;

import lombok.Getter;
import net.byteexception.cloud.lib.CloudLibrary;
import net.byteexception.cloud.lib.ServiceProvider;
import net.byteexception.cloud.master.commands.CommandProvider;
import net.byteexception.cloud.master.handler.NetworkHandler;
import net.byteexception.cloud.master.handler.WrapperHandler;
import net.byteexception.cloud.master.protocoll.PacketInRegisterWrapper;
import net.byteexception.cloud.network.Server;
import net.byteexception.cloud.network.handler.PacketDecoder;
import net.byteexception.cloud.network.handler.PacketEncoder;
import net.byteexception.cloud.network.registry.PacketRegistry;

@Getter
public class CloudMaster implements ServiceProvider{

    public static CloudMaster instance;

    private final Server server = new Server( 50001 );

    private final CloudLibrary cloudLibrary = new CloudLibrary();
    private final WrapperHandler wrapperHandler = new WrapperHandler();

    public void start(){
        setUpMaster();
        registerPackets();
        cloudLibrary.getCommandExecutor().execute();

        Thread.currentThread().setName( "main" );
    }

    public void stop(){

    }


    private CloudMaster(){
        start();
        instance = this;
        Runtime.getRuntime().addShutdownHook( new Thread( this::stop ) );
    }

    public static void main( String[] args ){
        new CloudMaster();
    }

    private void setUpMaster(){
        this.server.bind( () -> {
        }, channel -> channel.pipeline().addLast( new PacketEncoder() ).addLast( new PacketDecoder() ).addLast( new NetworkHandler() ) );
    }

    private void registerPackets(){
        PacketRegistry.PacketDirection.IN.addPacket( 1000, PacketInRegisterWrapper.class );
    }
}
