package controller;

import controller.helpers.SessionRequestContent;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/controller")
public class Controller extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) {
        SessionRequestContent sqc = new SessionRequestContent();
        sqc.extractValues(req);
        try {
            resp.getWriter().write("It is working");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

//
//public class Controller extends HttpServlet {
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        University uni = new University(Universities.NaUKMA.getName(),
//                Universities.NaUKMA.getAddress());
//        req.setCharacterEncoding("UTF-8");
//        resp.setCharacterEncoding("UTF-8");
//        resp.setContentType ("text/html; charset=UTF-8");
//     //   ServletContext servletContext = req.getSession().getServletContext();
//   //     ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
//    //    InputStream input = classLoader.getResourceAsStream("sql.properties");
//        try (Connection connection = ConnectionManager.getInstance().getConnection();) {
//            UniversityDao uniDao = DaoFactory.getUniversityDao(connection);
//     //       uniDao.setPath(input);
//            connection.setAutoCommit(false);
//            resp.getWriter().write(uniDao.getAll().toString());
//            Savepoint s1 = connection.setSavepoint();
//            uniDao.clearAllUniversities();
//            connection.rollback(s1);
//            connection.commit();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        //FacultyDao facultyDao = DaoFactory.getFacultyDao(connection);
//        resp.getWriter().write(uni.toString());
//        resp.getWriter().write("It is working");
//    }
//}
