package controller.commands;

import utils.Strings;

import javax.servlet.http.HttpServletRequest;

public class LogOutCommand implements ActionCommand {

    @Override
    public String execute(HttpServletRequest request) {
        request.getSession().invalidate();
        return Strings.LOG_OUT;
    }

}
