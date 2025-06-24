package filter;
import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebFilter("/*") // Áp dụng cho tất cả các request
public class RouterFilter implements Filter {
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Không cần cấu hình đặc biệt
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        String requestURI = httpRequest.getRequestURI();
        
        // Kiểm tra nếu URL kết thúc bằng .jsp
        if (requestURI.endsWith(".jsp")) {
            System.out.println("[RouterFilter] Chặn truy cập: " + requestURI);
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/home");
            return;
        }
        
        // Tiếp tục chuỗi filter nếu không bị chặn
        chain.doFilter(request, response);
    }
    @Override
    public void destroy() {
        // Không có tài nguyên nào cần giải phóng
    }
}
