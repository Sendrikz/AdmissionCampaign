package controller;

import controller.pagination.Pagination;
import model.enteties.Specialty;
import model.enteties.Subject;
import model.enteties.User;
import services.LoginService;
import services.SpecialtyService;
import services.SubjectService;
import services.UserService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

public class Util {

    public static void checkIfDisplayUserSubjectsAndGrade(HttpServletRequest request) {
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
        ArrayList<Specialty> paginationSpecialties =
                SpecialtyService.getAll(currentPage, recordsPerPage);
        request.getSession().setAttribute("paginationSpecialties", paginationSpecialties);
        int rows = SpecialtyService.getRows();
        int nOfPages = Pagination.countNumberOfPages(rows, Pagination.FIVE_RECORDS_PER_PAGE);
        request.getSession().setAttribute("noOfPages", nOfPages);
    }

    public static void generateListOfSubjectsForUserAndUsersWhichPassThem(HttpServletRequest request) {
        ArrayList<Subject> listOfSubjects = LoginService.getAllSubjects();
        request.getSession().setAttribute("subjectsList", listOfSubjects);

        HashMap<Subject, ArrayList<User>> subjectUserHashMap = new HashMap<>();
        for (Subject subject : listOfSubjects) {
            subjectUserHashMap.put(subject, SubjectService.getAllUsersWithUncheckedSubject(subject.getId()));
        }
        request.getSession().setAttribute("subjectUserHashMap", subjectUserHashMap);
    }

    public static void generateListOfUsersAndTheirRateBySpecialties(HttpServletRequest request) {
        request.getSession().setAttribute("specialtyUserGradeHashMap", CountGeneralGrade.fillListOfSpecialtiesAndUsers());
    }
}
