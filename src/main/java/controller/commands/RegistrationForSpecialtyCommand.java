package controller.commands;

import model.enteties.Specialty;
import model.enteties.Subject;
import model.enteties.University;
import model.enteties.User;
import org.apache.log4j.Logger;
import services.SpecialtyService;
import services.UserService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegistrationForSpecialtyCommand implements ActionCommand {

    private static final Logger log = Logger.getLogger(RegistrationForSpecialtyCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        log.info("Start class RegistrationForSpecialtyCommand execute()");
        int specialtyId = Integer.parseInt(request.getParameter("specialtyToRegistrId"));
        User user = (User) request.getSession().getAttribute("loginedUser");
        Specialty specialty = SpecialtyService.findById(specialtyId);
        HashMap<Subject, BigDecimal> mapOfSubjectsBySpecialty =
                SpecialtyService.getAllSubjectsOfSpecialty(specialtyId);
        ArrayList<Subject> listOfSubjectsOfUser = UserService.getAllSubjectsByUser(user.getId());

        for (Map.Entry<Subject, BigDecimal> entry : mapOfSubjectsBySpecialty.entrySet()) {
            if (!listOfSubjectsOfUser.contains(entry.getKey())) {
                request.getSession().setAttribute("notAllSubjects", "yes");
                return "/jsp/student/studentSpecialties.jsp";
            }
        }
        if (UserService.addUserToSpecialty(user, specialty, false)) {
            request.getSession().setAttribute("successfulSpecialty", "yes");
            log.debug("Successful = " + request.getSession().getAttribute("successfulSpecialty"));
        } else {
            request.getSession().setAttribute("successfulSpecialty", "no");
            log.debug("Successful = " + request.getSession().getAttribute("successfulSpecialty"));
        }
        page = "/jsp/student/studentSpecialties.jsp";
        log.debug(page);
        return page;
    }
}
