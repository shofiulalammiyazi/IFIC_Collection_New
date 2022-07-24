package com.unisoft.collection.settings.district;
/*
Created by   Islam at 7/3/2019
*/

import com.unisoft.audittrail.AuditTrailService;
import com.unisoft.collection.settings.division.DivisionService;
import com.unisoft.collection.settings.location.LocationService;
import com.unisoft.collection.settings.zone.ZoneService;
import com.unisoft.user.UserPrincipal;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/collection/district/")
public class DistrictController {

    @Autowired
    private DistrictService districtService;

    @Autowired
    private DivisionService divisionService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private ZoneService zoneService;

    @Autowired
    private AuditTrailService auditTrailService;

    @GetMapping(value = "list")
    public String viewAll(Model model) {
        model.addAttribute("districtList", districtService.getAll());
        return "collection/settings/district/district";
    }

    @ResponseBody
    @GetMapping(value = "listbydivision")
    public List viewAll(@RequestParam Long divisionId) {
        return districtService.getByDivision(divisionId);
    }

    @GetMapping(value = "view")
    public String viewPage(@RequestParam(value = "id") Long Id, Model model) {
        model.addAttribute("district", districtService.getById(Id));
        return "collection/settings/district/view";
    }

    @GetMapping(value = "create")
    public String addPage(Model model) {
        model.addAttribute("district", new DistrictEntity());
//        Changes made for lawyer table
        model.addAttribute("divisionList", divisionService.getActiveList());
        model.addAttribute("zoneList", zoneService.getActiveList());
        model.addAttribute("locationList", locationService.getActiveList());
        return "collection/settings/district/create";
    }

    @PostMapping(value = "create")
    public String create(DistrictEntity district) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (district.getId() == null) {
            district.setCreatedBy(user.getUsername());
            district.setCreatedDate(new Date());
            boolean save = districtService.saveNew(district);
            auditTrailService.saveCreatedData("District", district);
        } else {
            DistrictEntity oldEntity = districtService.getById(district.getId());
            DistrictEntity previousEntity = new DistrictEntity();
            BeanUtils.copyProperties(oldEntity, previousEntity);

            district.setModifiedBy(user.getUsername());
            district.setModifiedDate(new Date());
            boolean update = districtService.updateDis(district);
            auditTrailService.saveUpdatedData("District", previousEntity, district);
        }
        return "redirect:/collection/district/list";
    }

    @GetMapping(value = "edit")
    public String editPage(@RequestParam(value = "id") Long id, Model model) {
        //        Changes made for lawyer table
        model.addAttribute("divisionList", divisionService.getActiveList());
        model.addAttribute("zoneList", zoneService.getActiveList());
        model.addAttribute("locationList", locationService.getActiveList());
        model.addAttribute("district", districtService.getById(id));
        return "collection/settings/district/create";
    }


}
