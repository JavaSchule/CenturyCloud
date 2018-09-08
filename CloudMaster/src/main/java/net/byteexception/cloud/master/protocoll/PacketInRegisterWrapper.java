package net.byteexception.cloud.master.protocoll;

import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.Channel;
import net.byteexception.cloud.master.CloudMaster;
import net.byteexception.cloud.master.clients.Wrapper;
import net.byteexception.cloud.network.packet.Packet;

import java.io.IOException;

public class PacketInRegisterWrapper implements Packet{

    private String name;
    private int id;

    @Override
    public void read( ByteBufInputStream byteBuf ){
        try{
            this.name = byteBuf.readUTF();
            this.id = byteBuf.readInt();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public Packet handle( Channel channel ){

        Wrapper wrapper = new Wrapper( this.name, this.id, channel );
        CloudMaster.instance.getWrapperHandler().getWrappers().add( wrapper );
        System.out.println("Wrapper " + name + " registered");
        return null;
    }
}
