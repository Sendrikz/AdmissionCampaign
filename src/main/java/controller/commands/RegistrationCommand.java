package controller.commands;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import services.LoginService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class RegistrationCommand implements ActionCommand {

    private static final Logger log = Logger.getLogger(RegistrationCommand.class);
    private Properties property;

    RegistrationCommand() {
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
        log.info("start class RegistrationCommand execute()");
        String page = property.getProperty("path.page.login");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String patronymic = request.getParameter("patronymic");
        String birthday = request.getParameter("birthday");
        String city = request.getParameter("city");
        String email = request.getParameter("email");
        String password = DigestUtils.md5Hex(request.getParameter("password"));
        log.debug("Password in md5: " + password);
        if (LoginService.checkLogin (email, password) == null) {
            LoginService.addUser(lastName, firstName, patronymic, birthday, city, email, password);
            request.getSession().setAttribute("successfulRegistrated", "yes");
        } else {
            request.getSession().setAttribute("successfulRegistrated", "no");
        }
        return page;
    }
}
