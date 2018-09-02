package controller.commands;

import utils.property_loaders.LoadConfigProperty;
import utils.Strings;
import utils.Util;
import utils.pagination.Pagination;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class PaginationSpecialtyCommand implements ActionCommand {

    private static final Logger log = Logger.getLogger(PaginationSpecialtyCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        log.info("Start class PaginationSpecialtyCommand execute()");

        int currentPage = Integer.valueOf(request.getParameter(Strings.CURRENT_PAGE));
        log.debug("Current page: " + currentPage);
        int recordsPerPage = Pagination.FIVE_RECORDS_PER_PAGE;

        new Util().generatePaginationSpecialties(request, currentPage, recordsPerPage);

        return LoadConfigProperty.getInstance().getConfigProperty(Strings.PATH_PAGE_ADMIN_MAIN);
    }
}
