package controller.commands;

import model.enteties.Subject;
import model.enteties.User;
import org.apache.log4j.Logger;
import services.LoginService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

public class LoginCommand implements ActionCommand {

    private static final Logger log = Logger.getLogger(String.valueOf(LoginCommand.class));
    private static final String ADMIN_ROLE = "ADMINISTRATOR";
    private Properties property;

    LoginCommand() {
        property = new Properties();
        try (InputStream is = this.getClass().getClassLoader().
                getResourceAsStream("config.properties")){
            property.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        log.info("start class LoginCommand execute()");
        String login = request.getParameter("login");
        log.debug("Login: " + login);
        String password = request.getParameter("password");
        log.debug("Password: " + password);
        User loginedUser = LoginService.checkLogin(login, password);
        if (loginedUser != null) {
            log.info("Successfully login");
            if (LoginService.getRoleById(loginedUser.getRole()).toUpperCase().equals(ADMIN_ROLE)) {
                log.info("User is admin");
                request.getSession().setAttribute("admin", true);
                page = "/WEB-INF/view/adminMain.jsp";
            } else {
                log.info("User is student");
                request.getSession().setAttribute("admin", false);
                ArrayList<Subject> listOfSubjects = LoginService.getAllSubjects();
                log.info("List of subjects to display: " + listOfSubjects);
                request.getSession().setAttribute("subjectsList", listOfSubjects);
                page = "/WEB-INF/view/studentMain.jsp";
            }
            request.getSession().setAttribute("loginedUser", loginedUser);
            request.getSession().setAttribute("FirstName", loginedUser.getFirstName());
            request.getSession().setAttribute("LastName", loginedUser.getLastName());
            request.getSession().setAttribute("Patronymic", loginedUser.getPatronymic());
            request.getSession().setAttribute("City", loginedUser.getCity());
            request.getSession().setAttribute("Email", loginedUser.getEmail());
            log.info("Logined user is " + loginedUser);
        } else {
            log.info("Login fail");
            page = "loginFail";
        }
        return page;
    }
}
