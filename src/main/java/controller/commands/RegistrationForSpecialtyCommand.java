package controller.commands;

import model.enteties.Specialty;
import model.enteties.University;
import model.enteties.User;
import org.apache.log4j.Logger;
import services.SpecialtyService;
import services.UserService;

import javax.servlet.http.HttpServletRequest;

public class RegistrationForSpecialtyCommand implements ActionCommand {

    private static final Logger log = Logger.getLogger(String.valueOf(RegistrationForSpecialtyCommand.class));

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        log.info("Start class RegistrationForSpecialtyCommand execute()");
        int specialtyId = Integer.parseInt(request.getParameter("specialtyToRegistrId"));
        User user = (User) request.getSession().getAttribute("loginedUser");
        Specialty specialty = SpecialtyService.findById(specialtyId);
        if (UserService.addUserToSpecialty(user, specialty, false)) {
            page = "/WEB-INF/view/studentSpecialties.jsp:RegistratedSuccesfully";
            log.debug(page);
        } else {
            page = "/WEB-INF/view/studentSpecialties.jsp:AlreadyRegistrated";
            log.debug(page);
        }
        return page;
    }
}
