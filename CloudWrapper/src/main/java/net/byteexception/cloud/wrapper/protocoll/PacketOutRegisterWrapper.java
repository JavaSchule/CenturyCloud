package net.byteexception.cloud.wrapper.protocoll;

import io.netty.buffer.ByteBufOutputStream;
import net.byteexception.cloud.network.packet.Packet;

import java.io.IOException;

public class PacketOutRegisterWrapper implements Packet{

    private String name;
    private int id;


    public PacketOutRegisterWrapper( String name, int id ){
        this.name = name;
        this.id = id;
    }
    public PacketOutRegisterWrapper (){

    }

    @Override
    public void write( ByteBufOutputStream byteBuf ){
        try{
            byteBuf.writeUTF( this.name );
            byteBuf.writeInt( this.id );
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
