package com.unisoft.collection.settings.lateReasonExplain;

import com.unisoft.collection.allocationLogic.PeopleAllocationLogicInfo;
import com.unisoft.collection.allocationLogic.PeopleAllocationLogicService;
import com.unisoft.collection.settings.employee.EmployeeInfoEntity;
import com.unisoft.collection.settings.employee.EmployeeService;
import com.unisoft.user.UserPrincipal;
import com.unisoft.utillity.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
Created by   Islam at 7/22/2019
*/
@Controller
@RequestMapping(value = "/collection/latereasonexplain/")
public class LateReasonExplainController {

    @Autowired
    private LateReasonExplainService lateReasonExplainService;

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private PeopleAllocationLogicService peopleAllocationLogicService;
  @Autowired
  private  DateUtils dateUtils;
    @GetMapping("list")
    public String getLateReasonList(Model model){
        List<LateReasonExplainInfo> all = lateReasonExplainService.getAll();
        model.addAttribute("lateReasonlist", all);
        return "/collection/settings/lateReasonExplain/latereasonexplain";
    }



    @GetMapping("/find/by-teamleader")
    @ResponseBody
    public List<LateReasonExplainInfo> getLoginLogoutDealerListByTeamLeaderPin(){

        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        EmployeeInfoEntity employeeInfoEntity = employeeService.getByPin(user.getUsername());
        List<PeopleAllocationLogicInfo> peopleAllocationLogicInfoList = peopleAllocationLogicService.findDealerByTeamLeaderId(employeeInfoEntity.getId());

        List<LateReasonExplainInfo>  explainInfoList = new ArrayList<>();

        for (PeopleAllocationLogicInfo logicInfo: peopleAllocationLogicInfoList){
            List<LateReasonExplainInfo> explainInfos = lateReasonExplainService.findByUserId(logicInfo.getDealer().getUser().getUserId());
            explainInfoList.addAll(explainInfos);
        }

        return explainInfoList;
    }


    @GetMapping("/login-logout-current-month")
    @ResponseBody
    public List<LateReasonExplainInfo> getLoginLogOutHistoryCurrentMonth(){
        Date startDate = dateUtils.getMonthStartDate();
        Date endDate = dateUtils.getMonthEndDate();
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        EmployeeInfoEntity employeeInfoEntity = employeeService.getByPin(user.getUsername());
        List<LateReasonExplainInfo> explainInfos = lateReasonExplainService.findByUserIdInCurrentMonth(startDate,endDate,employeeInfoEntity.getUser());
        return explainInfos;
    }
}
