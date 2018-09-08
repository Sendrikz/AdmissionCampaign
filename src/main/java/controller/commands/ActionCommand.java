package controller.commands;

import javax.servlet.http.HttpServletRequest;

/**
 * The ActionCommand interface defines one action
 * execute(), the implementation of the interface is defined in the
 * methods with corresponding logic
 * @author Olha Yuryeva
 * @version 1.0
 */

public interface ActionCommand {

    /**
     * @param request HttpServletRequest
     * @return String page
     */
    String execute(HttpServletRequest request);

}
