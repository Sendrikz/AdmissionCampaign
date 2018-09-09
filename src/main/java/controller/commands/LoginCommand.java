package controller.commands;

import utils.exceptions.PatternCheckFailException;
import services.exceptions.NoSuchRoleException;
import utils.pagination.Pagination;
import utils.patterns.PatternConstructor;
import utils.patterns.Patterns;
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

/**
 * Class is implemented by the Authorization system
 * @author Olha Yuryeva
 * @version 1.0
 */

public class LoginCommand implements ActionCommand {

    private static final Logger log = Logger.getLogger(LoginCommand.class);
    private Boolean isTest;


    public LoginCommand() {
        this.isTest = false;
    }

    /**
     * Constructor for tests. It helps to get connection exactly to test database
     * @param isTest Boolean
     */
    public LoginCommand(Boolean isTest) {
        this.isTest = isTest;
    }

    /**
     *
     * @param request HttpServletRequest
     * @return String path to page
     */
    @Override
    public String execute(HttpServletRequest request) {
        log.info("Start class LoginCommand execute()");

        String page;
        User loginedUser;

        try {

            loginedUser = checkLogin(request);

        } catch (NoSuchUserException | PatternCheckFailException e) {
            log.error(e.getMessage());
            return loginFailRedirect(request);
        }

        page = authorizeUser(request, loginedUser);

        Util util = new Util();
        util.generateListOfSubjectsForUserAndUsersWhichPassThem(request);
        util.generateListOfUsersAndTheirRateBySpecialties(request);

        return page;
    }


    /**
     * If there is no user in database, system redirect to index page with corresponding
     * message
     * @param request HttpServletRequest
     * @return String path to page
     */
    private String loginFailRedirect(HttpServletRequest request) {
        request.getSession().setAttribute(Strings.SUCCESSFUL_LOGIN, Strings.NO);
        return LoadConfigProperty.getInstance().getConfigProperty(Strings.PATH_PAGE_INDEX);
    }

    /**
     * Method check if there is such user in database and if it is return it
     * otherwise throw exception
     * @param request HttpServletRequest
     * @return User already registrated user
     * @throws NoSuchUserException
     * @throws PatternCheckFailException
     */
    private User checkLogin(HttpServletRequest request) throws NoSuchUserException, PatternCheckFailException {
        try (LoginService loginService = new LoginService(isTest)) {
            String login = request.getParameter(Strings.LOGIN);
            String password = DigestUtils.md5Hex(request.getParameter(Strings.PASSWORD));
            PatternConstructor patternConstructor = new PatternConstructor();

            if (patternConstructor.checkWithPattern(Patterns.LOGIN, login)
                    && patternConstructor.checkWithPattern(Patterns.PASSWORD,
                    request.getParameter(Strings.PASSWORD))) {

                User loginedUser = loginService.checkLogin(login, password);

                request.getSession().setAttribute(Strings.LOGINED_USER, loginedUser);
                log.info("Logined user is " + loginedUser);

                return loginedUser;
            }
            throw new PatternCheckFailException();
        }
    }

    /**
     * Method to define user role and generate corresponding page. Also
     * here is used Util which prepare all needed information to be able to
     * go to next page
     * @param request HttpServletRequest
     * @param loginedUser User
     * @return String path to page
     * @see User
     * @see Util
     */
    private String authorizeUser(HttpServletRequest request, User loginedUser) {
        log.info("Successfully login");

        try (LoginService loginService = new LoginService(isTest)) {

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
        try (UniversityService universityService = new UniversityService(isTest)) {
            ArrayList<String> listOfCities = new ArrayList<>(universityService.getAllCities());
            request.getSession().setAttribute(Strings.CITIES_LIST, listOfCities);
        }

        Util util = new Util();
        util.checkIfDisplayUserSubjectsAndGrade(request);
        util.checkIfDisplayCongratulationOnSpecialty(request);

        return LoadConfigProperty.getInstance().getConfigProperty(Strings.PATH_PAGE_STUDENT_MAIN);
    }

}
