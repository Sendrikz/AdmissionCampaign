package controller;
import model.enteties.Specialty;
import model.enteties.Subject;
import model.enteties.User;
import org.apache.log4j.Logger;
import services.SpecialtyService;
import services.UserService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class CountGeneralGrade {

    private static final Logger log = Logger.getLogger(CountGeneralGrade.class);

    public static HashMap<Specialty, HashMap<BigDecimal, User>> fillListOfSpecialtiesAndUsers() {
        log.info("Start class CountGeneralGrade countGrade()");
        HashMap<Specialty, HashMap<BigDecimal, User>> specialtyRatingList
                = new HashMap<>();
        HashMap<User, HashMap<Subject, BigDecimal>> listOfUsersAndTheirGrades
                = new HashMap<>();

        ArrayList<User> listOfAllUsers = UserService.getAll();
        for (User user : listOfAllUsers) {
            listOfUsersAndTheirGrades.put(user,
                    UserService.getAllCheckedSubjectsByUser(user.getId()));
        }
        log.debug("List of all users: " + listOfAllUsers);

        ArrayList<Specialty> listOfAllSpecialties = SpecialtyService.getAll();
        log.debug("List of all specialties: " + listOfAllSpecialties);

        for (Specialty specialty : listOfAllSpecialties) {

            HashMap<Subject, BigDecimal> listOfSubjectsAndTheirCoef
                    = SpecialtyService.getAllSubjectsOfSpecialty(specialty.getId());
            log.debug("List of all subjects of specialty: " + listOfSubjectsAndTheirCoef);

            specialtyRatingList.put(specialty, countGrade(listOfSubjectsAndTheirCoef,
                    listOfUsersAndTheirGrades));
            log.debug("SpecialtyRatingList: " + specialtyRatingList);
        }

        return specialtyRatingList;
    }

    public static HashMap<BigDecimal, User> countGrade(HashMap<Subject, BigDecimal> listOfSubjectsAndTheirCoef,
                                                       HashMap<User, HashMap<Subject, BigDecimal>> listOfUsersAndTheirGrades) {

        log.debug("List of all users and their grades: " + listOfUsersAndTheirGrades);
        HashMap<BigDecimal, User> userGrade = new HashMap<>();
        for (Map.Entry<User, HashMap<Subject, BigDecimal>> entryUser :
                listOfUsersAndTheirGrades.entrySet()) {

            BigDecimal generalGrade = new BigDecimal(0);
            for (Map.Entry<Subject, BigDecimal> entrySubject :
                    listOfSubjectsAndTheirCoef.entrySet()) {

                generalGrade = generalGrade.add(entrySubject.getValue().multiply(
                        entryUser.getValue().get(entrySubject.getKey())));
                log.debug("General grade: " + generalGrade);
            }
            userGrade.put(generalGrade, entryUser.getKey());
            log.debug("User grade TreeMap: " + userGrade);
        }
        return userGrade;
    }

}
