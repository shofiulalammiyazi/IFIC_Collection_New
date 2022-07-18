package com.csinfotechbd.utillity;

import com.csinfotechbd.collection.settings.loginPolicy.LoginPolicyService;
import com.csinfotechbd.retail.card.dataEntry.distribution.accountDistributionInfo.CardAccountDistributionInfo;
import com.csinfotechbd.collection.distribution.loan.loanAccountDistribution.LoanAccountDistributionInfo;
import com.csinfotechbd.collection.settings.employee.EmployeeInfoEntity;
import com.csinfotechbd.collection.settings.employee.EmployeeService;
import com.csinfotechbd.collection.settings.lateReasonExplain.LateReasonExplainInfo;
import com.csinfotechbd.role.Role;
import com.csinfotechbd.user.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class HttpSessionUtils {

    private final EmployeeService employeeService;
    private final LoginPolicyService loginPolicyService;

    public void prepareEmployeeSession(EmployeeInfoEntity employee, HttpSession session) {
        //Todo: Add employee related session attributes here
    }

    public void setFollowupNotification(Map<String, String> notification, HttpSession session) {
        if (session.getAttribute("followupNotification") == null)
            session.setAttribute("followUpNotifications", notification);
    }


    public List<Long> getLoanCustomerIds(HttpSession session) {
        List<Long> custIdList = (List<Long>) session.getAttribute("custIdList");
        if (custIdList == null || custIdList.isEmpty()) {
            List<LoanAccountDistributionInfo> loanDist = (List<LoanAccountDistributionInfo>) session.getAttribute("loanDistributionList");
            custIdList = loanDist.parallelStream().map(loan -> loan.getLoanAccountBasicInfo().getCustomer().getId()).collect(Collectors.toList());
            session.setAttribute("custIdList", custIdList);
        }
        return custIdList;
    }

    public List<Long> getCardCustomerIds(HttpSession session) {
        List<Long> custIdList = (List<Long>) session.getAttribute("custIdList");
        if (custIdList == null || custIdList.isEmpty()) {
            List<CardAccountDistributionInfo> cardDist = (List<CardAccountDistributionInfo>) session.getAttribute("cardAccList");
            custIdList = cardDist.parallelStream().map(card -> card.getCardAccountBasicInfo().getCustomer().getId()).collect(Collectors.toList());
            session.setAttribute("custIdList", custIdList);
        }

        return custIdList;
    }

    public String getDesignation(HttpSession session) {
        String designation = (String) session.getAttribute("userDesignation");
        if (designation == null || designation.isEmpty()) {
            UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            EmployeeInfoEntity employee = employeeService.getByPin(principal.getUsername());
            designation = employee.getDesignation().getName();
            session.setAttribute("userDesignation", designation);
        }

        return designation;
    }


    public void setEmployeeSessionAttributes(HttpSession session,
                                             EmployeeInfoEntity employee,
                                             List<LateReasonExplainInfo> lateReasons) {
        if (session.getAttribute("loginTime") == null) {
            List<String> roles = employee.getUser().getRoles().stream().map(Role::getName).collect(Collectors.toList());
            int sessionIdleMinutes = (int) loginPolicyService.getPolicy().getSessionIdle();

            session.setAttribute("unit", employee.getUnit());
            session.setAttribute("userPhoto", employee.getPhoto());
            session.setAttribute("userDesignation", employee.getDesignation().getName());
            session.setAttribute("userRole", roles.toString());
            session.setAttribute("loginTime", new Date());
            session.setAttribute("loginStatus", lateReasons.size() == 0 ? "" : lateReasons.get(0).getLateReasonEntity().getReasonTitle());
            session.setMaxInactiveInterval(sessionIdleMinutes * 60); // Convert minutes into seconds
        }
    }


}
