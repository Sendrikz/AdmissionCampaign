package controller.commands;

import utils.property_loaders.LoadConfigProperty;

import javax.servlet.http.HttpServletRequest;

/**
 * Class will be executed if the servlet
 * rolling without command
 * @author Olha Yuryeva
 * @version 1.0
 */
public class EmptyCommand implements ActionCommand {

    /**
     *
     * @param request HttpServletRequest
     * @return String path to login page
     */
    @Override
    public String execute(HttpServletRequest request) {
        return LoadConfigProperty.getInstance().getConfigProperty("path.page.login");
    }

}
