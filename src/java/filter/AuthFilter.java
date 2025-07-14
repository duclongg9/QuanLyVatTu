///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package filter;
//
//import jakarta.servlet.Filter;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.ServletRequest;
//import jakarta.servlet.ServletResponse;
//import jakarta.servlet.annotation.WebFilter;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import java.io.IOException;
//
///**
// *
// * @author admin
// */
//@WebFilter("/*")
//public class AuthFilter implements Filter {
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//        throws IOException, ServletException {
//
//        HttpServletRequest req = (HttpServletRequest) request;
//        HttpServletResponse res = (HttpServletResponse) response;
//
//        // Chặn cache
//        res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
//        res.setHeader("Pragma", "no-cache");
//        res.setDateHeader("Expires", 0);
//
//        HttpSession session = req.getSession(false);
//        String uri = req.getRequestURI();
//
//        boolean loggedIn = (session != null && session.getAttribute("loggedInUser") != null);
//        boolean loginRequest = uri.endsWith("login") || uri.endsWith("login.jsp");
//        boolean staticResource = uri.contains("/assets/") || uri.matches(".*(\\.css|\\.js|\\.png|\\.jpg|\\.jpeg|\\.gif|\\.woff2|\\.ttf|\\.ico)$");
//        boolean forgotPasswordRequest = uri.contains("forgotpassword");
//boolean verifyOtpRequest = uri.contains("verifyotp");
//boolean resetPasswordRequest = uri.contains("resetpassword");
//
//        
//        if (loggedIn || loginRequest || staticResource || forgotPasswordRequest || verifyOtpRequest || resetPasswordRequest) {
//            chain.doFilter(request, response);
//        } else {
//            res.sendRedirect(req.getContextPath() + "/login");
//           // res.sendRedirect("login");
//        }
//    }
//}

package filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        // Chặn cache
        res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        res.setHeader("Pragma", "no-cache");
        res.setDateHeader("Expires", 0);

        HttpSession session = req.getSession(false);
        String uri = req.getRequestURI();

        boolean loggedIn = (session != null && session.getAttribute("loggedInUser") != null);
        boolean loginRequest = uri.endsWith("login") || uri.endsWith("login.jsp");
        boolean forgotPasswordRequest = uri.endsWith("forgotpassword") || uri.endsWith("forgotpassword.jsp");
        boolean verifyOtpRequest = uri.endsWith("verifyotp") || uri.endsWith("verifyotp.jsp");
        boolean resetPasswordRequest = uri.endsWith("resetpassword") || uri.endsWith("resetpassword.jsp");
        boolean staticResource = uri.contains("/assets/") || uri.matches(".*(\\.css|\\.js|\\.png|\\.jpg|\\.jpeg|\\.gif|\\.woff2|\\.ttf|\\.ico)$");

        if (loggedIn || loginRequest || forgotPasswordRequest || verifyOtpRequest || resetPasswordRequest || staticResource) {
            chain.doFilter(request, response);
        } else {
            res.sendRedirect(req.getContextPath() + "/login");
        }
    }
}