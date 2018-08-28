package controller.commands;

import controller.transaction.TransactionStudentSpecialty;
import model.enteties.Specialty;
import model.enteties.User;
import org.apache.log4j.Logger;
import services.SpecialtyService;
import services.UserService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Properties;
import java.util.TreeMap;

public class RateSpecialtyCommand implements ActionCommand {
    private static final Logger log = Logger.getLogger(RateSpecialtyCommand.class);
    private Properties property;

    RateSpecialtyCommand() {
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
        log.info("Start class rateSpecialtyCommand execute()");
        String page = property.getProperty("path.page.adminMain");
        int currentSpecialtyId = Integer.parseInt(request.getParameter("specialtyId"));
        HashMap<Specialty, TreeMap<BigDecimal, User>> specialtyTreeMapHashMap
                = (HashMap<Specialty, TreeMap<BigDecimal, User>>)
                request.getSession().getAttribute("specialtyUserGradeHashMap");
        Specialty selectedSpecialty = SpecialtyService.findById(currentSpecialtyId);
        log.debug("Selected specialty: " + selectedSpecialty);
        int availableQuantity = selectedSpecialty.getQuantityOfStudents();
        TreeMap<BigDecimal, User> userTreeMap = specialtyTreeMapHashMap.get(selectedSpecialty);
        int count = 0;
        for (User user : userTreeMap.values()) {
            if (UserService.getPassedSpecialtyByUser(user.getId()) != null) {
                request.getSession().setAttribute("confirmRate", "no");
                return page;
            }
            if (count < availableQuantity) {
                TransactionStudentSpecialty.updateUserSpecialty(user.getId(),
                        selectedSpecialty.getId());
            }
            count++;
        }
        log.debug("Count = " + count);
        request.getSession().setAttribute("confirmRate", "yes");
        return page;
    }
}
