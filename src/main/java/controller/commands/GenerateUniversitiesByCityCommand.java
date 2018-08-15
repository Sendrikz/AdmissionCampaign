package controller.commands;

import model.enteties.University;
import org.apache.log4j.Logger;
import services.UniversityService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

public class GenerateUniversitiesByCityCommand implements ActionCommand {

    private static final Logger log = Logger.getLogger(String.valueOf(LoginCommand.class));

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        log.info("Start class GenerateUniversitiesByCityCommand execute()");
        ArrayList<University> listOfUniversities = UniversityService.getAllUniversities();
        log.info("List of universities: " + listOfUniversities);
        ArrayList<University> listOfUniversitiesToDisplay = new ArrayList<>();
        for (University uni : listOfUniversities) {
            log.debug("Value of city from request: " + request.getParameter("city").trim());
            log.debug("Value of city from array: " + uni.getCity().trim());
            if (uni.getCity().equals(request.getParameter("city").trim())) {
                listOfUniversitiesToDisplay.add(uni);
            }
        }
        log.debug("List of universities to display: " + listOfUniversitiesToDisplay);
        request.getSession().setAttribute("listOfUni", listOfUniversitiesToDisplay);
        page = "/WEB-INF/view/studentUniByCity.jsp";
        return page;
    }
}
