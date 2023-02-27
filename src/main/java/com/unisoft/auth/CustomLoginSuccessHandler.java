package com.unisoft.auth;

import com.unisoft.collection.settings.employee.EmployeeInfoEntity;
import com.unisoft.collection.settings.employee.EmployeeService;
import com.unisoft.collection.settings.lateReasonExplain.LateReasonExplainInfo;
import com.unisoft.collection.settings.lateReasonExplain.LateReasonExplainRepository;
import com.unisoft.templatePermission.InterceptorConfig;
import com.unisoft.user.User;
import com.unisoft.user.UserDao;
import com.unisoft.user.UserPrincipal;
import com.unisoft.utillity.DateUtils;
import com.unisoft.utillity.HttpSessionUtils;
import com.unisoft.utillity.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;


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
        //UserPrincipal principal = new UserPrincipal();
        //UserPrincipal userPrincipal = new UserPrincipal();
        //principal.setUsername(authentication.getName());

        //(UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDao.findUserByUsername(principal.getUsername());
        //principal.setFirstName(user.getFirstName());
        //principal.setLastName(user.getLastName());

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
            //principal.setEmpId(String.valueOf(employeeInfoEntity.getId()));
            List<LateReasonExplainInfo> lateReasonExplainInfos = lateReasonExplainRepository.findByCreatedDateIsBetweenAndUserOrderByCreatedDateDesc(startDate, endDate, employeeInfoEntity.getUser());
            sessionUtils.setEmployeeSessionAttributes(request.getSession(), employeeInfoEntity, lateReasonExplainInfos);
        }
    }
}
