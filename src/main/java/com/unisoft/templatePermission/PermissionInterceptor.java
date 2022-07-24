package com.unisoft.templatePermission;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Component
public class PermissionInterceptor extends HandlerInterceptorAdapter {

    private static boolean startCheck = false;

    private HttpSession permissionSession;

    private boolean sesCheck = false;

    private boolean permission = false;

    List<String> permissionList = new ArrayList<>();
//    List<PageInfoEntity> permissionList=new ArrayList<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getSession() == null) return true;
        String sub = getPrincipal();
        String requestURI = request.getRequestURI();

        if (requestURI.startsWith("/css")
                || requestURI.startsWith("/error")
                || requestURI.startsWith("/js")
                || requestURI.contains("css")
                || requestURI.contains("js")
                || requestURI.contains("images")
                || requestURI.contains("img")
                || requestURI.contains("fonts")
                || requestURI.startsWith("/collection/lateReason")
                || requestURI.startsWith("/collection/modata/check")
                || requestURI.startsWith("/user-role/authentication/user/role-names")
                || requestURI.startsWith("/auth/password-reset/by-email")
                || requestURI.startsWith("/auth/password-reset/verification-code")
                || requestURI.startsWith("/auth/password-reset/save-password")
                || requestURI.startsWith("/auth/password-reset/email-verify"))
            return true;

        if (sub.equals("anonymousUser")) {
            if (requestURI.startsWith("/") && requestURI.length() == 1) return true;
            else response.sendRedirect(request.getContextPath() + "/");
        } else {
            permission = false;
            permissionSession = request.getSession(true);

            if (permissionSession.getAttribute("check") != null) sesCheck = (Boolean) permissionSession.getAttribute("check");
            else sesCheck = false;
            if (sesCheck) {
                startCheck = true;
                String reqUrl = request.getRequestURL().toString();
                if (requestURI.equals("/")) return true;
                else if (reqUrl.contains("/error")) return true;
                else if (reqUrl.contains("/loginError")) return true;
                else {
//                    permissionList= (List<String>) permissionSession.getAttribute("urlList");
//                    boolean contains = permissionList.contains(requestURI);
//                    if(contains) return true;
//                    else response.sendRedirect(request.getContextPath()+"/error");
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String requestURI = request.getRequestURI();
        //System.out.println("req url post : "+requestURI);
//        if(requestURI.contains(".js") && requestURI.endsWith(".js")){
//            request.authenticate( "/");
            //System.out.println("in :- ");
            //request.getSession().invalidate();
//        }
        //System.out.println("res _ :  "+response.toString());
    }

    private String getPrincipal() {
        String userName = null;
        Object principal = null;
        
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() != null)
            principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) userName = ((UserDetails) principal).getUsername();
        else userName = principal != null ? principal.toString() : null;

        return userName;
    }
}

