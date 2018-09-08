package net.byteexception.cloud.master.handler;

import lombok.Getter;
import net.byteexception.cloud.master.clients.Wrapper;

import java.util.ArrayList;
import java.util.List;

public class WrapperHandler{

    @Getter
    private final List<Wrapper> wrappers;

    public WrapperHandler(){
        wrappers = new ArrayList<>();
    }
}
