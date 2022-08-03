package com.unisoft.collection.settings.location;
/*
Created by   Islam at 6/23/2019
*/

import com.unisoft.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/collection/location/")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @GetMapping("list")
    public String viewList(Model model) {
        model.addAttribute("locList", locationService.getLocList());
        return "collection/settings/location/location";
    }

    @GetMapping("create")
    public String createPage(Model model) {
        model.addAttribute("location", new LocationEntity());
        return "collection/settings/location/create";
    }

    @PostMapping(value = "create", consumes = (MediaType.APPLICATION_FORM_URLENCODED_VALUE))
    public String createLoc(LocationEntity location, Model model) {
        String output = locationService.save(location);
        switch (output) {
            case "1":
                return "redirect:/collection/location/list";
            default:
                model.addAttribute("error", output);
        }
        model.addAttribute("location", location);
        return "collection/settings/location/create";
    }

    @GetMapping("edit")
    public String editLoc(@RequestParam(value = "id") Long locId, Model model) {
        LocationEntity loacationEntity = locationService.getById(locId);
        if (loacationEntity == null) throw new NotFoundException(LocationEntity.class);
        model.addAttribute("location", loacationEntity);
        return "collection/settings/location/create";
    }

    @GetMapping("view")
    public String viewLoc(@RequestParam(value = "id") Long locId, Model model) {
        model.addAttribute("location", locationService.getById(locId));
        return "collection/settings/location/view";
    }

}
