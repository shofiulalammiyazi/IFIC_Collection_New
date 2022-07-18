package com.csinfotechbd.auth;

import com.csinfotechbd.collection.settings.employee.EmployeeInfoEntity;
import com.csinfotechbd.collection.settings.employee.EmployeeService;
import com.csinfotechbd.trackUserWorking.TrackingUserService;
import com.csinfotechbd.user.User;
import com.csinfotechbd.user.UserDao;
import com.csinfotechbd.user.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
 * Implemented By :~ Hasibul Islam | 22-Mar-2021 12:35
 * To Check Permissions & Security for requested url.
 * */
@Component
public class FilterRequests implements Filter {

    @Autowired
    private UserDao userDao;

    @Autowired
    private EmployeeService employeeService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        /* check the resource or common uri is requested */
        if (!this.isExcluded(httpRequest.getRequestURI())) {

            /* check that user is logged in or not */
            if (!"anonymousUser".equals(SecurityContextHolder.getContext().getAuthentication().getPrincipal())) {
                UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                User user = userDao.findUserByUsername(principal.getUsername());

                /* if the password is common, then force to reset password. */
                if (new BCryptPasswordEncoder().matches("A123", user.getPassword())) {
                    EmployeeInfoEntity employee = employeeService.getByPin(user.getUsername());
                    if (employee.getUnit() != null && employee.getUnit().contains("Loan")) {
                        httpResponse.sendRedirect("/user/changePassword");
                    } else if (employee.getUnit() != null && employee.getUnit().contains("Legal")) {
                        if ("/collection/legal/dashboard/home".equals(httpRequest.getRequestURI()))
                            httpResponse.sendRedirect("/user/changePassword");
                    } else
                        httpResponse.sendRedirect("/user/changePassword");
                }
            }
        }
        chain.doFilter(httpRequest, httpResponse);
    }

    @Override
    public void destroy() {

    }

    /* check requested uri is resource or common for all
     * should be excluded from filtering */
    private boolean isExcluded(String uri) {
        return uri.matches(".*.css|.*.js|.*.png|.*.jpg|.*.ico|.*.woff2" +
                "|/user/changePassword|/collection/lateReason/latereason" +
                "|/bower_components.*");
    }
}
