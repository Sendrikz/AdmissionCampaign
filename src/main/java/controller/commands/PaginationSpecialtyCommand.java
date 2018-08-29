package controller.commands;

import controller.util.Util;
import controller.pagination.Pagination;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PaginationSpecialtyCommand implements ActionCommand {

    private static final Logger log = Logger.getLogger(PaginationSpecialtyCommand.class);
    private Properties property;

    PaginationSpecialtyCommand() {
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
        log.info("Start class PaginationSpecialtyCommand execute()");
        int currentPage = Integer.valueOf(request.getParameter("currentPage"));
        log.debug("Current page: " + currentPage);
        int recordsPerPage = Pagination.FIVE_RECORDS_PER_PAGE;

        Util.generatePaginationSpecialties(request, currentPage, recordsPerPage);

        return property.getProperty("path.page.adminMain");
    }
}
