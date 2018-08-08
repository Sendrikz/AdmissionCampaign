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
import java.io.InputStream;
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
        if (page != null) {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
            dispatcher.forward(req, resp);
        } else {
            property = new Properties();
            try (InputStream is = this.getClass().getClassLoader().
                    getResourceAsStream("config.properties")){
                property.load(is);
            } catch (IOException e) {
                e.printStackTrace();
            }
            page = property.getProperty("path.page.login");
            resp.sendRedirect(page);
        }
    }
}
