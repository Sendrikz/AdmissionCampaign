package controller;

import controller.commands.ActionCommand;
import controller.commands.ActionFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Properties;

@WebServlet("/controller")
public class Controller extends HttpServlet {


    private Properties property;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String page;
        ActionFactory actionFactory = new ActionFactory();
        ActionCommand command = actionFactory.defineCommand(req);
        page = command.execute(req);
        if (page.equals("loginFail") || page.equals("RegistrationFail")) {
            resp.getWriter().write("<script src='https://unpkg.com/sweetalert/dist/sweetalert.min.js'></script>");
            resp.getWriter().write("<script src='https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js'></script>");
            resp.getWriter().write("<script>");
            resp.getWriter().write("$(document).ready(function() {");
            resp.getWriter().write("swal('Try again!', 'There is no such user or such user already" +
                    " created', 'error');");
            resp.getWriter().write("});");
            resp.getWriter().write("</script>");
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/view/login.jsp");
            requestDispatcher.include(req, resp);
        } else {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
            dispatcher.forward(req, resp);
        }
//        }else if (page != null) {
//            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
//            dispatcher.forward(req, resp);
//        } else {
//            property = new Properties();
//            try (InputStream is = this.getClass().getClassLoader().
//                    getResourceAsStream("config.properties")){
//                property.load(is);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            page = property.getProperty("path.page.login");
//            resp.sendRedirect(page);
//        }
    }
}
