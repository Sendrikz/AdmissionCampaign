package controller.commands;

import utils.Strings;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Class to invalidate session if user decided to log out
 * @author Olha Yuryreva
 * @version 1.0
 */
public class LogOutCommand implements ActionCommand {

    /**
     * Invalidate the session if exists
     * @param request HttpServletRequest
     * @return String path to page
     */
    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session != null){
            session.invalidate();
        }
        return Strings.LOG_OUT;
    }

}
