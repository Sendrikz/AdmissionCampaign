package controller.commands;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class EmptyCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest request) {
        /* в случае ошибки или прямого обращения к контроллеру
         * переадресация на страницу ввода логина */
        Properties property = new Properties();
        try (InputStream is = this.getClass().getClassLoader().
                getResourceAsStream("config.properties")){
            property.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return property.getProperty("path.page.login");
    }
}
