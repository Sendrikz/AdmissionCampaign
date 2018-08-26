package controller.commands;

import controller.CountGeneralGrade;
import model.enteties.Subject;
import model.enteties.User;
import org.apache.log4j.Logger;
import services.LoginService;
import services.SubjectService;
import services.UniversityService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
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
        log.info("start class LoginCommand execute()");
        String page;
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        User loginedUser = LoginService.checkLogin(login, password);
        request.getSession().setAttribute("loginedUser", loginedUser);
        log.info("Logined user is " + loginedUser);

        if (loginedUser == null) {
            request.getSession().setAttribute("successfulLogin", "no");
            return property.getProperty("path.page.index");
        }
//
//        if (request.getSession().getServletContext().getAttribute("userSession") != null){
//            log.debug("Attribute userSession != null");
//            ((HttpSession) request.getSession().getServletContext().getAttribute("userSession")).invalidate();
//        }
//
//        request.getSession().getServletContext().setAttribute("userSession", request.getSession());
//        log.debug("User session: " + request.getSession().getServletContext().getAttribute("userSession"));
        log.info("Successfully login");
        if (LoginService.getRoleById(loginedUser.getRole()).toUpperCase().equals(ADMIN_ROLE)) {
            log.info("User is admin");
            request.getSession().setAttribute("role", "Administrator");
            request.getSession().setAttribute("specialtyUserGradeHashMap", CountGeneralGrade.fillListOfSpecialtiesAndUsers());
            page = property.getProperty("path.page.adminMain");
        } else {
            log.info("User is student");
            request.getSession().setAttribute("role", "Student");
            ArrayList<String> listOfCities = new ArrayList<>(UniversityService.getAllCities());
            request.getSession().setAttribute("citiesList", listOfCities);
            page = property.getProperty("path.page.studentMain");
        }
        ArrayList<Subject> listOfSubjects = LoginService.getAllSubjects();
        request.getSession().setAttribute("subjectsList", listOfSubjects);
        HashMap<Subject, ArrayList<User>> subjectUserHashMap = new HashMap<>();
        for (Subject subject : listOfSubjects) {
            subjectUserHashMap.put(subject, SubjectService.getAllUsersWithUncheckedSubject(subject.getId()));
        }
        request.getSession().setAttribute("subjectUserHashMap", subjectUserHashMap);
        return page;
    }
}
