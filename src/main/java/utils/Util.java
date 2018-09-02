package utils;

import services.exceptions.NoSuchSpecialtyException;
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
import java.util.ArrayList;
import java.util.HashMap;

public class Util {

    private static final Logger log = Logger.getLogger(Util.class);

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

    public void checkIfDisplayCongratulationOnSpecialty(HttpServletRequest request) {
        log.info("Start class Util checkIfDisplayCongratulationOnSpecialty()");

        User user = (User) request.getSession().getAttribute(Strings.LOGINED_USER);
        try (UserService userService = new UserService()) {
            Specialty userSpecialty = userService.getPassedSpecialtyByUser(user.getId());
            if (userSpecialty != null) {
                request.getSession().setAttribute(Strings.USER_SPECIALTY, userSpecialty);
                request.getSession().setAttribute(Strings.IS_PASSED, Strings.YES);
            } else {
                request.getSession().setAttribute(Strings.IS_PASSED, Strings.NO);
            }
        } catch (NoSuchSpecialtyException e) {
            e.printStackTrace();
        }
    }

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

    public void generateListOfUsersAndTheirRateBySpecialties(HttpServletRequest request) {
        log.info("Start class Util generateListOfUsersAndTheirRateBySpecialties()");
        request.getSession().setAttribute(Strings.USER_GRADE_HASH_MAP,
                new CountGeneralGrade().fillListOfSpecialtiesAndUsers());
    }
}
