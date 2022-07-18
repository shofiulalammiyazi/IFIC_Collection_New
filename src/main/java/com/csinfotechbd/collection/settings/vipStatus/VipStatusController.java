package com.csinfotechbd.collection.settings.vipStatus;
/*
Created by Yasir Araphat on 01/02/2021
*/

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/collection/settings/vip-status/")
public class VipStatusController {

    private final VipStatusService vipStatusService;

    @GetMapping("list")
    public String getAll(Model model) {
        model.addAttribute("list", vipStatusService.getList());
        return "collection/settings/vipStatus/list";
    }

    @GetMapping("json/list")
    @ResponseBody
    public List<VipStatus> getJsonList() {
        return vipStatusService.getActiveList();
    }

    @GetMapping("create")
    public String addPage(Model model) {
        model.addAttribute("entity", new VipStatus());
        return "collection/settings/vipStatus/create";
    }

    @PostMapping("create")
    public String addNew(@Valid @ModelAttribute("entity") VipStatus vipStatus, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            String output = vipStatusService.save(vipStatus);
            switch (output) {
                case "1":
                    return "redirect:/collection/settings/vip-status/list";
                default:
                    model.addAttribute("error", output);
            }
        }
        model.addAttribute("entity", vipStatus);
        return "collection/settings/vipStatus/create";
    }

    @GetMapping("edit")
    public String editPage(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("entity", vipStatusService.getById(id));
        return "collection/settings/vipStatus/create";
    }

    @GetMapping(value = "view")
    public String viewPage(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("entity", vipStatusService.getById(id));
        return "collection/settings/vipStatus/view";
    }


}
