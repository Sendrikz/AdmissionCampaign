//package controller.listeners;
//
//import javax.servlet.annotation.WebListener;
//import javax.servlet.http.HttpSessionEvent;
//import javax.servlet.http.HttpSessionListener;
//
//@WebListener
//public class SessionListener implements HttpSessionListener {
//    @Override
//    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
//
//    }
//
//    @Override
//    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
//        httpSessionEvent.getSession().getServletContext().removeAttribute(
//                (String)httpSessionEvent.getSession().getAttribute("userSession")
//        );
//    }
//}
