package controller.commands;

import controller.helpers.SessionRequestContent;

import java.util.Arrays;

public class ActionFactory {

    public ActionCommand defineCommand(SessionRequestContent src) {
        ActionCommand currentCommand = new EmptyCommand();
        String action = Arrays.toString(src.getParameter("command"));
        if (action.isEmpty()) {
            return currentCommand;
        }

        CommandEnum currentEnum = CommandEnum.valueOf(action.trim().toUpperCase());
        currentCommand = currentEnum.getCommand();

        return currentCommand;
    }
}
