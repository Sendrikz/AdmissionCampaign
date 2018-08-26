//package controller.filters;
//
//import org.apache.log4j.Logger;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//
//@WebFilter(urlPatterns = { "/*" })
//
//public class SecurityFilter implements Filter {
//
//    private static final Logger log = Logger.getLogger(SecurityFilter.class);
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//
//    }
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        log.info("Start class SecurityFilter doFilter()");
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        HttpServletResponse response = (HttpServletResponse) servletResponse;
//        HttpSession session = request.getSession();
//        String role = (String) session.getAttribute("role");
//        log.debug("Role: " + role);
//        if (role == null) {
//            session.setAttribute("role", "Guest");
//            RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
//            dispatcher.forward(request, response);
//            return;
//        }
//
//        filterChain.doFilter(request, response);
//    }
//
//    @Override
//    public void destroy() {
//
//    }
//}
