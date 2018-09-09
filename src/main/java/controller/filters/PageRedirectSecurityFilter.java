package controller.filters;

import utils.Strings;
import utils.property_loaders.LoadConfigProperty;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filter is used only for pages like /jsp/* and if user try to go on such
 * page it redirect him to login page
 * @author Olha Yuryeva
 * @version 1.0
 */

@WebFilter(urlPatterns = {"/jsp/*"})
public class PageRedirectSecurityFilter implements Filter {

    /**
     * @param filterConfig FilterConfig
     */
    @Override
    public void init(FilterConfig filterConfig) {

    }

    /**
     * @param servletRequest ServletRequest
     * @param servletResponse ServletResponse
     * @param filterChain FilterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        httpResponse.sendRedirect(LoadConfigProperty.getInstance()
                .getConfigProperty(Strings.PATH_PAGE_INDEX));
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }
}


