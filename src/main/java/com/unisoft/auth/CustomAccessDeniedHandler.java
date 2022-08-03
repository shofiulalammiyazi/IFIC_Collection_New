package com.unisoft.auth;

import com.unisoft.user.UserPrincipal;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;


@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

//    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            UserPrincipal user = (UserPrincipal) auth.getPrincipal();
//            log.warn("User : " + user.getUsername() + " attempted to access the protected URL:" + request.getRequestURI());
        }
//        response.sendRedirect(request.getContextPath() + "/user/access-denied");
        response.sendRedirect(request.getContextPath() + "/");
    }
}
