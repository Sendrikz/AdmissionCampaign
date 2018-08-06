package controller.commands;

import controller.helpers.SessionRequestContent;

public interface ActionCommand {

    public String execute(SessionRequestContent sqc);

}
