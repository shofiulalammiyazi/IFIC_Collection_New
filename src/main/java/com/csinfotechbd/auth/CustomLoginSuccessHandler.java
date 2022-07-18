package com.csinfotechbd.auth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.csinfotechbd.collection.settings.employee.EmployeeInfoEntity;
import com.csinfotechbd.collection.settings.employee.EmployeeService;
import com.csinfotechbd.collection.settings.lateReasonExplain.LateReasonExplainInfo;
import com.csinfotechbd.collection.settings.lateReasonExplain.LateReasonExplainRepository;
import com.csinfotechbd.templatePermission.InterceptorConfig;
import com.csinfotechbd.user.User;
import com.csinfotechbd.user.UserDao;
import com.csinfotechbd.user.UserPrincipal;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import com.csinfotechbd.utillity.DateUtils;
import com.csinfotechbd.utillity.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;
import com.csinfotechbd.utillity.StringUtils;


@Component
public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

//    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private RequestCache requestCache = new HttpSessionRequestCache();
    @Autowired
    private InterceptorConfig interceptorConfig;
    @Autowired
    private UserDao userDao;
    @Autowired
    private HttpSessionUtils sessionUtils;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private DateUtils dateUtils;
    @Autowired
    private LateReasonExplainRepository lateReasonExplainRepository;

    @Override
    public void
    onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                            Authentication authentication) throws IOException, ServletException {
        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDao.findUserByUsername(principal.getUsername());
        user.setLoggedIn(true);
        userDao.update(user);
        interceptorConfig.BeginPermissionFilter(request);
        String module = ((CustomWebAuthenticationDetails) authentication.getDetails()).getModudleName();
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        request.getSession().setAttribute("module", module);
        setSessionData(request, user, principal);


        if (savedRequest == null) {
            super.onAuthenticationSuccess(request, response, authentication);
            return;
        }

        String targetUrlParameter = getTargetUrlParameter();
        if (isAlwaysUseDefaultTargetUrl()
                || (targetUrlParameter != null && StringUtils.hasText(request
                .getParameter(targetUrlParameter)))) {
            requestCache.removeRequest(request, response);
            super.onAuthenticationSuccess(request, response, authentication);
            return;
        }

        clearAuthenticationAttributes(request);
        String targetUrl = savedRequest.getRedirectUrl();
//        logger.debug("Redirecting to DefaultSavedRequest Url: " + targetUrl);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    private void setSessionData(HttpServletRequest request, User user, UserPrincipal principal) {
        Date startDate = dateUtils.getMonthStartDate();
        Date endDate = dateUtils.getMonthEndDate();
        if (!user.getIsAgency() && principal != null) {
            EmployeeInfoEntity employeeInfoEntity = employeeService.getByPin(principal.getEmpId());
            List<LateReasonExplainInfo> lateReasonExplainInfos = lateReasonExplainRepository.findByCreatedDateIsBetweenAndUserOrderByCreatedDateDesc(startDate, endDate, employeeInfoEntity.getUser());
            sessionUtils.setEmployeeSessionAttributes(request.getSession(), employeeInfoEntity, lateReasonExplainInfos);
        }
    }
}
