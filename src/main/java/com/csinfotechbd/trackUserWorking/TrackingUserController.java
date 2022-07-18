package com.csinfotechbd.trackUserWorking;


import com.csinfotechbd.collection.allocationLogic.PeopleAllocationLogicInfo;
import com.csinfotechbd.collection.allocationLogic.PeopleAllocationLogicService;
import com.csinfotechbd.collection.settings.employee.EmployeeInfoEntity;
import com.csinfotechbd.collection.settings.employee.EmployeeService;
import com.csinfotechbd.user.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller("/tracking-user")
@RequestMapping
public class TrackingUserController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private PeopleAllocationLogicService peopleAllocationLogicService;

    @Autowired
    private TrackingUserService trackingUserService;


    @GetMapping("/teamleader/dealer-tracking-list")
    public List<TrackUserWorking> getTrackUserList() {
        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        EmployeeInfoEntity employeeInfoEntity = employeeService.getByPin(principal.getUsername());
        List<PeopleAllocationLogicInfo> peopleAllocationLogicInfoList = peopleAllocationLogicService.findDealerByTeamLeaderId(employeeInfoEntity.getId());
        List<TrackUserWorking> trackUserWorkingList = new ArrayList<>();
        for (PeopleAllocationLogicInfo logicInfo : peopleAllocationLogicInfoList) {
            TrackUserWorking trackUserWorking = trackingUserService.findByDealerPin(logicInfo.getDealer().getPin());
            trackUserWorkingList.add(trackUserWorking);
        }
        return trackUserWorkingList;
    }
}
