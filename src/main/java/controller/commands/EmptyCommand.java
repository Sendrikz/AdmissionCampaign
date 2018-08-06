package controller.commands;

import controller.helpers.SessionRequestContent;

public class EmptyCommand implements ActionCommand {
    @Override
    public String execute(SessionRequestContent sqc) {
        /* в случае ошибки или прямого обращения к контроллеру
         * переадресация на страницу ввода логина */
        String page = "/WEB-INF/view/login.jsp";
        return page;
    }
}
