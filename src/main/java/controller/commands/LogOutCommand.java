package controller.commands;

import utils.Strings;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LogOutCommand implements ActionCommand {

    @Override
    public String execute(HttpServletRequest request) {
        //invalidate the session if exists
        HttpSession session = request.getSession(false);
        if(session != null){
            session.invalidate();
        }
        return Strings.LOG_OUT;
    }

}
