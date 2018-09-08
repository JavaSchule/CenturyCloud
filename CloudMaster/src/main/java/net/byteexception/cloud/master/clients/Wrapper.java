package net.byteexception.cloud.master.clients;

import io.netty.channel.Channel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Wrapper{

    private String name;
    private int id;
    private Channel channel;

}
