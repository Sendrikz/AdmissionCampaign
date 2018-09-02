package controller.commands;

import utils.property_loaders.LoadConfigProperty;
import utils.Strings;
import utils.Util;
import utils.pagination.Pagination;

import javax.servlet.http.HttpServletRequest;

public class ReturnToMainCommand implements ActionCommand {

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
