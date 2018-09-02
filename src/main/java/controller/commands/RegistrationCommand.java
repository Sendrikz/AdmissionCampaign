package controller.commands;

import utils.property_loaders.LoadConfigProperty;
import utils.Strings;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import services.LoginService;
import services.exceptions.NoSuchUserException;

import javax.servlet.http.HttpServletRequest;

public class RegistrationCommand implements ActionCommand {

    private static final Logger log = Logger.getLogger(RegistrationCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        log.info("Start class RegistrationCommand execute()");

        String page = LoadConfigProperty.getInstance().getConfigProperty(Strings.PATH_PAGE_LOGIN);

        checkIfSuchUserExistsAndRegistrateHim(request);

        return page;
    }

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

}
