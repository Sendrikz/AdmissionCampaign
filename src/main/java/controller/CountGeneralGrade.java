package controller;
import model.enteties.Specialty;
import model.enteties.Subject;
import model.enteties.User;
import org.apache.log4j.Logger;
import services.SpecialtyService;
import services.UserService;

import java.math.BigDecimal;
import java.util.*;

public class CountGeneralGrade {

    private static final Logger log = Logger.getLogger(CountGeneralGrade.class);

    public static HashMap<Specialty, TreeMap<BigDecimal, User>> fillListOfSpecialtiesAndUsers() {
        log.info("Start class CountGeneralGrade countGrade()");
        HashMap<Specialty, TreeMap<BigDecimal, User>> specialtyRatingList
                = new HashMap<>();

        ArrayList<Specialty> listOfSpecialty = SpecialtyService.getAll();
        for (Specialty specialty : listOfSpecialty) {
            HashMap<User, HashMap<Subject, BigDecimal>> listOfUsersAndTheirGrades
                = new HashMap<>();
            HashMap<Subject, BigDecimal> listOfSubjectsBySpecialty =
                    SpecialtyService.getAllSubjectsOfSpecialty(specialty.getId());

            ArrayList<User> listOfUsersBySpecialty = UserService.getAllStudents(
                    specialty.getId());
            for (User user : listOfUsersBySpecialty) {
                listOfUsersAndTheirGrades.put(user,
                        UserService.getAllCheckedSubjectsByUser(user.getId()));
            }

            specialtyRatingList.put(specialty, countGrade(listOfSubjectsBySpecialty,
                        listOfUsersAndTheirGrades));

        }

        return specialtyRatingList;
    }

    static TreeMap<BigDecimal, User> countGrade(HashMap<Subject, BigDecimal> listOfSubjectsAndTheirCoef,
                                                       HashMap<User, HashMap<Subject, BigDecimal>> listOfUsersAndTheirGrades) {

        log.debug("List of all users and their grades: " + listOfUsersAndTheirGrades);
        TreeMap<BigDecimal, User> userGrade = new TreeMap<>(new ComparatorDescend());
        for (Map.Entry<User, HashMap<Subject, BigDecimal>> entryUser :
                listOfUsersAndTheirGrades.entrySet()) {

            BigDecimal generalGrade = new BigDecimal(0);
            for (Map.Entry<Subject, BigDecimal> entrySubject :
                    listOfSubjectsAndTheirCoef.entrySet()) {

                if (entryUser.getValue().get(entrySubject.getKey()) != null) {
                    generalGrade = generalGrade.add(entrySubject.getValue().multiply(
                            entryUser.getValue().get(entrySubject.getKey())));
                    log.debug("General grade: " + generalGrade);
                }
            }
            userGrade.put(generalGrade, entryUser.getKey());
            log.debug("User grade TreeMap: " + userGrade);
        }
        return userGrade;
    }

}
