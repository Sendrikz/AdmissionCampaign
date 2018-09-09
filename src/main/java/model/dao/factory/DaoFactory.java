package model.dao.factory;

import model.dao.*;
import model.dao.impl.*;

import java.sql.Connection;

/**
 * @author Olha Yuryeva
 * @version 1.0
 */
public class DaoFactory {

    /**
     * @param connection Connection
     * @return FacultyDao
     * @see FacultyJdbcDao
     */
    public static FacultyDao getFacultyDao(Connection connection) {
        return new FacultyJdbcDao(connection);
    }

    /**
     * @param connection Connection
     * @return RoleDao
     * @see RoleJdbcDao
     */
    public static RoleDao getRoleDao(Connection connection) {
        return new RoleJdbcDao(connection);
    }

    /**
     * @param connection Connection
     * @return SpecialtyDao
     * @see SpecialtyJdbcDao
     */
    public static SpecialtyDao getSpecialtyDao(Connection connection) {
        return new SpecialtyJdbcDao(connection);
    }

    /**
     * @param connection Connection
     * @return SubjectDao
     * @see SubjectJdbcDao
     */
    public static SubjectDao getSubjectDao(Connection connection) {
        return new SubjectJdbcDao(connection);
    }

    /**
     * @param connection Connection
     * @return UniversityDao
     * @see UniversityJdbcDao
     */
    public static UniversityDao getUniversityDao(Connection connection) {
        return new UniversityJdbcDao(connection);
    }

    /**
     * @param connection Connection
     * @return UserDao
     * @see UserJdbcDao
     */
    public static UserDao getUserDao(Connection connection) {
        return new UserJdbcDao(connection);
    }
}
