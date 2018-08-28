package controller.commands;

import controller.CountGeneralGrade;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GradeListRedirectCommand implements ActionCommand {

    private Properties property;

    GradeListRedirectCommand() {
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
        request.getSession().setAttribute("specialtyUserGradeHashMap", CountGeneralGrade.fillListOfSpecialtiesAndUsers());
        return property.getProperty("path.page.gradeList");
    }
}
