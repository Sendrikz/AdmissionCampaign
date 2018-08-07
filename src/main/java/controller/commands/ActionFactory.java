package controller.commands;

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
