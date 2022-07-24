package com.unisoft.collection.settings.atm;
/*
Created by    on 26/11/2020
*/


import com.unisoft.collection.settings.district.DistrictService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/collection/settings/atm/")
public class AtmController {

    private final AtmService atmService;
    private final DistrictService districtService;

    @GetMapping("/list")
    public String getAll(Model model) {
        model.addAttribute("list", atmService.getList());
        return "collection/settings/atm/list";
    }

    @GetMapping("/create")
    public String addPage(Model model) {
        model.addAttribute("entity", new AtmEntity());
        model.addAttribute("disList", districtService.getActiveOnly());
        return "collection/settings/atm/create";
    }

    @PostMapping("/create")
    public String addNew(AtmEntity entity, Model model) {
        String output = atmService.save(entity);
        switch (output) {
            case "1":
                return "redirect:/collection/settings/atm/list";
            default:
                model.addAttribute("error", output);
        }
        model.addAttribute("entity", entity);
        return "collection/settings/atm/create";
    }

    @GetMapping("/edit")
    public String editPage(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("disList", districtService.getActiveOnly());
        model.addAttribute("entity", atmService.getById(id));
        return "collection/settings/atm/create";
    }

    @GetMapping(value = "/view")
    public String viewPage(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("entity", atmService.getById(id));
        return "collection/settings/atm/view";
    }


}
