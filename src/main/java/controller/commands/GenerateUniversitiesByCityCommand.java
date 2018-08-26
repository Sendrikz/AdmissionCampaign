package controller.commands;

import model.enteties.University;
import org.apache.log4j.Logger;
import services.UniversityService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

public class GenerateUniversitiesByCityCommand implements ActionCommand {

    private static final Logger log = Logger.getLogger(GenerateUniversitiesByCityCommand.class);
    private Properties property;

    GenerateUniversitiesByCityCommand() {
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
        log.info("Start class GenerateUniversitiesByCityCommand execute()");
        String page = property.getProperty("path.page.studentUniByCity");
        ArrayList<University> listOfUniversities = UniversityService.getAllUniversities();
        ArrayList<University> listOfUniversitiesToDisplay = new ArrayList<>();

        for (University uni : listOfUniversities) {
            if (uni.getCity().equals(request.getParameter("city").trim())) {
                listOfUniversitiesToDisplay.add(uni);
            }
        }
        request.getSession().setAttribute("listOfUni", listOfUniversitiesToDisplay);
        return page;
    }
}
