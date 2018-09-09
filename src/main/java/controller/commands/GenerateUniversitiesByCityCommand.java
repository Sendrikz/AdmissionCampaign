package controller.commands;

import utils.property_loaders.LoadConfigProperty;
import utils.Strings;
import model.enteties.University;
import org.apache.log4j.Logger;
import services.UniversityService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * Class to generate list of universities depending on selected city
 * @author Olha Yuryeva
 * @version 1.0
 */

public class GenerateUniversitiesByCityCommand implements ActionCommand {

    private static final Logger log = Logger.getLogger(GenerateUniversitiesByCityCommand.class);

    /**
     *
     * @param request HttpServletRequest
     * @return String path to page
     */
    @Override
    public String execute(HttpServletRequest request) {
        log.info("Start class GenerateUniversitiesByCityCommand execute()");

        String page = LoadConfigProperty.getInstance()
                .getConfigProperty(Strings.PATH_PAGE_STUDENT_UNI_BY_CITY);

        generateUniversitiesBySelectedCity(request);

        return page;
    }

    /**
     *
     * @param request HttpServletRequest
     */
    private void generateUniversitiesBySelectedCity(HttpServletRequest request) {
        ArrayList<University> listOfUniversitiesToDisplay = new ArrayList<>();

        try (UniversityService universityService = new UniversityService()) {
            ArrayList<University> listOfUniversities = universityService.getAllUniversities();

            for (University uni : listOfUniversities) {
                if (uni.getCity().equals(request.getParameter(Strings.CITY).trim())) {
                    listOfUniversitiesToDisplay.add(uni);
                }
            }
        }
        request.getSession().setAttribute(Strings.LIST_OF_UNI, listOfUniversitiesToDisplay);
    }

}
