package net.byteexception.cloud.master.commands;

import net.byteexception.cloud.lib.command.Command;
import net.byteexception.cloud.master.CloudMaster;

import java.util.List;

public class CommandProvider{

    private final List<Command> commands;

    public CommandProvider(){
        commands = CloudMaster.instance.getCloudLibrary().getCommandExecutor().getCommands();
    }
}