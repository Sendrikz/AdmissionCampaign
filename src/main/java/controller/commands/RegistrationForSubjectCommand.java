package controller.commands;

import utils.property_loaders.LoadConfigProperty;
import utils.Strings;
import model.enteties.Subject;
import model.enteties.User;
import org.apache.log4j.Logger;
import services.SubjectService;
import services.UserService;
import services.exceptions.NoSuchSubjectException;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/**
 * Class helps to registrate user on subject
 * @author Olha Yuryeva
 * @version 1.0
 */
public class RegistrationForSubjectCommand implements ActionCommand {

    private static final Logger log = Logger.getLogger(RegistrationForSubjectCommand.class);

    /**
     *
     * @param request HttpServletRequest
     * @return String path to page
     */
    @Override
    public String execute(HttpServletRequest request) {
        log.info("Start class RegistrationForSubjectCommand execute()");

        String page = LoadConfigProperty.getInstance().getConfigProperty(Strings.PATH_PAGE_STUDENT_MAIN);

        registrateStudentForSubject(request);

        return page;
    }

    /**
     * Add to database if user successfully registrated on subject or in case of fail
     * return to the same page with corresponding message
     * @param request HttpServletRequest
     */
    private void registrateStudentForSubject(HttpServletRequest request) {
        String selectedSubject = request.getParameter(Strings.SUBJECT);
        log.debug("Selected subject: " + selectedSubject);
        User user = (User) request.getSession().getAttribute(Strings.LOGINED_USER);
        log.debug("Current user: " + user);

        try (SubjectService subjectService = new SubjectService();
             UserService userService = new UserService()) {

            Subject subject = subjectService.getSubjectIdByName(selectedSubject);

            if (userService.addUserToSubject(user, subject, false, new BigDecimal(0))) {
                request.getSession().setAttribute(Strings.SUCCESSFUL_SUBJECT, Strings.YES);
                log.debug("Successful = " + request.getSession().getAttribute(Strings.SUCCESSFUL_SUBJECT));
            } else {
                request.getSession().setAttribute(Strings.SUCCESSFUL_SUBJECT, Strings.NO);
                log.debug("Successful = " + request.getSession().getAttribute(Strings.SUCCESSFUL_SUBJECT));
            }

        } catch (NoSuchSubjectException e) {
            e.printStackTrace();
        }
    }
}
