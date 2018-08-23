package controller.commands;

import model.enteties.Subject;
import model.enteties.User;
import org.apache.log4j.Logger;
import services.LoginService;
import services.SubjectService;
import services.UniversityService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Properties;

public class LoginCommand implements ActionCommand {

    private static final Logger log = Logger.getLogger(LoginCommand.class);
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
        request.getSession().setAttribute("loginedUser", loginedUser);
        log.info("Logined user is " + loginedUser);
        if (loginedUser != null) {
            log.info("Successfully login");
            if (LoginService.getRoleById(loginedUser.getRole()).toUpperCase().equals(ADMIN_ROLE)) {
                log.info("User is admin");
                request.getSession().setAttribute("admin", true);
                page = "/jsp/admin/adminMain.jsp";
            } else {
                log.info("User is student");
                request.getSession().setAttribute("admin", false);
                ArrayList<String> listOfCities = new ArrayList<>(UniversityService.getAllCities());
                log.info("List of cities to display: " + listOfCities);
                request.getSession().setAttribute("citiesList", listOfCities);
                page = "/jsp/student/studentMain.jsp";
            }
            ArrayList<Subject> listOfSubjects = LoginService.getAllSubjects();
            log.info("List of subjects to display: " + listOfSubjects);
            request.getSession().setAttribute("subjectsList", listOfSubjects);
            HashMap<Subject, ArrayList<User>> subjectUserHashMap = new HashMap<>();
            for (Subject subject : listOfSubjects) {
                subjectUserHashMap.put(subject, SubjectService.getAllUsersWithUncheckedSubject(subject.getId()));
            }
            log.debug("HashMap of subjects and users: " + subjectUserHashMap);
            request.getSession().setAttribute("subjectUserHashMap", subjectUserHashMap);
        } else {
            log.info("Login fail");
            page = "loginFail";
        }
        return page;
    }
}
