package controller.commands;

import services.exceptions.NoSuchSpecialtyException;
import services.transaction.TransactionStudentSpecialty;
import utils.property_loaders.LoadConfigProperty;
import utils.Strings;
import model.enteties.Specialty;
import model.enteties.User;
import org.apache.log4j.Logger;
import services.SpecialtyService;
import services.UserService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.TreeMap;

/**
 * Class to generate rating lists by specialty. Should use after checking all subjects
 * @author Olha Yuryeva
 * @version 1.0
 */

public class RateSpecialtyCommand implements ActionCommand {
    private static final Logger log = Logger.getLogger(RateSpecialtyCommand.class);

    /**
     * @param request HttpServletRequest
     * @return String path to page
     */
    @Override
    public String execute(HttpServletRequest request) {
        log.info("Start class rateSpecialtyCommand execute()");
        String page;

        page = makeRatingListOfStudentsWhoHasPassedOnSpecialty(request);

        return page;
    }

    /**
     * Method to make rating list. Also here we using transaction
     * @param request HttpServletRequest
     * @return String path to page
     * @see TransactionStudentSpecialty
     */
    private String makeRatingListOfStudentsWhoHasPassedOnSpecialty(HttpServletRequest request) {
        String page = LoadConfigProperty.getInstance().getConfigProperty(Strings.PATH_PAGE_ADMIN_MAIN);
        int currentSpecialtyId = Integer.parseInt(request.getParameter(Strings.SPECIALTY_ID));
        HashMap<Specialty, TreeMap<BigDecimal, User>> specialtyTreeMapHashMap
                = (HashMap<Specialty, TreeMap<BigDecimal, User>>)
                request.getSession().getAttribute("specialtyUserGradeHashMap");

        try (SpecialtyService specialtyService = new SpecialtyService();
             UserService userService = new UserService()) {

            Specialty selectedSpecialty = specialtyService.findById(currentSpecialtyId);
            log.debug("Selected specialty: " + selectedSpecialty);
            int availableQuantity = selectedSpecialty.getQuantityOfStudents();
            TreeMap<BigDecimal, User> userTreeMap = specialtyTreeMapHashMap.get(selectedSpecialty);
            int count = 0;
            for (User user : userTreeMap.values()) {
                if (userService.getPassedSpecialtyByUser(user.getId()).isPresent()) {
                    request.getSession().setAttribute(Strings.CONFIRM_RATE, Strings.NO);
                    return page;
                }
                if (count < availableQuantity) {
                    TransactionStudentSpecialty.updateUserSpecialty(user.getId(),
                            selectedSpecialty.getId());
                }
                count++;
            }
            log.debug("Count = " + count);

        } catch (NoSuchSpecialtyException e) {
            log.error(e.getMessage());
        }
        request.getSession().setAttribute(Strings.CONFIRM_RATE, Strings.YES);
        return page;
    }

}
