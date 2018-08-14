package controller.commands;

import model.enteties.Subject;
import model.enteties.User;
import org.apache.log4j.Logger;
import services.SubjectService;
import services.UserService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

public class RegistrationForSubjectCommand implements ActionCommand {

    private static final Logger log = Logger.getLogger(String.valueOf(RegistrationForSubjectCommand.class));

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        log.info("Start class RegistrationForSubjectCommand execute()");
        String selectedSubject = request.getParameter("subject");
        log.debug("Selected subject: " + selectedSubject);
        User user = (User) request.getSession().getAttribute("loginedUser");
        log.debug("Current user: " + user);
        Subject subject = SubjectService.getSubjectIdByName(selectedSubject);
        if (UserService.addUserToSubject(user, subject, false, new BigDecimal(0))) {
            page = "/WEB-INF/view/studentMain.jsp:RegistratedSuccesfully";
            log.debug(page);
        } else {
            page = "/WEB-INF/view/studentMain.jsp:AlreadyRegistrated";
            log.debug(page);
        }
        return page;
    }
}
