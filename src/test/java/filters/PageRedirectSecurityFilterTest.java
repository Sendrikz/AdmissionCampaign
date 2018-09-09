package filters;

import controller.filters.PageRedirectSecurityFilter;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Olha Yuryeva
 * @version 1.0
 */

public class PageRedirectSecurityFilterTest extends Mockito {

    @Test
    public void doFilterTest() throws IOException, ServletException {
        PageRedirectSecurityFilter filter = new PageRedirectSecurityFilter();
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);
        when(httpServletRequest.getRequestURI()).thenReturn("/jsp/admin/adminMain.jsp");
        filter.doFilter(httpServletRequest, httpServletResponse, filterChain);

        verify(httpServletResponse).sendRedirect("/index.jsp");
    }
}
