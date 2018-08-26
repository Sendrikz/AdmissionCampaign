package controller.commands;

import model.enteties.Faculty;
import model.enteties.Specialty;
import model.enteties.Subject;
import org.apache.log4j.Logger;
import services.FacultyService;
import services.SpecialtyService;
import services.UniversityService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

public class GenerateSpecialtyByUniCommand implements ActionCommand {

    private static final Logger log = Logger.getLogger(GenerateSpecialtyByUniCommand.class);
    private Properties property;

    GenerateSpecialtyByUniCommand() {
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
        log.info("Start class GenerateSpecialtyByUniCommand execute()");
        String page = property.getProperty("path.page.studentSpecialties");
        int uniId = Integer.parseInt(request.getParameter("uniId").trim());
        log.debug("University id from jsp: " + uniId);
        ArrayList<Faculty> listOfFaculties = UniversityService.getAllFacultiesOfUniversity(
                uniId);
        request.getSession().setAttribute("selectedUni", UniversityService.findById(uniId));
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
        request.getSession().setAttribute("facultySpecialtyMap", facultySpecialtyHashMap);
        request.getSession().setAttribute("specialtySubjectMap", subjectBigDecimalHashMap);
        return page;
    }
}
