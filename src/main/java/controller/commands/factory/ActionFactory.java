package controller.commands.factory;

import controller.commands.ActionCommand;
import controller.commands.command_enum.CommandEnum;
import controller.commands.EmptyCommand;

import javax.servlet.http.HttpServletRequest;

/**
 * To determine the type and create the command instance, use the
 * Factory Method template in the ActionFactory class,
 * it extracts the value of the command parameter from the query and,
 * based on it, retrieves the corresponding object-command
 * @author Olha Yuryeva
 * @version 1.0
 */

public class ActionFactory {

    /**
     * @param request HttpServletRequest
     * @return ActionCommand
     * @see CommandEnum
     */

    public ActionCommand defineCommand(HttpServletRequest request) {
        ActionCommand currentCommand = new EmptyCommand();
        String action = request.getParameter("command");
        if (action.isEmpty()) {
            return currentCommand;
        }

        CommandEnum currentEnum = CommandEnum.valueOf(action.trim().toUpperCase());
        currentCommand = currentEnum.getCommand();

        return currentCommand;
    }

}
