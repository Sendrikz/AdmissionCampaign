package controller.commands;

import controller.CountGeneralGrade;
import org.apache.log4j.Logger;
import services.SubjectService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;

public class SetGradeCommand implements ActionCommand {
    private static final Logger log = Logger.getLogger(SetGradeCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        log.info("Start class SetGradeCommand execute()");
        int subjectId = Integer.parseInt(request.getParameter("subjectId"));
        log.debug("Subject id: " + subjectId);
        int userId = Integer.parseInt(request.getParameter("userId"));
        log.debug("User id: " + userId);
        BigDecimal grade = BigDecimal.valueOf(Double.parseDouble(request.getParameter("grade")));
        log.debug("Grade: " + grade);
        SubjectService.updateSubjectToUser(subjectId, userId, grade);
        request.getSession().setAttribute("subjectUserHashMap",
                SubjectService.updateHashMapOfSubjectUsers(ArrayList.class.cast(request.getSession().getAttribute("subjectsList"))));
        return "/jsp/admin/adminMain.jsp";
    }
}
