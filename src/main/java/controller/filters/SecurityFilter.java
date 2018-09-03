//package controller.filters;
//
//import utils.Strings;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//
//@WebFilter(urlPatterns = { "/vstup" }, servletNames = { "Controller" })
//
//public class SecurityFilter implements Filter {
//
//
//    @Override
//    public void init(FilterConfig filterConfig) {
//    }
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
//            HttpServletRequest req = (HttpServletRequest) request;
//            HttpServletResponse resp = (HttpServletResponse) response;
//            HttpSession session = req.getSession();
//            String role = (String) session.getAttribute(Strings.ROLE);
//            if (role == null) {
//                role = Strings.GUEST;
//                session.setAttribute("role", role);
//                RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
//                dispatcher.forward(req, resp);
//                return;
//            }
//            chain.doFilter(request, response);
//    }
//
//    @Override
//    public void destroy() {
//
//    }
//}
