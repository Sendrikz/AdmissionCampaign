package controller.commands;

import org.apache.log4j.Logger;
import services.LoginService;

import javax.servlet.http.HttpServletRequest;

public class LoginCommand implements ActionCommand {

    private static final Logger log = Logger.getLogger(String.valueOf(LoginCommand.class));

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        log.info("start class LoginCommand execute()");
        String login = request.getParameter("login");
        log.debug("Login: " + login);
        String password = request.getParameter("password");
        log.debug("Password: " + password);
        if (LoginService.checkLogin(login, password)) {
            log.info("Successfully login");
            request.setAttribute("user", login);
            page = "/WEB-INF/view/main.jsp";
        } else {
            log.info("Login fail");
            request.setAttribute("errorLoginPassMessage",
                    "Invalid login or password");
            page = "/WEB-INF/view/error.jsp";
        }
        return page;
    }
}
