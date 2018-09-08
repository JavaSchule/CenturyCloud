package net.byteexception.cloud.lib;

import lombok.Getter;
import net.byteexception.cloud.lib.command.CommandExecutor;

public class CloudLibrary {

    @Getter
    public final CommandExecutor commandExecutor = new CommandExecutor();


    public  CloudLibrary(){
        loadLibrary();
    }

    private void loadLibrary(){
    }

    public static void main(String[] args){
        new CloudLibrary();
    }

}
