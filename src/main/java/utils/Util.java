package utils;

import utils.grade_counter.CountGeneralGrade;
import utils.pagination.Pagination;
import model.enteties.Specialty;
import model.enteties.Subject;
import model.enteties.User;
import org.apache.log4j.Logger;
import services.LoginService;
import services.SpecialtyService;
import services.SubjectService;
import services.UserService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Olha Yuryeva
 * @version 1.0
 */

public class Util {

    private static final Logger log = Logger.getLogger(Util.class);

    public Util() {}

    /**
     * Check if user already have a grade for subject and if he is - show table of grades
     * @param request HttpServletRequest
     */
    public void checkIfDisplayUserSubjectsAndGrade(HttpServletRequest request) {
        log.info("Start class Util checkIfDisplayUserSubjectsAndGrade()");

        User user = (User) request.getSession().getAttribute(Strings.LOGINED_USER);
        try (UserService userService = new UserService()) {
            HashMap<Subject, BigDecimal> listOfSubjectsByUser =
                    userService.getAllCheckedSubjectsByUser(user.getId());
            if (listOfSubjectsByUser.size() != 0) {
                request.getSession().setAttribute(Strings.IS_CHECKED, Strings.YES);
                request.getSession().setAttribute(Strings.SUBJECT_GRADE_LIST, listOfSubjectsByUser);
            } else {
                request.getSession().setAttribute(Strings.IS_CHECKED, Strings.NO);
            }
        }
    }

    /**
     * If user passed on specialty - system show corresponding message
     * @param request HttpServletRequest
     */
    public void checkIfDisplayCongratulationOnSpecialty(HttpServletRequest request) {
        log.info("Start class Util checkIfDisplayCongratulationOnSpecialty()");

        User user = (User) request.getSession().getAttribute(Strings.LOGINED_USER);
        try (UserService userService = new UserService()) {
            Specialty userSpecialty;
            if (userService.getPassedSpecialtyByUser(user.getId()).isPresent()) {
                userSpecialty = userService.getPassedSpecialtyByUser(user.getId()).get();
                request.getSession().setAttribute(Strings.USER_SPECIALTY, userSpecialty);
                request.getSession().setAttribute(Strings.IS_PASSED, Strings.YES);
            } else {
                request.getSession().setAttribute(Strings.IS_PASSED, Strings.NO);
            }
        }
    }

    /**
     * Get from database limited information to generate pagination
     * @param request HttpServletRequest
     * @param currentPage int
     * @param recordsPerPage int
     */
    public void generatePaginationSpecialties(HttpServletRequest request, int currentPage,
                                                     int recordsPerPage) {
        log.info("Start class Util generatePaginationSpecialties()");

        try (SpecialtyService specialtyService = new SpecialtyService()) {
            ArrayList<Specialty> paginationSpecialties =
                    specialtyService.getAll(currentPage, recordsPerPage);
            request.getSession().setAttribute(Strings.PAGINATION_SPECILATIES, paginationSpecialties);
            int rows = specialtyService.getRows();
            int nOfPages = Pagination.countNumberOfPages(rows, Pagination.FIVE_RECORDS_PER_PAGE);
            request.getSession().setAttribute(Strings.NO_OF_PAGES, nOfPages);
        }
    }

    /**
     * @param request HttpServletRequest
     */
    public void generateListOfSubjectsForUserAndUsersWhichPassThem(HttpServletRequest request) {
        log.info("Start class Util generateListOfSubjectsForUserAndUsersWhichPassThem()");

        try (LoginService loginService = new LoginService();
             SubjectService subjectService = new SubjectService()) {
            ArrayList<Subject> listOfSubjects = loginService.getAllSubjects();
            request.getSession().setAttribute(Strings.SUBJECTS_LIST, listOfSubjects);

            HashMap<Subject, ArrayList<User>> subjectUserHashMap = new HashMap<>();
            for (Subject subject : listOfSubjects) {
                subjectUserHashMap.put(subject, subjectService.getAllUsersWithUncheckedSubject(subject.getId()));
            }
            request.getSession().setAttribute(Strings.SUBJECT_USER_HASH_MAP, subjectUserHashMap);
        }
    }

    /**
     * @param request HttpServletRequest
     */
    public void generateListOfUsersAndTheirRateBySpecialties(HttpServletRequest request) {
        log.info("Start class Util generateListOfUsersAndTheirRateBySpecialties()");
        request.getSession().setAttribute(Strings.USER_GRADE_HASH_MAP,
                new CountGeneralGrade().fillListOfSpecialtiesAndUsers());
    }
}
