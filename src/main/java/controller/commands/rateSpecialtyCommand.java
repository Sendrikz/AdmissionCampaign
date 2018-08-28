package controller.commands;

import controller.transaction.TranactionStudentSpecialty;
import model.enteties.Specialty;
import model.enteties.User;
import org.apache.log4j.Logger;
import services.SpecialtyService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Properties;
import java.util.TreeMap;

public class rateSpecialtyCommand implements ActionCommand {
    private static final Logger log = Logger.getLogger(rateSpecialtyCommand.class);
    private Properties property;

    rateSpecialtyCommand() {
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
            if (count < availableQuantity) {
                TranactionStudentSpecialty.updateUserSpecialty(user.getId(),
                        selectedSpecialty.getId());
            }
            count++;
        }
        log.debug("Count = " + count);
        return property.getProperty("path.page.adminMain");
    }
}
