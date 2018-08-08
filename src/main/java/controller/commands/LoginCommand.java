package controller.commands;

import org.apache.log4j.Logger;
import services.LoginService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LoginCommand implements ActionCommand {

    private static final Logger log = Logger.getLogger(String.valueOf(LoginCommand.class));
    private Properties property;

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        log.info("start class LoginCommand execute()");
        String login = request.getParameter("login");
        log.debug("Login: " + login);
        String password = request.getParameter("password");
        log.debug("Password: " + password);
        if (LoginService.checkLogin(login, password)) {
            log.info("Successfully login");
            request.setAttribute("user", login);
            request.setAttribute("name", "НаУКМА");
            property = new Properties();
            try (InputStream is = this.getClass().getClassLoader().
                    getResourceAsStream("config.properties")){
                property.load(is);
            } catch (IOException e) {
                e.printStackTrace();
            }
            page = property.getProperty("path.page.main");
        } else {
            log.info("Login fail");
            page = "login_fail";

//            resp.getWriter().write("<script>");
//            resp.getWriter().write("alert('Hello! I am an alert box!!');");
//            resp.getWriter().write("</script>");
//            page = property.getProperty("path.page.login");
//            RequestDispatcher requestDispatcher = req.getRequestDispatcher(page);
//            requestDispatcher.include(req, resp);
        }
        return page;
    }
}
