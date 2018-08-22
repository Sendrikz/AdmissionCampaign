package controller.commands;

import javax.servlet.http.HttpServletRequest;

public class LogOutCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest request) {
        request.getSession().invalidate();
        return "/jsp/login.jsp";
    }
}
