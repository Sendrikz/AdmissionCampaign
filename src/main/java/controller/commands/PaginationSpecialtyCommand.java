package controller.commands;

import controller.pagination.Pagination;
import model.enteties.Specialty;
import org.apache.log4j.Logger;
import services.SpecialtyService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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

        ArrayList<Specialty> paginationSpecialties = SpecialtyService.getAll(currentPage,
                recordsPerPage);
        request.getSession().setAttribute("paginationSpecialties", paginationSpecialties);
        log.debug("Pagination specialties: " + paginationSpecialties);
        int rows = SpecialtyService.getRows();
        int nOfPages = Pagination.countNumberOfPages(rows, recordsPerPage);
        request.getSession().setAttribute("noOfPages", nOfPages);

        return property.getProperty("path.page.adminMain");
    }
}
