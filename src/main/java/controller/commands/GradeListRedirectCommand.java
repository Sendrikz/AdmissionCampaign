package controller.commands;

import utils.grade_counter.CountGeneralGrade;
import utils.property_loaders.LoadConfigProperty;
import utils.Strings;

import javax.servlet.http.HttpServletRequest;

/**
 * Class to redirect both type of users to the same page in which they can see
 * rate of all students order by specialties
 * @author Olha Yuryeva
 * @version 1.0
 */
public class GradeListRedirectCommand implements ActionCommand {

    /**
     *
     * @param request HttpServletRequest
     * @return String path to page
     */
    @Override
    public String execute(HttpServletRequest request) {
        request.getSession().setAttribute(Strings.SPECIALTY_USER_GRADE_HASH_MAP,
                new CountGeneralGrade().fillListOfSpecialtiesAndUsers());
        return LoadConfigProperty.getInstance().getConfigProperty(Strings.PATH_PAGE_GRADE_LIST);
    }

}
