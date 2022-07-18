package com.csinfotechbd.collection.settings.sectorGroup;
/*
Created by Monirul Islam at 7/2/2019 
*/


import com.csinfotechbd.audittrail.AuditTrailService;
import com.csinfotechbd.user.UserPrincipal;
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
@RequestMapping(value = "/collection/sector_group/")
public class SectorGroupController {

    @Autowired
    private SectorGroupService sectorGroupService;

    @Autowired
    private AuditTrailService auditTrailService;

    @GetMapping(value = "list")
    public String viewList(Model model)
    {
        model.addAttribute("sectGrpList",sectorGroupService.getAll());
        return "collection/settings/sectorGroup/sector_group";
    }

    @GetMapping(value = "view")
    public String viewPage(@RequestParam(value = "id")Long id,Model model)
    {
        model.addAttribute("sectorGroup",sectorGroupService.getById(id));
        return "collection/settings/sectorGroup/view";
    }

    @GetMapping(value = "create")
    public String addPage(Model model)
    {
        model.addAttribute("sectorGroup",new SectorGroupEntity());
        return "collection/settings/sectorGroup/create";
    }

    @PostMapping(value = "create")
    public String create(SectorGroupEntity sectorGroup)
    {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(sectorGroup.getId() == null)
        {
            sectorGroup.setCreatedBy(user.getUsername());
            sectorGroup.setCreatedDate(new Date());
            boolean save=sectorGroupService.saveNew(sectorGroup);
            auditTrailService.saveCreatedData("Sector Group", sectorGroup);
        }else {
            SectorGroupEntity oldEntity = sectorGroupService.getById(sectorGroup.getId());
            SectorGroupEntity previousEntity = new SectorGroupEntity();
            BeanUtils.copyProperties(oldEntity, previousEntity);

            sectorGroup.setModifiedBy(user.getUsername());
            sectorGroup.setModifiedDate(new Date());
            boolean update=sectorGroupService.updateSectGrp(sectorGroup);
            auditTrailService.saveUpdatedData("Sector Group", previousEntity, sectorGroup);
        }

        return "redirect:/collection/sector_group/list";
    }

    @GetMapping(value = "edit")
    public String editpage(@RequestParam(value = "id")Long id,Model model)
    {
        model.addAttribute("sectorGroup",sectorGroupService.getById(id));
        return "collection/settings/sectorGroup/create";
    }
}
