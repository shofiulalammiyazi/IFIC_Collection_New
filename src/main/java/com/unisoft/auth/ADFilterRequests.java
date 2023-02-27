package com.unisoft.auth;

import com.unisoft.collection.settings.employee.EmployeeInfoEntity;
import com.unisoft.collection.settings.employee.EmployeeService;
import com.unisoft.user.User;
import com.unisoft.user.UserDao;
import com.unisoft.user.UserPrincipal;
import com.unisoft.utillity.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@Component
public class ADFilterRequests { //implements Filter


    @Autowired
    private UserDao userDao;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private HttpSessionUtils httpSessionUtils;

//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//
//    }
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
//        HttpServletRequest httpRequest = (HttpServletRequest) request;
//        HttpServletResponse httpResponse = (HttpServletResponse) response;
//
//        /* check the resource or common uri is requested */
//        if (!this.isExcluded(httpRequest.getRequestURI())) {
//
//            /* check that user is logged in or not */
//            if (!"anonymousUser".equals(SecurityContextHolder.getContext().getAuthentication().getPrincipal())) {
//                UserPrincipal principal = httpSessionUtils.getUserPrinciple();//(UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//                User user = userDao.findUserByUsername(principal.getUsername());
//
//                /* if the password is common, then force to reset password. */
//                if (new BCryptPasswordEncoder().matches("A123", user.getPassword())) {
//                    EmployeeInfoEntity employee = employeeService.getByPin(user.getUsername());
//                    if (employee.getUnit() != null && employee.getUnit().contains("Loan")) {
//                        httpResponse.sendRedirect("/user/changePassword");
//                    } else if (employee.getUnit() != null && employee.getUnit().contains("Legal")) {
//                        if ("/collection/legal/dashboard/home".equals(httpRequest.getRequestURI()))
//                            httpResponse.sendRedirect("/user/changePassword");
//                    } else
//                        httpResponse.sendRedirect("/user/changePassword");
//                }
//            }
//        }
//        chain.doFilter(httpRequest, httpResponse);
//    }
//
//    @Override
//    public void destroy() {
//
//    }

    /* check requested uri is resource or common for all
     * should be excluded from filtering */
    private boolean isExcluded(String uri) {
        return uri.matches(".*.css|.*.js|.*.png|.*.jpg|.*.ico|.*.woff2" +
                "|/user/changePassword|/collection/lateReason/latereason" +
                "|/bower_components.*");
    }

}
