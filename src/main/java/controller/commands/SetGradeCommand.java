package controller.commands;

import utils.property_loaders.LoadConfigProperty;
import utils.Strings;
import utils.Util;
import org.apache.log4j.Logger;
import services.SubjectService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/**
 * Class for setting grade to students
 * @author Olha Yuryeva
 * @version 1.0
 */
public class SetGradeCommand implements ActionCommand {

    private static final Logger log = Logger.getLogger(SetGradeCommand.class);

    /**
     * @param request HttpServletRequest
     * @return String path to page
     */
    @Override
    public String execute(HttpServletRequest request) {
        log.info("Start class SetGradeCommand execute()");
        String page = LoadConfigProperty.getInstance().getConfigProperty(Strings.PATH_PAGE_ADMIN_MAIN);

        setGradeToStudent(request);

        new Util().generateListOfSubjectsForUserAndUsersWhichPassThem(request);
        return page;
    }

    /**
     * @param request HttpServletRequest
     */
    private void setGradeToStudent(HttpServletRequest request) {
        int subjectId = Integer.parseInt(request.getParameter(Strings.SUBJECT_ID));
        log.debug("Subject id: " + subjectId);
        int userId = Integer.parseInt(request.getParameter(Strings.USER_ID));
        log.debug("User id: " + userId);
        BigDecimal grade = BigDecimal.valueOf(Double.parseDouble(request.getParameter(Strings.GRADE)));
        log.debug("Grade: " + grade);
        try (SubjectService subjectService = new SubjectService()) {
            subjectService.updateSubjectToUser(subjectId, userId, grade);
        }
    }
}
