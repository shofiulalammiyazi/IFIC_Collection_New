package com.unisoft.schedulermonitoringstatus;

import com.unisoft.audittrail.AuditTrail;
import com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformationRepository.AccountInformationDao;
import com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformationRepository.AccountInformationRepository;
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

    @Autowired
    private AccountInformationDao accountInformationDao;

    @Autowired
    private AccountInformationRepository accountInformationRepository;

    @GetMapping(value = "/list")
    public String viewAll(Model model) {

        model.addAttribute("list",schedulerMonitoringStatusService.findAll());
        model.addAttribute("apiSize",accountInformationDao.getData().size());
        model.addAttribute("dbSize",accountInformationRepository.countNotClosedAccount());

        return "collection/schedulermonitoringstatus/schedulermonitoringstatus";
    }

}
