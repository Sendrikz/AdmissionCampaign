package controller.commands;

import services.exceptions.NoSuchUniversityException;
import utils.property_loaders.LoadConfigProperty;
import utils.Strings;
import model.enteties.Faculty;
import model.enteties.Specialty;
import model.enteties.Subject;
import org.apache.log4j.Logger;
import services.FacultyService;
import services.SpecialtyService;
import services.UniversityService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

public class GenerateSpecialtyByUniCommand implements ActionCommand {

    private static final Logger log = Logger.getLogger(GenerateSpecialtyByUniCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        log.info("Start class GenerateSpecialtyByUniCommand execute()");

        String page = LoadConfigProperty.getInstance()
                .getConfigProperty(Strings.PATH_PAGE_STUDENT_SPECIALTIES);

        generateSpecialtiesByUniversityAndItsCoef(request);

        return page;
    }

    private void generateSpecialtiesByUniversityAndItsCoef(HttpServletRequest request) {
        int uniId = Integer.parseInt(request.getParameter(Strings.UNI_ID).trim());
        log.debug("University id from jsp: " + uniId);

        try (UniversityService universityService = new UniversityService();
             FacultyService facultyService = new FacultyService();
             SpecialtyService specialtyService = new SpecialtyService()) {

            ArrayList<Faculty> listOfFaculties = universityService.getAllFacultiesOfUniversity(
                    uniId);
            request.getSession().setAttribute(Strings.SELECTED_UNI, universityService.findById(uniId));
            HashMap<Faculty, ArrayList<Specialty>> facultySpecialtyHashMap = new HashMap<>();
            HashMap<Specialty, HashMap<Subject, BigDecimal>> subjectBigDecimalHashMap
                    = new HashMap<>();

            for (Faculty faculty : listOfFaculties) {
                facultySpecialtyHashMap.put(faculty,
                        facultyService.getAllSpecialtiesOfFaculty(faculty.getId()));

                for (Specialty specialty : facultySpecialtyHashMap.get(faculty)) {
                    subjectBigDecimalHashMap.put(specialty,
                            specialtyService.getAllSubjectsOfSpecialty(specialty.getId()));
                }
            }
            request.getSession().setAttribute(Strings.FACULTY_SPECIALTY_MAP, facultySpecialtyHashMap);
            request.getSession().setAttribute(Strings.SPECIALTY_SUBJECT_MAP, subjectBigDecimalHashMap);

        } catch (NoSuchUniversityException e) {
            log.error(e.getMessage());
        }
    }

}
