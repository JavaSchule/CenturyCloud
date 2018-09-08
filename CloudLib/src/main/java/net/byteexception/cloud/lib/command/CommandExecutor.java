package net.byteexception.cloud.lib.command;

import lombok.Getter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CommandExecutor{

    @Getter
    public final List<Command> commands;

    public CommandExecutor(){
        this.commands = new ArrayList<>();
    }

    public void addCommand( Command command ){
        commands.add( command );
    }

    public void removeCommand( Command command ){
        commands.remove( command );
    }

    public void execute(){
        BufferedReader reader = new BufferedReader( new InputStreamReader( System.in ) );
        String line;
        try{
            while ( ( line = reader.readLine() ) != null ){
                if ( line.length() != 0 ){
                    for ( Command command : commands ){
                        command.execute( line.split( " " ) );
                    }
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void getDescriptions(){
        if ( commands.size() != 0 ){
            commands.forEach( command -> System.out.println( command.description() ) );
        } else{
            System.out.println( "Ees sind keine Commands verfügbar" );
        }
    }
}
