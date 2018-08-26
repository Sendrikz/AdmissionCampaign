package controller.commands;

import model.enteties.Subject;
import model.enteties.User;
import org.apache.log4j.Logger;
import services.SubjectService;
import services.UserService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Properties;

public class RegistrationForSubjectCommand implements ActionCommand {

    private static final Logger log = Logger.getLogger(RegistrationForSubjectCommand.class);
    private Properties property;

    RegistrationForSubjectCommand() {
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
        log.info("Start class RegistrationForSubjectCommand execute()");
        String page = property.getProperty("path.page.studentMain");
        String selectedSubject = request.getParameter("subject");
        log.debug("Selected subject: " + selectedSubject);
        User user = (User) request.getSession().getAttribute("loginedUser");
        log.debug("Current user: " + user);
        Subject subject = SubjectService.getSubjectIdByName(selectedSubject);

        if (UserService.addUserToSubject(user, subject, false, new BigDecimal(0))) {
            request.getSession().setAttribute("successfulSubject", "yes");
            log.debug("Successful = " + request.getSession().getAttribute("successfulSubject"));
        } else {
            request.getSession().setAttribute("successfulSubject", "no");
            log.debug("Successful = " + request.getSession().getAttribute("successfulSubject"));
        }

        return page;
    }
}
