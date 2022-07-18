package com.csinfotechbd.collection.settings.manualLetterSetup;


import com.csinfotechbd.audittrail.AuditTrailService;
import com.csinfotechbd.collection.settings.designation.DesignationEntity;
import com.csinfotechbd.collection.settings.designation.DesignationService;
import com.csinfotechbd.user.UserPrincipal;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/collection/settings/manuallettersetup/")
public class ManualLetterSetupController {

    private ManualLetterSetupService manualLetterSetupService;
    private DesignationService designationService;
    private AuditTrailService auditTrailService;

    @GetMapping("list")
    private String viewManualLetterSetupList(Model model){
        List<ManualLetterSetupInfo> manualLetterSetupList = manualLetterSetupService.getManualLetterSetupList();
        model.addAttribute("mlettersetuplist", manualLetterSetupList);
        return "collection/settings/manuallettersetup/manuallettersetup";
    }

    @GetMapping("create")
    public String addNewPage(Model model)
    {
        Gson gson = new Gson();
        model.addAttribute("designationList", gson.toJson(designationService.getAll()));
        model.addAttribute("mlsetup",new ManualLetterSetupInfo());
        return "collection/settings/manuallettersetup/create";
    }

    @PostMapping(value = "create")
    public String addNewManualLetterSetup( ManualLetterSetupInfo manualLetterSetupInfo)
    {
        //System.err.println("LOC :"+location);


        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(manualLetterSetupInfo.getId() == null)
        {
            List<DesignationEntity> designationEntities = new ArrayList<>();

            for(String s: manualLetterSetupInfo.getDesignationIds()){
                DesignationEntity designationEntitiy = designationService.getById(new Long(s));
                designationEntities.add(designationEntitiy);
            }

            manualLetterSetupInfo.setDesignation(designationEntities);
            manualLetterSetupInfo.setCreatedBy(user.getUsername());
            manualLetterSetupInfo.setCreatedDate(new Date());
            boolean save=manualLetterSetupService.saveManualLetterSetup(manualLetterSetupInfo);
            auditTrailService.saveCreatedData("Manual letter setup", manualLetterSetupInfo);
        }else {
            List<DesignationEntity> designationEntities = new ArrayList<>();
            ManualLetterSetupInfo oldData = manualLetterSetupService.getById(manualLetterSetupInfo.getId());
            ManualLetterSetupInfo previousData = new ManualLetterSetupInfo();
            BeanUtils.copyProperties(oldData, previousData);


            for(String s: manualLetterSetupInfo.getDesignationIds()){
                DesignationEntity designationEntitiy = designationService.getById(new Long(s));
                designationEntities.add(designationEntitiy);
            }

            manualLetterSetupInfo.setDesignation(designationEntities);
            manualLetterSetupInfo.setModifiedBy(user.getUsername());
            manualLetterSetupInfo.setModifiedDate(new Date());
            boolean update=manualLetterSetupService.updateManualLetterSetup(manualLetterSetupInfo);
            auditTrailService.saveUpdatedData("Manual letter setup", previousData,manualLetterSetupInfo);
        }
        return "redirect:/collection/settings/manuallettersetup/list";
    }

    @GetMapping("edit")
    public String editLoc(@RequestParam(value = "id") Long mlsId, Model model)
    {
        ManualLetterSetupInfo manualLetterSetupInfo = manualLetterSetupService.getById(mlsId);
        Gson gson = new Gson();

        model.addAttribute("designationList", gson.toJson(designationService.getAll()));
        model.addAttribute("selectedDesignationList", gson.toJson(manualLetterSetupInfo.getDesignation()));

        model.addAttribute("mlsetup", manualLetterSetupInfo);
        return "collection/settings/manuallettersetup/create";
    }

    @GetMapping("view")
    public String viewLoc(@RequestParam(value = "id") Long mlsId,Model model)
    {
        model.addAttribute("mlsetup",manualLetterSetupService.getById(mlsId));
        return "collection/settings/manuallettersetup/view";
    }
}
