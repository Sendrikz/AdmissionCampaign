package controller.filters;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

import java.io.IOException;

/**
 * Filter which is used on all paths for encoding
 * @author Olha Yuryeva
 * @version 1.0
 */

@WebFilter(urlPatterns = { "/*" }, initParams = {
        @WebInitParam(name = "encoding", value = "UTF-8", description = "Encoding Param") })
public class EncodingFilter implements Filter {

    private String code;

    /**
     * @param filterConfig FilterConfig
     */
    @Override
    public void init(FilterConfig filterConfig) {
        code = filterConfig.getInitParameter("encoding");
    }

    /**
     * @param servletRequest ServletRequest
     * @param servletResponse ServletResponse
     * @param filterChain FilterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        String codeRequest = servletRequest.getCharacterEncoding();

        if (code != null && !code.equalsIgnoreCase(codeRequest)) {
            servletRequest.setCharacterEncoding(code);
            servletResponse.setCharacterEncoding(code);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        code = null;
    }
}
