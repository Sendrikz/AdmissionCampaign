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

/**
 * Connects view part with model part for teamwork
 * @author Olha Yuryeva
 * @version 1.0
 */
@WebServlet("/vstup")
public class Controller extends HttpServlet {

    private static final Logger log = Logger.getLogger(String.valueOf(LoginCommand.class));

    /**
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);

    }

    /**
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    /**
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException
     * @throws IOException
     * @see ActionFactory
     * @see ActionCommand
     */
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
