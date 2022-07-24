package com.unisoft.collection.agency.agencyStatusManager;


import com.unisoft.collection.agency.agencyStatus.AgencyStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/collection/agency/agency-status-manager")
public class AgencyStatusManagerController {


    @Autowired
    private AgencyStatusManagerService agencyStatusManagerService;
    @Autowired
    private AgencyStatusService agencyStatusService;


    @GetMapping("/list")
    public String agencyManagerStatusList(Model model){
        List<AgencyStatusManagerEntity> managerEntityList = agencyStatusManagerService.findAll();
        model.addAttribute("managerEntityList", managerEntityList);
        return "agency/setting/agencyStatusManager/list";
    }


    @GetMapping("/find")
    public String createAgency(Model model, @RequestParam Long id){
        AgencyStatusManagerEntity agencyStatusManagerEntity = agencyStatusManagerService.findById(id);
        model.addAttribute("agencyStatusManagerEntity", agencyStatusManagerEntity);
        model.addAttribute("agencyStatusList", agencyStatusService.getAllStatus());
        return "agency/setting/agencyStatusManager/create";
    }


    @PostMapping("/create")
    public String save(Model model, AgencyStatusManagerEntity agencyStatusManagerEntity, BindingResult result){
        if (!result.hasErrors()){
            agencyStatusManagerService.save(agencyStatusManagerEntity);
            return "redirect:/collection/agency/agency-status-manager/list";
        }
        model.addAttribute("agencyStatusManagerEntity", agencyStatusManagerEntity);
        model.addAttribute("error", true);
        return "agency/setting/agencyStatusManager/create";
    }
}
