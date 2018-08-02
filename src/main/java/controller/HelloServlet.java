package controller;

import enums.Universities;
import model.connection.ConnectionManager;
import model.dao.DaoFactory;
import model.dao.dao_interfaces.UniversityDao;
import model.enteties.University;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;

public class HelloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        University uni = new University(Universities.NaUKMA.getName(),
                Universities.NaUKMA.getAddress());
        ServletContext servletContext = req.getSession().getServletContext();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream input = classLoader.getResourceAsStream("sql.properties");
        try (Connection connection = ConnectionManager.getInstance().getConnection();) {
            UniversityDao uniDao = DaoFactory.getUniversityDao(connection);
            uniDao.setPath(input);
            connection.setAutoCommit(false);
            uniDao.add(uni);
            Savepoint s1 = connection.setSavepoint();
            uniDao.clearAllUniversities();
            connection.rollback(s1);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //FacultyDao facultyDao = DaoFactory.getFacultyDao(connection);
        resp.getWriter().write(uni.toString());
        resp.getWriter().write("It is working");
    }
}
