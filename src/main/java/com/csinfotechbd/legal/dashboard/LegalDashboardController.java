package com.csinfotechbd.legal.dashboard;

import com.csinfotechbd.collection.settings.employee.EmployeeInfoEntity;
import com.csinfotechbd.collection.settings.employee.EmployeeRepository;
import com.csinfotechbd.collection.settings.lateReasonExplain.LateReasonExplainInfo;
import com.csinfotechbd.collection.settings.lateReasonExplain.LateReasonExplainRepository;
import com.csinfotechbd.legal.setup.caseFiledType.CaseFiledTypeService;
import com.csinfotechbd.legal.setup.caseType.CaseTypeService;
import com.csinfotechbd.legal.setup.courseOfAction.CourseOfActionService;
import com.csinfotechbd.user.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/collection/legal/dashboard/")
public class LegalDashboardController {

    private final CaseFiledTypeService caseFiledTypeService;
    private final CaseTypeService caseTypeService;
    private final CourseOfActionService courseOfActionService;
    private final EmployeeRepository employeeRepository;
    private final LateReasonExplainRepository lateReasonRepository;

    @GetMapping("/home")
    public String getLegalDashboard(Model model, HttpSession session){

        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        LocalDate today = LocalDate.now();

        LocalDate startDateOfMonth = today.withDayOfMonth(1);
        Date startDate = Date.from(startDateOfMonth.atStartOfDay(ZoneId.systemDefault()).toInstant());

        LocalDate endDateOfMonth = today.withDayOfMonth(today.lengthOfMonth());
        String endDateOfMonthString = endDateOfMonth.toString();
        endDateOfMonthString = endDateOfMonthString + " 11:59 PM";

        Date endDate = null;
        try {
            endDate = new SimpleDateFormat("yyyy-MM-dd hh:mm a").parse(endDateOfMonthString);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        
        EmployeeInfoEntity employeeInfoEntity = employeeRepository.findByPin(userPrincipal.getEmpId());
        List<LateReasonExplainInfo> lateReasonExplainInfos = lateReasonRepository.findByCreatedDateIsBetweenAndUserOrderByCreatedDateDesc(startDate, endDate, employeeInfoEntity.getUser());
        
        model.addAttribute("employee",employeeInfoEntity);
        model.addAttribute("caseFiledTypeList",caseFiledTypeService.findAll());
        model.addAttribute("caseTypeList",caseTypeService.findAllCaseType());
        model.addAttribute("courseOfActionList",courseOfActionService.findAll());
        setEmployeeSessionAttributes(session, employeeInfoEntity, lateReasonExplainInfos);
        return "legal/dashboard/main";
    }


    private void setEmployeeSessionAttributes(HttpSession session,
                                              EmployeeInfoEntity employee,
                                              List<LateReasonExplainInfo> lateReasons) {
        if (session.getAttribute("loginTime") == null) {
            session.setAttribute("userPhoto", employee.getPhoto());
            session.setAttribute("userDesignation", employee.getDesignation().getName());
            session.setAttribute("loginTime", new Date());
            session.setAttribute("loginStatus", lateReasons.get(0).getLateReasonEntity().getReasonTitle());
        }
    }
}
