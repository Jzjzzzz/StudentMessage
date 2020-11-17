package web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 登录验证的过滤器
 */
@WebFilter("/*")
public class LoginFilter implements Filter {


    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        //强制转换
        HttpServletRequest request=(HttpServletRequest) req;
        //获取资源请求路径
        String uri = request.getRequestURI();
        //判断是否包含登录相关资源路径
        if(uri.contains("/login.jsp") || uri.contains("/loginServlet") || uri.contains("/css/") ||uri.contains("/js/") ||uri.contains("/fonts/") || uri.contains("/checkCode")){
            //包含.用户就是想登录.放行
            chain.doFilter(req, resp);
        }else {
            Object user = request.getSession().getAttribute("user");
            if(user!=null){
                chain.doFilter(req, resp);
            }else {
                request.setAttribute("login_msg","您尚未登录，请登录");
                request.getRequestDispatcher("/login.jsp").forward(request,resp);
            }
        }

    }

    public void init(FilterConfig config) throws ServletException {

    }
    public void destroy() {
    }

}
