package com.unisoft.collection.settings.clstatus;

import com.unisoft.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/collection/clStatus")
public class CLStatusController {

    @Autowired
    private CLStatusService clStatusService;

    @GetMapping(value = "/list")
        public String viewList(Model model){
        model.addAttribute("clStatusList",clStatusService.getClStatusList());
        return "collection/settings/clStatus/list";
    }

    @GetMapping(value = "/create")
    public String create(Model model){
        model.addAttribute("clStatus", new CLStatus());
        return "collection/settings/clStatus/create";
    }

    @PostMapping(value = "/create", consumes = (MediaType.APPLICATION_FORM_URLENCODED_VALUE))
    public String createPage(CLStatus clStatus,Model model){
        String output = clStatusService.save(clStatus);
        switch (output) {
            case "1":
                return "redirect:/collection/clStatus/list";
            default:
                model.addAttribute("error", output);
        }
        model.addAttribute("clStatus",clStatus);
        return "collection/settings/clStatus/create";
    }

    @GetMapping(value = "/edit")
    public String edit(@RequestParam(value = "id") Long clId, Model model){
        CLStatus clStatus = clStatusService.getById(clId);
        if(clStatus == null)
            throw new NotFoundException(CLStatus.class);
            model.addAttribute("clStatus",clStatus);
            return "collection/settings/clStatus/create";
    }

    @GetMapping("view")
    public String viewLoc(@RequestParam(value = "id") Long clId, Model model) {
        model.addAttribute("clStatus", clStatusService.getById(clId));
        return "collection/settings/clStatus/view";
    }

    @GetMapping(value = "/clList")
    @ResponseBody
    public List<CLStatus> list(){
        List<CLStatus> clStatusList = clStatusService.getClStatusList();
        return clStatusList;
    }

    @GetMapping(value = "/getById")
    @ResponseBody
    public CLStatus viewGet(@RequestParam(value = "id") Long locId) {
        CLStatus clStatus = clStatusService.getById(locId);
        return clStatus;
    }
}
