package controller.commands;

import javax.servlet.http.HttpServletRequest;

public class ReturnToMainCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest request) {
        return "/jsp/student/studentMain.jsp";
    }
}
