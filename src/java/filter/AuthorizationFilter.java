///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Filter.java to edit this template
// */
//package filter;
//
//import dao.role.PermissionDAO;
//import java.io.IOException;
//import java.io.PrintStream;
//import java.io.PrintWriter;
//import java.io.StringWriter;
//import jakarta.servlet.Filter;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.FilterConfig;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.ServletRequest;
//import jakarta.servlet.ServletResponse;
//import jakarta.servlet.annotation.WebFilter;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import model.Permission;
//import dao.role.PermissionRoleDAO;
//
///**
// *
// * @author
// */
//@WebFilter("/*")
//public class AuthorizationFilter implements Filter {
//
//    private static final boolean debug = true;
//    private FilterConfig filterConfig = null;
//
//    public AuthorizationFilter() {
//    }
//
//    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
//            throws IOException, ServletException {
//        if (debug) {
//            log("AuthorizationFilter:DoBeforeProcessing");
//        }
//
//    }
//
//    private void doAfterProcessing(ServletRequest request, ServletResponse response)
//            throws IOException, ServletException {
//        if (debug) {
//            log("AuthorizationFilter:DoAfterProcessing");
//        }
//    }
//
//    @Override
//public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//        throws IOException, ServletException {
//
//    HttpServletRequest req = (HttpServletRequest) request;
//    HttpServletResponse res = (HttpServletResponse) response;
//
//    String contextPath = req.getContextPath();
//    String uri = req.getRequestURI();
//    String path = uri.substring(contextPath.length());
//    
//
//    // 1. trang không cần login
//    if (path.matches(".*\\.(png|jpg|jpeg|gif|css|js|ico|woff|woff2|ttf|svg)$")
//        || path.equals("/login") || path.equals("/login.jsp")
//        || path.equals("/home")|| path.equals("/board.jsp")
//        || path.equals("/403") || path.equals("/403.jsp")
//          || path.equals("/logoutController") || path.equals("/permission") 
//            || path.equals("/userprofile")|| path.equals("/userprofile.jsp")
//            || path.equals("/updateprofile")|| path.equals("/updateprofile.jsp")
//            || path.equals("/changepassword") || path.equals("/changePassword.jsp")
//            || path.equals("/suppliercontroller") || path.equals("/supplier.jsp"))
//    {
//        chain.doFilter(request, response);
//        return;
//    }
//
//    // 2. Kiểm tra đăng nhập
//    HttpSession session = req.getSession(false); // false để không tự tạo session mới
//    Object roleObj = (session != null) ? session.getAttribute("role_id") : null;
//
//    if (roleObj == null) {
//        res.sendRedirect(contextPath + "/login");
//        return;
//    }
//
//    // 3. Kiểm tra phân quyền
//    int roleId = (Integer) roleObj;
//    PermissionRoleDAO dao = new PermissionRoleDAO();
//
//    boolean allowed = dao.permissionCheck(roleId, path);
//    if (!allowed) {
//        res.sendRedirect(contextPath + "/403");
//        return;
//    }
//
//    // 4. Nếu hợp lệ, cho phép đi tiếp
//    chain.doFilter(request, response);
//}
//
//    /**
//     * Return the filter configuration object for this filter.
//     */
//    public FilterConfig getFilterConfig() {
//        return (this.filterConfig);
//    }
//
//    /**
//     * Set the filter configuration object for this filter.
//     *
//     * @param filterConfig The filter configuration object
//     */
//    public void setFilterConfig(FilterConfig filterConfig) {
//        this.filterConfig = filterConfig;
//    }
//
//    /**
//     * Destroy method for this filter
//     */
//    public void destroy() {
//    }
//
//    /**
//     * Init method for this filter
//     */
//    public void init(FilterConfig filterConfig) {
//        this.filterConfig = filterConfig;
//        if (filterConfig != null) {
//            if (debug) {
//                log("AuthorizationFilter:Initializing filter");
//            }
//        }
//    }
//
//    /**
//     * Return a String representation of this object.
//     */
//    @Override
//    public String toString() {
//        if (filterConfig == null) {
//            return ("AuthorizationFilter()");
//        }
//        StringBuffer sb = new StringBuffer("AuthorizationFilter(");
//        sb.append(filterConfig);
//        sb.append(")");
//        return (sb.toString());
//    }
//
//    private void sendProcessingError(Throwable t, ServletResponse response) {
//        String stackTrace = getStackTrace(t);
//        if (stackTrace != null && !stackTrace.equals("")) {
//            try {
//                response.setContentType("text/html");
//                PrintStream ps = new PrintStream(response.getOutputStream());
//                PrintWriter pw = new PrintWriter(ps);
//                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N
//                // PENDING! Localize this for next official release
//                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");
//                pw.print(stackTrace);
//                pw.print("</pre></body>\n</html>"); //NOI18N
//                pw.close();
//                ps.close();
//                response.getOutputStream().close();
//            } catch (Exception ex) {
//            }
//        } else {
//            try {
//                PrintStream ps = new PrintStream(response.getOutputStream());
//                t.printStackTrace(ps);
//                ps.close();
//                response.getOutputStream().close();
//            } catch (Exception ex) {
//            }
//        }
//    }
//
//    public static String getStackTrace(Throwable t) {
//        String stackTrace = null;
//        try {
//            StringWriter sw = new StringWriter();
//            PrintWriter pw = new PrintWriter(sw);
//            t.printStackTrace(pw);
//            pw.close();
//            sw.close();
//            stackTrace = sw.getBuffer().toString();
//        } catch (Exception ex) {
//        }
//        return stackTrace;
//    }
//
//    public void log(String msg) {
//        if (filterConfig != null) {
//            filterConfig.getServletContext().log(msg);
//        } else {
//            System.out.println("LOG (no servlet context): " + msg);
//        }
//    }
//}
