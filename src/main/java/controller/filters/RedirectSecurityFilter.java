package controller.filters;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(dispatcherTypes = {
        DispatcherType.INCLUDE
}, urlPatterns = { "/WEB-INF/view/*" },
        initParams = { @WebInitParam(name = "INDEX_PATH", value = "/index.jsp") })
public class RedirectSecurityFilter implements Filter {

    private static final Logger log = Logger.getLogger(String.valueOf(RedirectSecurityFilter.class));
    private String indexPath;

    @Override
    public void init(FilterConfig filterConfig) {
        indexPath = filterConfig.getInitParameter("INDEX_PATH");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        log.info("start class RedirectSecurityFilter doFiler()");
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        log.debug("Index path: " + indexPath);
        log.debug("Context path: " + httpServletRequest.getContextPath());
        httpServletResponse.sendRedirect("http://localhost:8081/");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
