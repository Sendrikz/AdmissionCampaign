package controller.commands;

import services.exceptions.NoSuchSpecialtyException;
import utils.property_loaders.LoadConfigProperty;
import utils.Strings;
import model.enteties.Specialty;
import model.enteties.Subject;
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
        log.info("Start class RegistrationForSpecialtyCommand execute()");
        String page;

        page = registrateStudentOnSubject(request);

        return page;
    }

    private String registrateStudentOnSubject(HttpServletRequest request) {
        String page = LoadConfigProperty.getInstance()
                .getConfigProperty(Strings.PATH_PAGE_STUDENT_SPECIALTIES);
        int specialtyId = Integer.parseInt(request.getParameter(Strings.SPECILATY_TO_REGISTR_ID));
        User user = (User) request.getSession().getAttribute(Strings.LOGINED_USER);

        try (SpecialtyService specialtyService = new SpecialtyService();
             UserService userService = new UserService()) {
            Specialty specialty = specialtyService.findById(specialtyId);

            HashMap<Subject, BigDecimal> mapOfSubjectsBySpecialty =
                    specialtyService.getAllSubjectsOfSpecialty(specialtyId);
            ArrayList<Subject> listOfSubjectsOfUser = userService.getAllSubjectsByUser(user.getId());

            for (Map.Entry<Subject, BigDecimal> entry : mapOfSubjectsBySpecialty.entrySet()) {
                if (!listOfSubjectsOfUser.contains(entry.getKey())) {
                    request.getSession().setAttribute(Strings.NOT_ALL_SUBJECTS, Strings.YES);
                    return page;
                }
            }
            if (userService.addUserToSpecialty(user, specialty, false)) {
                request.getSession().setAttribute(Strings.SUCCESSFUL_SPECIALTY, Strings.YES);
                log.debug("Successful = " + request.getSession().getAttribute(Strings.SUCCESSFUL_SPECIALTY));
            } else {
                request.getSession().setAttribute(Strings.SUCCESSFUL_SPECIALTY, Strings.NO);
                log.debug("Successful = " + request.getSession().getAttribute(Strings.SUCCESSFUL_SPECIALTY));
            }
        } catch (NoSuchSpecialtyException e) {
            log.error(e.getMessage());
        }
        return page;
    }

}
