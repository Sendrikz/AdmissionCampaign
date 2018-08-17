package controller.commands;

import model.enteties.Faculty;
import model.enteties.Specialty;
import org.apache.log4j.Logger;
import services.FacultyService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;

public class GenerateSpecialtyByUniCommand implements ActionCommand {

    private static final Logger log = Logger.getLogger(String.valueOf(GenerateSpecialtyByUniCommand.class));

    @Override
    public String execute(HttpServletRequest request) {
        String page = "/WEB-INF/view/studentSpecialties.jsp";
        ArrayList<Faculty> listOfFaculties = FacultyService.getAll();
        HashMap<Faculty, ArrayList<Specialty>> facultySpecialtyHashMap = new HashMap<>();
        for (Faculty faculty : listOfFaculties) {
            facultySpecialtyHashMap.put(faculty, FacultyService.getAllSpecialtiesOfFaculty(faculty.getId()));
        }
        log.debug("Faculty_specialty hash map: " + facultySpecialtyHashMap);
        request.getSession().setAttribute("facultySpecialtyMap", facultySpecialtyHashMap);
        return page;
    }
}
