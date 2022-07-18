package com.csinfotechbd.collection.settings.waiverOrReversalFacility;
/*
Created by Monirul Islam on 7/14/2019 
*/

import com.csinfotechbd.user.UserPrincipal;
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
@RequestMapping(value = "/collection/waiver_reversal_facility/")
public class WaiverOrReversalFacController {

    @Autowired
    private WaiverOrReversalFacService waiverOrReversalFacService;

    @GetMapping(value = "list")
    public String viewAll(Model model)
    {
        model.addAttribute("waiverList",waiverOrReversalFacService.getAll());
        return "collection/settings/waiverOrReversalFacility/waiver";
    }

    @GetMapping(value = "create")
    public String addPage(Model model)
    {
        model.addAttribute("waiver",new WaiverOrReversalFacilityEntity());
        return "collection/settings/waiverOrReversalFacility/create";
    }

    @PostMapping(value = "create")
    public String create(WaiverOrReversalFacilityEntity waiverOrReversalFacility)
    {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(waiverOrReversalFacility.getId() == null)
        {
            waiverOrReversalFacility.setCreatedBy(user.getUsername());
            waiverOrReversalFacility.setCreatedDate(new Date());
            boolean save=waiverOrReversalFacService.saveNew(waiverOrReversalFacility);
        }else {
            waiverOrReversalFacility.setModifiedBy(user.getUsername());
            waiverOrReversalFacility.setModifiedDate(new Date());
            boolean update=waiverOrReversalFacService.updateWaiver(waiverOrReversalFacility);
        }
        return "redirect:/collection/waiver_reversal_facility/list";
    }

    @GetMapping(value = "edit")
    public String editPage(@RequestParam(value = "id")Long id,Model model)
    {
        model.addAttribute("waiver",waiverOrReversalFacService.getById(id));
        return "collection/settings/waiverOrReversalFacility/create";
    }

    @GetMapping(value = "view")
    public String viewPage(@RequestParam(value = "id")Long id,Model model)
    {
        model.addAttribute("waiver",waiverOrReversalFacService.getById(id));
        return "collection/settings/waiverOrReversalFacility/view";
    }
}
