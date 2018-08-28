package controller.commands;

import controller.CountGeneralGrade;
import org.apache.log4j.Logger;
import services.SubjectService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Properties;

public class SetGradeCommand implements ActionCommand {

    private static final Logger log = Logger.getLogger(SetGradeCommand.class);
    private Properties property;

    SetGradeCommand() {
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
        log.info("Start class SetGradeCommand execute()");
        String page = property.getProperty("path.page.adminMain");
        int subjectId = Integer.parseInt(request.getParameter("subjectId"));
        log.debug("Subject id: " + subjectId);
        int userId = Integer.parseInt(request.getParameter("userId"));
        log.debug("User id: " + userId);
        BigDecimal grade = BigDecimal.valueOf(Double.parseDouble(request.getParameter("grade")));
        log.debug("Grade: " + grade);
        SubjectService.updateSubjectToUser(subjectId, userId, grade);
        request.getSession().setAttribute("subjectUserHashMap",
                SubjectService.updateHashMapOfSubjectUsers(ArrayList.class.cast(request.getSession().getAttribute("subjectsList"))));
        return page;
    }
}
