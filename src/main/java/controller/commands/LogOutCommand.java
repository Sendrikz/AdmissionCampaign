package controller.commands;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LogOutCommand implements ActionCommand {

    private Properties property;

    LogOutCommand() {
        property = new Properties();
        try (InputStream is = this.getClass().getClassLoader().
                getResourceAsStream("config.properties")){
            property.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String execute(HttpServletRequest request) {
        request.getSession().invalidate();
        return property.getProperty("path.page.index");
    }
}
