package controller.commands;

import utils.grade_counter.CountGeneralGrade;
import utils.property_loaders.LoadConfigProperty;
import utils.Strings;

import javax.servlet.http.HttpServletRequest;

public class GradeListRedirectCommand implements ActionCommand {

    @Override
    public String execute(HttpServletRequest request) {
        request.getSession().setAttribute(Strings.SPECIALTY_USER_GRADE_HASH_MAP,
                new CountGeneralGrade().fillListOfSpecialtiesAndUsers());
        return LoadConfigProperty.getInstance().getConfigProperty(Strings.PATH_PAGE_GRADE_LIST);
    }

}
