package model.dao;

import model.dao.dao_implementations.*;
import model.dao.dao_interfaces.*;

import java.sql.Connection;

public class DaoFactory {

    public static FacultyDao getFacultyDao(Connection connection) {
        return new FacultyJdbcDao(connection);
    }

    public static RoleDao getRoleDao(Connection connection) {
        return new RoleJdbcDao(connection);
    }

    public static SpecialtyDao getSpecialtyDao(Connection connection) {
        return new SpecialtyJdbcDao(connection);
    }

    public static SubjectDao getSubjectDao(Connection connection) {
        return new SubjectJdbcDao(connection);
    }

    public static UniversityDao getUniversityDao(Connection connection) {
        return new UniversityJdbcDao(connection);
    }

    public static UserDao getUserDao(Connection connection) {
        return new UserJdbcDao(connection);
    }
}
