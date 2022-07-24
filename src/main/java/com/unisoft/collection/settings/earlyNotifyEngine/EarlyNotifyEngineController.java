package com.unisoft.collection.settings.earlyNotifyEngine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/collection/card/earlyNotificationEngine")
public class EarlyNotifyEngineController {

    @Autowired
    private EarlyNotifyEngineService earlyNotifyEngineService;

    @GetMapping(value = "/list")
    public String viewAll(Model model){
        model.addAttribute("earlyNotifyList",earlyNotifyEngineService.getList());
        return "collection/settings/earlyNotifyEngine/list";
    }

    @GetMapping(value = "/create")
    public String viewPage(Model model){
        model.addAttribute("earlyNotify",new EarlyNotifyEngine());
        return "collection/settings/earlyNotifyEngine/create";
    }

    @PostMapping(value = "create")
    public String create(EarlyNotifyEngine earlyNotifyEngine, Model model) {
        String output = earlyNotifyEngineService.save(earlyNotifyEngine);
        switch (output) {
            case "1":
                return "redirect:/collection/card/earlyNotificationEngine/list";
            default:
                model.addAttribute("error", output);
        }
        model.addAttribute("earlyNotify", earlyNotifyEngine);
        return "collection/settings/earlyNotifyEngine/create";
    }

    @GetMapping(value = "edit")
    public String editPage(@RequestParam(value = "id")Long id, Model model)
    {
        model.addAttribute("earlyNotify",earlyNotifyEngineService.getById(id));
        return "collection/settings/earlyNotifyEngine/create";
    }

    @GetMapping(value = "view")
    public String viewPage(@RequestParam(value = "id")Long id,Model model)
    {
        model.addAttribute("earlyNotify",earlyNotifyEngineService.getById(id));
        return "collection/settings/earlyNotifyEngine/view";
    }
}
