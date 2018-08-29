package controller.util;

import controller.grade_counter.CountGeneralGrade;
import controller.pagination.Pagination;
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

    public static void checkIfDisplayUserSubjectsAndGrade(HttpServletRequest request) {
        log.info("Start class Util checkIfDisplayUserSubjectsAndGrade()");
        User user = (User) request.getSession().getAttribute("loginedUser");
        HashMap<Subject, BigDecimal> listOfSubjectsByUser =
                UserService.getAllCheckedSubjectsByUser(user.getId());
        if (listOfSubjectsByUser.size() != 0) {
            request.getSession().setAttribute("isChecked", "yes");
            request.getSession().setAttribute("subjectGradeList", listOfSubjectsByUser);
        } else {
            request.getSession().setAttribute("isChecked", "no");
        }
    }

    public static void checkIfDisplayCongratulationOnSpecialty(HttpServletRequest request) {
        log.info("Start class Util checkIfDisplayCongratulationOnSpecialty()");
        User user = (User) request.getSession().getAttribute("loginedUser");
        Specialty userSpecialty = UserService.getPassedSpecialtyByUser(user.getId());
        if (userSpecialty != null) {
            request.getSession().setAttribute("userSpecialty", userSpecialty);
            request.getSession().setAttribute("isPassed", "yes");
        } else {
            request.getSession().setAttribute("isPassed", "no");
        }
    }

    public static void generatePaginationSpecialties(HttpServletRequest request, int currentPage,
                                                     int recordsPerPage) {
        log.info("Start class Util generatePaginationSpecialties()");
        ArrayList<Specialty> paginationSpecialties =
                SpecialtyService.getAll(currentPage, recordsPerPage);
        request.getSession().setAttribute("paginationSpecialties", paginationSpecialties);
        int rows = SpecialtyService.getRows();
        int nOfPages = Pagination.countNumberOfPages(rows, Pagination.FIVE_RECORDS_PER_PAGE);
        request.getSession().setAttribute("noOfPages", nOfPages);
    }

    public static void generateListOfSubjectsForUserAndUsersWhichPassThem(HttpServletRequest request) {
        log.info("Start class Util generateListOfSubjectsForUserAndUsersWhichPassThem()");
        ArrayList<Subject> listOfSubjects = LoginService.getAllSubjects();
        request.getSession().setAttribute("subjectsList", listOfSubjects);

        HashMap<Subject, ArrayList<User>> subjectUserHashMap = new HashMap<>();
        for (Subject subject : listOfSubjects) {
            subjectUserHashMap.put(subject, SubjectService.getAllUsersWithUncheckedSubject(subject.getId()));
        }
        request.getSession().setAttribute("subjectUserHashMap", subjectUserHashMap);
    }

    public static void generateListOfUsersAndTheirRateBySpecialties(HttpServletRequest request) {
        log.info("Start class Util generateListOfUsersAndTheirRateBySpecialties()");
        request.getSession().setAttribute("specialtyUserGradeHashMap", CountGeneralGrade.fillListOfSpecialtiesAndUsers());
    }
}
