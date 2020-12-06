package com.verbovskiy.server.controller.command;

/**
 * The type Command provider.
 *
 * @author Verbovskiy Sergei
 * @version 1.0
 */
public class CommandProvider {
    /**
     * Define command action command.
     *
     * @param commandName the command name
     * @return the action command
     */
    public static ActionCommand defineCommand(String commandName){
        return CommandType.valueOf(commandName.toUpperCase()).getCommand();
    }
}
