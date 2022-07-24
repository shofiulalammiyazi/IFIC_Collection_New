package com.unisoft.collection.settings.division;
/*
Created by   Islam at 6/23/2019
*/


import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequiredArgsConstructor
@RequestMapping("/collection/division")
public class DivisionController {

    private final DivisionService divisionService;

    @GetMapping("/list")
    public String getAllDiv(Model model) {
        model.addAttribute("divList", divisionService.getDivList());
        return "collection/settings/division/division";
    }

    @GetMapping("/create")
    public String addDivPage(Model model) {
        model.addAttribute("division", new DivisionEntity());
        return "collection/settings/division/create";
    }

    @PostMapping(value = "/create", consumes = (MediaType.APPLICATION_FORM_URLENCODED_VALUE))
    public String addNewDiv(Model model, DivisionEntity division) {
            String output = divisionService.save(division);
            switch (output) {
                case "1":
                    return "redirect:/collection/division/list";
                default:
                    model.addAttribute("error", output);
                    model.addAttribute("division", division);
                    return "collection/settings/division/create";
            }
    }

    @GetMapping("/edit")
    public String editPage(@RequestParam(value = "id") Long divid, Model model) {
        model.addAttribute("division", divisionService.getById(divid));
        return "collection/settings/division/create";
    }

    @GetMapping(value = "/view")
    public String viewPage(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("division", divisionService.getById(id));
        return "collection/settings/division/view";
    }


}
