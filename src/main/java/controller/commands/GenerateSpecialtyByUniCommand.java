package controller.commands;

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

    private static final Logger log = Logger.getLogger(String.valueOf(GenerateSpecialtyByUniCommand.class));

    @Override
    public String execute(HttpServletRequest request) {
        log.info("Start class GenerateSpecialtyByUniCommand execute()");
        String page = "/jsp/student/studentSpecialties.jsp";
        int uniId = Integer.parseInt(request.getParameter("uniId").trim());
        log.debug("University id from jsp: " + uniId);
        ArrayList<Faculty> listOfFaculties = UniversityService.getAllFacultiesOfUniversity(
                uniId);
        request.getSession().setAttribute("selectedUni", UniversityService.findById(uniId));
        log.debug("List of faculties by university: " + listOfFaculties);
        HashMap<Faculty, ArrayList<Specialty>> facultySpecialtyHashMap = new HashMap<>();
        HashMap<Specialty, HashMap<Subject, BigDecimal>> subjectBigDecimalHashMap
                = new HashMap<>();
        for (Faculty faculty : listOfFaculties) {
            facultySpecialtyHashMap.put(faculty,
                    FacultyService.getAllSpecialtiesOfFaculty(faculty.getId()));
            for (Specialty specialty : facultySpecialtyHashMap.get(faculty)) {
                subjectBigDecimalHashMap.put(specialty,
                        SpecialtyService.getAllSubjectsOfSpecialty(specialty.getId()));
            }
        }
        log.debug("Faculty_specialty hash map: " + facultySpecialtyHashMap);
        request.getSession().setAttribute("facultySpecialtyMap", facultySpecialtyHashMap);
        log.debug("Map of specialties and subjects: " + subjectBigDecimalHashMap);
        request.getSession().setAttribute("specialtySubjectMap", subjectBigDecimalHashMap);
        log.debug("Page: " + page);
        return page;
    }
}
