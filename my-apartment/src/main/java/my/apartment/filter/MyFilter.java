package my.apartment.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class MyFilter implements Filter {
    
    private String[] ignorePages = {
        "/login_process.html",
        "/login_process2.html",
        "/login_process3.html"
    };

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //throw new UnsupportedOperationException("Not supported yet."); 
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //throw new UnsupportedOperationException("Not supported yet."); 
        //To change body of generated methods, choose Tools | Templates.
        
        HttpServletRequest httpServletRequest = ((HttpServletRequest) request);
        
        HttpSession httpSession = httpServletRequest.getSession();
        
        String contextPath = httpServletRequest.getContextPath();
        
        Boolean isIgnore = Boolean.FALSE;
        Boolean isLogin = Boolean.FALSE;
        
        if(httpSession.getAttribute("userFirstname") != null) {
            isLogin = Boolean.TRUE;
        }
        
        String uriPage = httpServletRequest.getRequestURI();
        
        for(String ignorePage : ignorePages) {
            if(uriPage.equals(contextPath + ignorePage)) {
                isIgnore = Boolean.TRUE;
            }
        }
        
        if(isLogin || isIgnore) {
            /** ignore or already login */
            
            chain.doFilter(request, response);
        }
        else {
            /** not ignore and not login */
            
            String notLoginRedirectUri = httpServletRequest.getScheme()
                    + "://" + httpServletRequest.getServerName()
                    + ":" + httpServletRequest.getServerPort()
                    + contextPath;
            
            ((HttpServletResponse) response).sendRedirect(notLoginRedirectUri);
        }
    }

    @Override
    public void destroy() {
        //throw new UnsupportedOperationException("Not supported yet."); 
        //To change body of generated methods, choose Tools | Templates.
    }
    
    public static boolean isAjaxRequest(HttpServletRequest request) {
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }
    
}