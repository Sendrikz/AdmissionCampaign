package controller.commands;

import utils.property_loaders.LoadConfigProperty;
import utils.Strings;
import utils.Util;
import utils.pagination.Pagination;

import javax.servlet.http.HttpServletRequest;

/**
 * Redirect to the main page
 * @author Olha Yuryeva
 * @version 1.0
 */
public class ReturnToMainCommand implements ActionCommand {

    /**
     * Method uses Util to prepare all needed information for redirect
     * @param request HttpServletRequest
     * @return String path to page
     * @see Util
     */
    @Override
    public String execute(HttpServletRequest request) {
        Util util = new Util();
        util.checkIfDisplayUserSubjectsAndGrade(request);
        util.checkIfDisplayCongratulationOnSpecialty(request);
        util.generatePaginationSpecialties(request, Pagination.FIRST_PAGE,
                Pagination. FIVE_RECORDS_PER_PAGE);

        return LoadConfigProperty.getInstance().getConfigProperty(Strings.PATH_PAGE_STUDENT_MAIN);
    }
}
