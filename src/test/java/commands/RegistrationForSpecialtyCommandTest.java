package commands;

import controller.commands.RegistrationForSpecialtyCommand;
import model.builder.*;
import model.connection.ConnectionManager;
import model.dao.*;
import model.dao.factory.DaoFactory;
import model.dao.impl.FacultyJdbcDao;
import model.dao.impl.UniversityJdbcDao;
import model.enteties.*;
import model.enteties_enum.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import utils.Strings;
import utils.property_loaders.LoadConfigProperty;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.sql.Connection;
import static org.junit.Assert.assertEquals;

public class RegistrationForSpecialtyCommandTest extends Mockito {

    private Connection connection;
    private Role role;
    private Faculty faculty;
    private Subject subjectMath;
    private Subject subjectLanguage;
    private Specialty specialty;

    @Before
    public void setUp() {
        connection = ConnectionManager.getInstance().getConnectionToTestBD();
        role = new RoleBuilder().setName(Roles.STUDENT.getName()).createRole();
        RoleDao roleDao = DaoFactory.getRoleDao(connection);
        roleDao.add(role);
        UniversityDao universityDao = DaoFactory.getUniversityDao(connection);
        University university = new UniversityBuilder().setName(Universities.NaUKMA.getName())
                .setAddress(Universities.NaUKMA.getAddress())
                .setCity(Universities.NaUKMA.getCity())
                .createUniversity();
        universityDao.add(university);
        FacultyDao facultyDao = DaoFactory.getFacultyDao(connection);
        faculty = new FacultyBuilder().setName(Faculties.IT.getName())
                .setUniversityId(university.getId())
                .createFaculty();
        facultyDao.add(faculty);
        SubjectDao subjectDao = DaoFactory.getSubjectDao(connection);
        subjectMath = setUpNewMathSubject();
        subjectLanguage = setUpNewLanguageSubject();
        Subject subjectEnglish = setUpNewLanguageEnglishSubject();
        subjectDao.add(subjectMath);
        subjectDao.add(subjectLanguage);
        subjectDao.add(subjectEnglish);
        specialty = setUpNewCompSpecialty();
        SpecialtyDao specialtyDao = DaoFactory.getSpecialtyDao(connection);
        specialtyDao.add(specialty);
        subjectDao.addSubjectToSpecialty(subjectMath, specialty, new BigDecimal(0.4));
        subjectDao.addSubjectToSpecialty(subjectLanguage, specialty, new BigDecimal(0.5));
        subjectDao.addSubjectToSpecialty(subjectEnglish, specialty, new BigDecimal(0.4));
    }

    @After
    public void tearDown() {
        ConnectionManager.getInstance().close(connection);
    }

    private User setUpNewKostya() {
        return new UserBuilder()
                .setLastName(Users.KOSTYA.getLastName())
                .setFirstName(Users.KOSTYA.getFirstName())
                .setPatronymic(Users.KOSTYA.getPatronymic())
                .setBirthday(Users.KOSTYA.getBirthday())
                .setCity(Users.KOSTYA.getCity())
                .setEmail(Users.KOSTYA.getEmail())
                .setPassword(Users.KOSTYA.getPassword())
                .setRole(role.getId())
                .createUser();
    }

    private Specialty setUpNewCompSpecialty() {
        return new SpecialtyBuilder().setName(Specialties.COMPUTER_SCIENCE.getName())
                .setQuantityOfStudents(Specialties.COMPUTER_SCIENCE.getQuantityOfStudents())
                .setFacultyId(faculty.getId()).createSpecialty();
    }

    private Subject setUpNewMathSubject() {
        return new SubjectBuilder().setName(Subjects.MATH.getName()).setDuration(Subjects.MATH.getDuration()).createSubject();
    }

    private Subject setUpNewLanguageSubject() {
        return new SubjectBuilder().setName(Subjects.UA_LANGUAGE.getName()).setDuration(Subjects.UA_LANGUAGE.getDuration()).createSubject();
    }

    private Subject setUpNewLanguageEnglishSubject() {
        return new SubjectBuilder().setName(Subjects.ENG_LANGUAGE.getName())
                .setDuration(Subjects.ENG_LANGUAGE.getDuration())
                .createSubject();
    }

    @Test
    public void registrateStudentOnSpecialtyNotEnoughtSubjectsTest() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        User user = setUpNewKostya();
        UserDao userDao = DaoFactory.getUserDao(connection);
        userDao.add(user);
        userDao.addUserToSubject(user, subjectLanguage, false, new BigDecimal(0));
        userDao.addUserToSubject(user, subjectMath, false, new BigDecimal(0));
        when(request.getParameter(Strings.SPECILATY_TO_REGISTR_ID)).thenReturn(String.valueOf(specialty.getId()));
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute(Strings.LOGINED_USER)).thenReturn(user);
        RegistrationForSpecialtyCommand registration = new RegistrationForSpecialtyCommand();

        assertEquals(LoadConfigProperty.getInstance()
                .getConfigProperty(Strings.PATH_PAGE_STUDENT_SPECIALTIES),
                registration.execute(request));
    }
}
