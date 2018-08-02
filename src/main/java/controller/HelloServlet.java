package controller;

import enums.Universities;
import model.connection.ConnectionManager;
import model.dao.UniversityDao;
import model.dao.UniversityJdbcDao;
import model.enteties.University;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;

public class HelloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        University uni = new University(Universities.NaUKMA.getName(),
                Universities.NaUKMA.getAddress());
        ServletContext servletContext = req.getSession().getServletContext();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream input = classLoader.getResourceAsStream("sql.properties");
        Connection connection = ConnectionManager.getInstance().getConnection();
        UniversityDao dao = new UniversityJdbcDao(connection);
        dao.setPath(input);
        dao.add(uni);
        resp.getWriter().write(uni.toString());
        resp.getWriter().write("It is working");
    }
}
