package controller.commands;

import controller.Util;
import controller.pagination.Pagination;
import model.enteties.User;
import org.apache.log4j.Logger;
import services.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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

        log.info("Successfully login");
        if (LoginService.getRoleById(loginedUser.getRole()).toUpperCase().equals(ADMIN_ROLE)) {
            log.info("User is admin");
            request.getSession().setAttribute("role", "Administrator");
            Util.generatePaginationSpecialties(request, Pagination.FIRST_PAGE,
                    Pagination. FIVE_RECORDS_PER_PAGE);
            page = property.getProperty("path.page.adminMain");
        } else {
            log.info("User is student");
            request.getSession().setAttribute("role", "Student");
            ArrayList<String> listOfCities = new ArrayList<>(UniversityService.getAllCities());
            request.getSession().setAttribute("citiesList", listOfCities);
            Util.checkIfDisplayUserSubjectsAndGrade(request);
            Util.checkIfDisplayCongratulationOnSpecialty(request);
            page = property.getProperty("path.page.studentMain");
        }

        Util.generateListOfSubjectsForUserAndUsersWhichPassThem(request);
        Util.generateListOfUsersAndTheirRateBySpecialties(request);
        return page;
    }
}
