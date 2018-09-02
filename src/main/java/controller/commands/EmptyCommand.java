package controller.commands;

import utils.property_loaders.LoadConfigProperty;

import javax.servlet.http.HttpServletRequest;

public class EmptyCommand implements ActionCommand {

    @Override
    public String execute(HttpServletRequest request) {
        return LoadConfigProperty.getInstance().getConfigProperty("path.page.login");
    }

}
