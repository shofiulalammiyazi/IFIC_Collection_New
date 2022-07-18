package com.csinfotechbd.collection.settings.sector;
/*
Created by Monirul Islam at 7/2/2019 
*/


import com.csinfotechbd.audittrail.AuditTrailService;
import com.csinfotechbd.collection.settings.sectorGroup.SectorGroupEntity;
import com.csinfotechbd.collection.settings.sectorGroup.SectorGroupService;
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
@RequestMapping(value = "/collection/sector/")
public class SectorController {

    @Autowired
    private SectorService sectorService;

    @Autowired
    private SectorGroupService sectorGroupService;

    @Autowired
    private AuditTrailService auditTrailService;

    @GetMapping(value = "list")
    public String viewList(Model model)
    {
        model.addAttribute("sectList",sectorService.getAll());
        return "collection/settings/sector/sector";
    }

    @PostMapping(value = "create")
    public String addnew(SectorEntity sector, @RequestParam(value = "sectGrpId") Long sectGrpId)
    {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        SectorGroupEntity sectorGroup=new SectorGroupEntity();
        sectorGroup.setId(sectGrpId);

        sector.setSectorGroup(sectorGroup);
        if(sector.getId() == null)
        {
            sector.setCreatedBy(user.getUsername());
            sector.setCreatedDate(new Date());
            boolean save=sectorService.saveNew(sector);
            auditTrailService.saveCreatedData("Sector", sector);
        }else {
            SectorEntity oldEntity = sectorService.getById(sector.getId());
            SectorEntity previousEntity = new SectorEntity();
            BeanUtils.copyProperties(oldEntity, previousEntity);

            sector.setModifiedBy(user.getUsername());
            sector.setModifiedDate(new Date());
            boolean update=sectorService.updateSect(sector);
            auditTrailService.saveUpdatedData("Sector", previousEntity, sector);
        }
        return "redirect:/collection/sector/list";
    }

    @GetMapping(value = "create")
    public String addpage(Model model)
    {
        model.addAttribute("sectGrpList",sectorGroupService.getActiveList());
        model.addAttribute("sector",new SectorEntity());
        return "collection/settings/sector/create";
    }

    @GetMapping(value = "edit")
    public String editPage(@RequestParam(value = "id") Long id,Model model)
    {
        model.addAttribute("sector",sectorService.getById(id));
        model.addAttribute("sectGrpList",sectorGroupService.getActiveList());
        return "collection/settings/sector/create";
    }

    @GetMapping(value = "view")
    public String viewPage(@RequestParam(value = "id") Long id,Model model)
    {
        model.addAttribute("sector",sectorService.getById(id));
        return "collection/settings/sector/view";
    }
}
