package controller.commands;

import controller.Util;
import controller.pagination.Pagination;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReturnToMainCommand implements ActionCommand {

    private Properties property;

    ReturnToMainCommand() {
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
        Util.checkIfDisplayUserSubjectsAndGrade(request);
        Util.checkIfDisplayCongratulationOnSpecialty(request);
        Util.generatePaginationSpecialties(request, Pagination.FIRST_PAGE,
                Pagination. FIVE_RECORDS_PER_PAGE);
        return property.getProperty("path.page.studentMain");
    }
}
