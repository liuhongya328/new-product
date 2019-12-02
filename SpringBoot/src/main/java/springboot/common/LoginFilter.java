//package springboot.common;
//
//import java.io.IOException;
//
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.FilterConfig;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
///**
// * 
// * @author QiuSheng Lv
// *
// * @date 2019年7月2日
// */
//@WebFilter(filterName = "LoginFilter",urlPatterns = {"/*"})
//public class LoginFilter implements Filter{
//
//	@Override
//	public void init(FilterConfig filterConfig) throws ServletException {
//		
//	}
//
//	@Override
//	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//		HttpServletRequest req = (HttpServletRequest) request;
//		HttpServletResponse res = (HttpServletResponse) response;
//		HttpSession session = req.getSession();
//		String spath = req.getServletPath();
//		 //不需要过滤的url
//        String[] urls = {"/login","/register","/json","/findProductInfoById","/xiaochengxu",".js",".css",".ico",".jpg",".png"};
//        boolean flag = true;
//        for (String str : urls) {
//            if (spath.indexOf(str) != -1) {
//                flag =false;
//                break;
//            }
//        }
//        if (spath.indexOf(".jsp") != -1) {
//            flag = true;
//        }
//        if(flag) {
//        	if(session.getAttribute("user")== null 
//    				&& req.getRequestURI().indexOf("login.jsp") == -1 
//    				&& req.getRequestURI().indexOf("login.do") == -1  
//    				&& req.getRequestURI().indexOf("returnLicenseInformation.do") == -1){
//    			
//    			res.sendRedirect(req.getContextPath()+"/login.jsp");
//    			
//    			return;
//    		}else {
//    			chain.doFilter(request, response);
//    		}
//        	
//        }else {
//        	chain.doFilter(request, response);
//        }
//		
//		
//	}
//
//	@Override
//	public void destroy() {
//		
//	}
//
//}
