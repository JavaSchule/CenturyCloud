package net.byteexception.cloud.master.commands;

import net.byteexception.cloud.lib.command.Command;

public class CommandTest implements Command{
    @Override
    public void execute( String[] args){
        if(args[0].equalsIgnoreCase( "test" )){
            System.out.println("test");
        }
    }

    @Override
    public String description(){
        return null;
    }
}
