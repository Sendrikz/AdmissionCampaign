package utils.grade_counter;
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

    public HashMap<Specialty, TreeMap<BigDecimal, User>> fillListOfSpecialtiesAndUsers() {
        log.info("Start class CountGeneralGrade fillListOfSpecialtiesAndUsers()");

        HashMap<Specialty, TreeMap<BigDecimal, User>> specialtyRatingList
                = new HashMap<>();

        try (SpecialtyService specialtyService = new SpecialtyService();
             UserService userService = new UserService()) {
            ArrayList<Specialty> listOfSpecialty = specialtyService.getAll();

            for (Specialty specialty : listOfSpecialty) {
                HashMap<User, HashMap<Subject, BigDecimal>> listOfUsersAndTheirGrades
                        = new HashMap<>();
                HashMap<Subject, BigDecimal> listOfSubjectsBySpecialty =
                        specialtyService.getAllSubjectsOfSpecialty(specialty.getId());

                ArrayList<User> listOfUsersBySpecialty = userService.getAllStudents(
                        specialty.getId());

                for (User user : listOfUsersBySpecialty) {
                    listOfUsersAndTheirGrades.put(user,
                            userService.getAllCheckedSubjectsByUser(user.getId()));
                }

                specialtyRatingList.put(specialty, countGrade(listOfSubjectsBySpecialty,
                        listOfUsersAndTheirGrades));

            }
        }
        return specialtyRatingList;
    }

    private TreeMap<BigDecimal, User> countGrade(HashMap<Subject, BigDecimal> listOfSubjectsAndTheirCoef,
                                                       HashMap<User, HashMap<Subject, BigDecimal>> listOfUsersAndTheirGrades) {

        log.info("Start class CountGeneralGrade countGrade()");

        TreeMap<BigDecimal, User> userGrade = new TreeMap<>(Comparator.reverseOrder());

        for (Map.Entry<User, HashMap<Subject, BigDecimal>> entryUser :
                listOfUsersAndTheirGrades.entrySet()) {

            BigDecimal generalGrade = new BigDecimal(0);
            for (Map.Entry<Subject, BigDecimal> entrySubject :
                    listOfSubjectsAndTheirCoef.entrySet()) {

                if (entryUser.getValue().get(entrySubject.getKey()) != null) {
                    generalGrade = generalGrade.add(entrySubject.getValue().multiply(
                            entryUser.getValue().get(entrySubject.getKey())));
                }
            }
            userGrade.put(generalGrade, entryUser.getKey());
        }
        return userGrade;
    }

}
