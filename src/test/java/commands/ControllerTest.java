package commands;

import controller.Controller;
import org.junit.Test;
import org.mockito.Mockito;
import utils.Strings;
import utils.property_loaders.LoadConfigProperty;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ControllerTest extends Mockito {

    @Test
    public void processRequestTest() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Controller controller = new Controller();
        when(request.getParameter("command")).thenReturn("logOut");
        controller.processRequest(request, response);

        verify(response).sendRedirect(LoadConfigProperty.getInstance().getConfigProperty(Strings.PATH_PAGE_INDEX));
    }
}
