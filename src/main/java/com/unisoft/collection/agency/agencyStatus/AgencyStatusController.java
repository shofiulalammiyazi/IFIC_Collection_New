package com.unisoft.collection.agency.agencyStatus;


import com.unisoft.audittrail.AuditTrailService;
import com.unisoft.user.UserPrincipal;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller
@RequestMapping("/collection/agency/agencyStatus/")
public class AgencyStatusController {


    @Autowired
    private AgencyStatusService agencyStatusService;

    @Autowired
    private AuditTrailService auditTrailService;


    @GetMapping(value = "list")
    public String viewAll(Model model)
    {
        model.addAttribute("statList", agencyStatusService.getAllStatus());
        return "agency/setting/agencyStatus/agencyStatus";
    }

    @GetMapping(value = "create")
    public String addPage(Model model)
    {
        model.addAttribute("status",new AgencyStatusEntity());
        return "agency/setting/agencyStatus/create";
    }

    @PostMapping(value = "create")
    public String createStatus(AgencyStatusEntity currentStatus)
    {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(currentStatus.getId() == null)
        {
            currentStatus.setCreatedBy(user.getUsername());
            currentStatus.setCreatedDate(new Date());
            boolean save= agencyStatusService.saveStat(currentStatus);
            auditTrailService.saveCreatedData("Agency Status", currentStatus);
        }else {
            AgencyStatusEntity oldStatus = agencyStatusService.getById(currentStatus.getId());
            AgencyStatusEntity previousEntity = new AgencyStatusEntity();
            BeanUtils.copyProperties(oldStatus, previousEntity);

            currentStatus.setModifiedBy(user.getUsername());
            currentStatus.setModifiedDate(new Date());
            boolean update= agencyStatusService.updateStat(currentStatus);
            auditTrailService.saveUpdatedData("Agency Status", previousEntity, currentStatus);
        }

        return "redirect:/collection/agency/agencyStatus/list";
    }

    @GetMapping(value = "view")
    public String viewStatus(@RequestParam(value = "id")Long Id, Model model)
    {
        model.addAttribute("status", agencyStatusService.getById(Id));
        return "agency/setting/agencyStatus/view";
    }

    @GetMapping(value = "edit")
    public String editPage(@RequestParam(value = "id")Long Id,Model model)
    {
        model.addAttribute("status",agencyStatusService.getById(Id));
        return "agency/setting/agencyStatus/create";
    }
}
