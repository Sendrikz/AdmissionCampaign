package controller;

import controller.commands.ActionCommand;
import controller.commands.factory.ActionFactory;
import controller.commands.LoginCommand;
import utils.property_loaders.LoadConfigProperty;
import utils.Strings;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/vstup")
public class Controller extends HttpServlet {

    private static final Logger log = Logger.getLogger(String.valueOf(LoginCommand.class));

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    public void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Start class Controller processRequest()");
        String page;
        ActionFactory actionFactory = new ActionFactory();
        ActionCommand command = actionFactory.defineCommand(req);
        page = command.execute(req);
        log.debug("Page = " + page);

        if (page != null && page.contains(":LogOut")) {
            resp.sendRedirect(LoadConfigProperty.getInstance().getConfigProperty(Strings.PATH_PAGE_INDEX));
        } else if (page != null) {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
            dispatcher.forward(req, resp);
        }
    }
}
