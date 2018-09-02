package controller.commands;

import services.exceptions.NoSuchRoleException;
import utils.pagination.Pagination;
import utils.property_loaders.LoadConfigProperty;
import utils.Strings;
import utils.Util;
import model.enteties.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import services.LoginService;
import services.UniversityService;
import services.exceptions.NoSuchUserException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

public class LoginCommand implements ActionCommand {

    private static final Logger log = Logger.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        log.info("Start class LoginCommand execute()");

        String page;
        User loginedUser;

        try {
            loginedUser = checkLogin(request);
        } catch (NoSuchUserException e) {
            log.error(e.getMessage());
            return loginFailRedirect(request);
        }

        page = authorizeUser(request, loginedUser);

        Util util = new Util();
        util.generateListOfSubjectsForUserAndUsersWhichPassThem(request);
        util.generateListOfUsersAndTheirRateBySpecialties(request);

        return page;
    }

    private String loginFailRedirect(HttpServletRequest request) {
        request.getSession().setAttribute(Strings.SUCCESSFUL_LOGIN, Strings.NO);
        return LoadConfigProperty.getInstance().getConfigProperty(Strings.PATH_PAGE_INDEX);
    }

    private User checkLogin(HttpServletRequest request) throws NoSuchUserException {
        try (LoginService loginService = new LoginService()) {
            String login = request.getParameter(Strings.LOGIN);
            String password = DigestUtils.md5Hex(request.getParameter(Strings.PASSWORD));
            User loginedUser = loginService.checkLogin(login, password);

            request.getSession().setAttribute(Strings.LOGINED_USER, loginedUser);
            log.info("Logined user is " + loginedUser);

//            if (request.getSession().getServletContext().getAttribute(login) != null) {
//                ((HttpSession) request.getSession().getServletContext().getAttribute(login)).invalidate();
//            }
//            request.getSession().getServletContext().setAttribute(login, request.getSession());

            return loginedUser;
        }
    }

    private String authorizeUser(HttpServletRequest request, User loginedUser) {
        log.info("Successfully login");

        try (LoginService loginService = new LoginService()) {

            if (loginService.getRoleById(loginedUser.getRole()).toUpperCase()
                    .equals(Strings.ADMIN_ROLE)) {
                log.info("User is admin");
                request.getSession().setAttribute(Strings.ROLE, Strings.ADMIN_ROLE);
                new Util().generatePaginationSpecialties(request, Pagination.FIRST_PAGE,
                        Pagination.FIVE_RECORDS_PER_PAGE);

                return LoadConfigProperty.getInstance().getConfigProperty(Strings.PATH_PAGE_ADMIN_MAIN);
            }

        } catch (NoSuchRoleException e) {
            log.error(e.getMessage());
        }

        log.info("User is student");
        request.getSession().setAttribute(Strings.ROLE, Strings.STUDENT);
        try (UniversityService universityService = new UniversityService()) {
            ArrayList<String> listOfCities = new ArrayList<>(universityService.getAllCities());
            request.getSession().setAttribute(Strings.CITIES_LIST, listOfCities);
        }

        Util util = new Util();
        util.checkIfDisplayUserSubjectsAndGrade(request);
        util.checkIfDisplayCongratulationOnSpecialty(request);

        return LoadConfigProperty.getInstance().getConfigProperty(Strings.PATH_PAGE_STUDENT_MAIN);
    }

}
