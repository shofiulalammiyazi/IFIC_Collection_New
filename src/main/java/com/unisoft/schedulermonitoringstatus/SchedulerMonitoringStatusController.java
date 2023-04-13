package com.unisoft.schedulermonitoringstatus;

import com.unisoft.audittrail.AuditTrail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = "/scheduler/monitoring")
public class SchedulerMonitoringStatusController {

    @Autowired
    private SchedulerMonitoringStatusService schedulerMonitoringStatusService;

    @GetMapping(value = "/list")
    public String viewAll(Model model) {

        model.addAttribute("list",schedulerMonitoringStatusService.findAll());

        return "collection/schedulermonitoringstatus/schedulermonitoringstatus";
    }

}
