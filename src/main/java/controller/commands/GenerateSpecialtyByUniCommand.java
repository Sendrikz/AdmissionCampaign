package controller.commands;

import model.enteties.Faculty;
import model.enteties.Specialty;
import org.apache.log4j.Logger;
import services.FacultyService;
import services.UniversityService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;

public class GenerateSpecialtyByUniCommand implements ActionCommand {

    private static final Logger log = Logger.getLogger(String.valueOf(GenerateSpecialtyByUniCommand.class));

    @Override
    public String execute(HttpServletRequest request) {
        log.info("Start class GenerateSpecialtyByUniCommand execute()");
        String page = "/WEB-INF/view/studentSpecialties.jsp";
        int uniId = Integer.parseInt(request.getParameter("uniId").trim());
        log.debug("University id from jsp: " + uniId);
        ArrayList<Faculty> listOfFaculties = UniversityService.getAllFacultiesOfUniversity(
                uniId);
        request.getSession().setAttribute("selectedUni", UniversityService.findById(uniId));
        log.debug("List of faculties by university: " + listOfFaculties);
        HashMap<Faculty, ArrayList<Specialty>> facultySpecialtyHashMap = new HashMap<>();
        for (Faculty faculty : listOfFaculties) {
            facultySpecialtyHashMap.put(faculty, FacultyService.getAllSpecialtiesOfFaculty(faculty.getId()));
        }
        log.debug("Faculty_specialty hash map: " + facultySpecialtyHashMap);
        request.getSession().setAttribute("facultySpecialtyMap", facultySpecialtyHashMap);
        log.debug("Page: " + page);
        return page;
    }
}
