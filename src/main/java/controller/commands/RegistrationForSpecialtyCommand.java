package controller.commands;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class RegistrationForSpecialtyCommand implements ActionCommand {

    private static final Logger log = Logger.getLogger(String.valueOf(RegistrationForSpecialtyCommand.class));

    @Override
    public String execute(HttpServletRequest request) {
        String page = "";
        log.info("Start class RegistrationForSpecialtyCommand execute()");

        return page;
    }
}
