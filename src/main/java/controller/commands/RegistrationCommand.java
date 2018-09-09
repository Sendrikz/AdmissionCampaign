package controller.commands;

import utils.exceptions.PatternCheckFailException;
import utils.patterns.PatternConstructor;
import utils.patterns.Patterns;
import utils.property_loaders.LoadConfigProperty;
import utils.Strings;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import services.LoginService;
import services.exceptions.NoSuchUserException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * Class is implemented by the Authentication system
 * @author Olha Yuryeva
 * @version 1.0
 */
public class RegistrationCommand implements ActionCommand {

    private static final Logger log = Logger.getLogger(RegistrationCommand.class);

    /**
     * @param request HttpServletRequest
     * @return String path to page
     */
    @Override
    public String execute(HttpServletRequest request) {
        log.info("Start class RegistrationCommand execute()");

        String page = LoadConfigProperty.getInstance().getConfigProperty(Strings.PATH_PAGE_LOGIN);
        try {
            patternCheck(request);
        } catch (PatternCheckFailException e) {
            log.error(e.getMessage());
            return failRedirect();
        }
        checkIfSuchUserExistsAndRegistrateHim(request);

        return page;
    }

    /**
     * Check is it received a valid information
     * @param request HttpServletRequest
     * @throws PatternCheckFailException
     */
    private void patternCheck(HttpServletRequest request) throws PatternCheckFailException {
        PatternConstructor constructor = new PatternConstructor();
        ArrayList<String> userInfo = new ArrayList<>();
        userInfo.add(request.getParameter(Strings.FIRST_NAME));
        userInfo.add(request.getParameter(Strings.PATRONYMIC));
        userInfo.add(request.getParameter(Strings.BIRTHDAY));
        userInfo.add(request.getParameter(Strings.CITY));
        for (String str : userInfo) {
            if (!constructor.checkWithPattern(Patterns.NAME_FIELDS, str)) {

                throw new PatternCheckFailException();

            }
        }
        if (!(constructor.checkWithPattern(Patterns.LOGIN, request.getParameter(Strings.EMAIL)) &&
                constructor.checkWithPattern(Patterns.PASSWORD, request.getParameter(Strings.PASSWORD)))) {

            throw new PatternCheckFailException();

        }
    }

    /**
     * @param request HttpServletRequest
     */
    private void checkIfSuchUserExistsAndRegistrateHim(HttpServletRequest request) {
        try (LoginService loginService = new LoginService()) {
            loginService.checkLogin(request.getParameter(Strings.EMAIL),
                    DigestUtils.md5Hex(request.getParameter(Strings.PASSWORD)));
            request.getSession().setAttribute(Strings.SUCCESSFUL_REGISTRATED, Strings.NO);
        } catch (NoSuchUserException e) {
            log.error(e.getMessage());
            registrateUser(request);
        }
    }

    /**
     * @param request HttpServletRequest
     */
    private void registrateUser(HttpServletRequest request) {
        try (LoginService loginService = new LoginService()) {
            loginService.addUser(request.getParameter(Strings.LAST_NAME),
                    request.getParameter(Strings.FIRST_NAME), request.getParameter(Strings.PATRONYMIC),
                    request.getParameter(Strings.BIRTHDAY), request.getParameter(Strings.CITY),
                    request.getParameter(Strings.EMAIL),
                    DigestUtils.md5Hex(request.getParameter(Strings.PASSWORD)));

            request.getSession().setAttribute(Strings.SUCCESSFUL_REGISTRATED, Strings.YES);
        }
    }

    /**
     * @return String path to page
     */
    private String failRedirect() {
        return LoadConfigProperty.getInstance().getConfigProperty(Strings.PATH_PAGE_INDEX);
    }

}
