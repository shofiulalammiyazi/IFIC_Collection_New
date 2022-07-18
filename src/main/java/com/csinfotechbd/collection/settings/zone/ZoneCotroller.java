package com.csinfotechbd.collection.settings.zone;
/*
Created by Monirul Islam at 7/3/2019 
*/

import com.csinfotechbd.audittrail.AuditTrailService;
import com.csinfotechbd.collection.settings.location.LocationService;
import com.csinfotechbd.user.UserPrincipal;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/collection/zone/")
public class ZoneCotroller {

    @Autowired
    private ZoneService zoneService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private AuditTrailService auditTrailService;

    @GetMapping(value = "list")
    public String viewAll(Model model)
    {
        model.addAttribute("zoneList",zoneService.getAll());
        return "collection/settings/zone/zone";
    }

    @GetMapping(value = "view")
    public String viewPage(Model model, @RequestParam(value = "id")Long Id)
    {
        model.addAttribute("zone",zoneService.getById(Id));
        return "collection/settings/zone/view";
    }

    @GetMapping(value = "create")
    public String addPage(Model model)
    {
        model.addAttribute("zone",new ZoneEntity());
        model.addAttribute("locList",locationService.getActiveList());
        return "collection/settings/zone/create";
    }

    @PostMapping(value = "create")
    public String create(ZoneEntity zone)
    {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        System.err.println("Zone "+zone);
        if(zone.getId() == null)
        {
            zone.setCreatedBy(user.getUsername());
            zone.setCreatedDate(new Date());
            boolean save=zoneService.saveNew(zone);
            auditTrailService.saveCreatedData("Zone", zone);
        }else{
            ZoneEntity oldEntity = zoneService.getById(zone.getId());
            ZoneEntity previousEntity = new ZoneEntity();
            BeanUtils.copyProperties(oldEntity, previousEntity);

            zone.setModifiedBy(user.getUsername());
            zone.setModifiedDate(new Date());
            boolean update=zoneService.updateZone(zone);
            auditTrailService.saveUpdatedData("Zone", previousEntity, zone);
        }
        return "redirect:/collection/zone/list";
    }

    @GetMapping(value = "edit")
    public String editPage(@RequestParam(value = "id")Long Id,Model model)
    {
        model.addAttribute("locList",locationService.getActiveList());
        model.addAttribute("zone",zoneService.getById(Id));
        return "collection/settings/zone/create";
    }


    @GetMapping(value = "/zonelistbyid")
    public @ResponseBody List<ZoneEntity> getZonByLoc(@RequestParam(value = "locId")Long locId) {
        return zoneService.getZoneByLoc(locId);
    }

}
