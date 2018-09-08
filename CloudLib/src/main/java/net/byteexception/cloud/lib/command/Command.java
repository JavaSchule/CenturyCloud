package net.byteexception.cloud.lib.command;

public interface Command{

    void execute( String[] arg0 );

    String description();
}
