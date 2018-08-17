package controller.commands;

import org.apache.log4j.Logger;
import services.LoginService;

import javax.servlet.http.HttpServletRequest;

public class RegistrationCommand implements ActionCommand {

    private static final Logger log = Logger.getLogger(String.valueOf(LoginCommand.class));

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        log.info("start class RegistrationCommand execute()");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String patronymic = request.getParameter("patronymic");
        String birthday = request.getParameter("birthday");
        String city = request.getParameter("city");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (LoginService.checkLogin (email, password) == null) {
            LoginService.addUser(firstName, lastName, patronymic, birthday, city, email, password);
            request.getSession().setAttribute("admin", false);
            page = "/WEB-INF/view/login.jsp:RegistrationSubmit";
        } else {
            page = "RegistrationFail";
        }
        return page;
    }
}
