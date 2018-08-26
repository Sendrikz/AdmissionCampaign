package controller.commands;

import model.enteties.Specialty;
import model.enteties.Subject;
import model.enteties.University;
import model.enteties.User;
import org.apache.log4j.Logger;
import services.SpecialtyService;
import services.UserService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class RegistrationForSpecialtyCommand implements ActionCommand {

    private static final Logger log = Logger.getLogger(RegistrationForSpecialtyCommand.class);
    private Properties property;

    RegistrationForSpecialtyCommand() {
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
        log.info("Start class RegistrationForSpecialtyCommand execute()");
        String page = property.getProperty("path.page.studentSpecialties");
        int specialtyId = Integer.parseInt(request.getParameter("specialtyToRegistrId"));
        User user = (User) request.getSession().getAttribute("loginedUser");
        Specialty specialty = SpecialtyService.findById(specialtyId);

        HashMap<Subject, BigDecimal> mapOfSubjectsBySpecialty =
                SpecialtyService.getAllSubjectsOfSpecialty(specialtyId);
        ArrayList<Subject> listOfSubjectsOfUser = UserService.getAllSubjectsByUser(user.getId());

        for (Map.Entry<Subject, BigDecimal> entry : mapOfSubjectsBySpecialty.entrySet()) {
            if (!listOfSubjectsOfUser.contains(entry.getKey())) {
                request.getSession().setAttribute("notAllSubjects", "yes");
                return page;
            }
        }
        if (UserService.addUserToSpecialty(user, specialty, false)) {
            request.getSession().setAttribute("successfulSpecialty", "yes");
            log.debug("Successful = " + request.getSession().getAttribute("successfulSpecialty"));
        } else {
            request.getSession().setAttribute("successfulSpecialty", "no");
            log.debug("Successful = " + request.getSession().getAttribute("successfulSpecialty"));
        }
        return page;
    }
}
