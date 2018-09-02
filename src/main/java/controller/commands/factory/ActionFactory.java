package controller.commands.factory;

import controller.commands.ActionCommand;
import controller.commands.command_enum.CommandEnum;
import controller.commands.EmptyCommand;

import javax.servlet.http.HttpServletRequest;

public class ActionFactory {

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
