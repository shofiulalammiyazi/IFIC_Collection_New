package com.csinfotechbd.collection.settings.considerAttempt;

import com.csinfotechbd.exception.NotFoundException;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/collection/considerAttempt")
public class ConsiderAttemptController {

    @Autowired
    private ConsiderAttemptService considerAttemptService;

    @GetMapping(value = "/list")
    public String viewList(Model model){
        model.addAttribute("considerAttemptList", considerAttemptService.getConsiderAttemptList());
        return "collection/settings/considerAttempt/edit";
    }

    @GetMapping(value = "/create")
    public String create(Model model){
        model.addAttribute("considerAttempt", new ConsiderAttempt());
        return "collection/settings/considerAttempt/create";
    }

    @PostMapping(value = "/create", consumes = (MediaType.APPLICATION_FORM_URLENCODED_VALUE))
    public String createPage(ConsiderAttempt considerAttempt, Model model){
        String output = considerAttemptService.save(considerAttempt);
        switch (output) {
            case "1":
                return "redirect:/collection/considerAttempt/list";
            default:
                model.addAttribute("error", output);
        }
        model.addAttribute("considerAttempt", considerAttempt);
        return "collection/settings/considerAttempt/create";
    }

    @GetMapping("edit")
    public String edit(@RequestParam(value = "id") Long conId, Model model) {
        ConsiderAttempt considerAttempt = considerAttemptService.getById(conId);
        Gson gson = new Gson();
        if (considerAttempt == null) throw new NotFoundException(ConsiderAttempt.class);
        model.addAttribute("considerAttempt", considerAttempt);
        model.addAttribute("typeList", considerAttempt.getType());
        return "collection/settings/considerAttempt/create";
    }

    @GetMapping("view")
    public String viewLoc(@RequestParam(value = "id") Long locId, Model model) {
        model.addAttribute("considerAttempt", considerAttemptService.getById(locId));
        return "collection/settings/considerAttempt/view";
    }

    @GetMapping(value = "/attemptList/loan")
    @ResponseBody
    public List<ConsiderAttempt> listLoan(){
        List<ConsiderAttempt> considerAttemptList = considerAttemptService.getConsiderAttemptListLoan();
        return considerAttemptList;
    }

    @GetMapping(value = "/attemptList/card")
    @ResponseBody
    public List<ConsiderAttempt> listCard(){
        List<ConsiderAttempt> considerAttemptList = considerAttemptService.getConsiderAttemptListCard();
        return considerAttemptList;
    }

    @GetMapping(value = "/getById")
    @ResponseBody
    public ConsiderAttempt viewGet(@RequestParam(value = "id") Long locId) {
        ConsiderAttempt considerAttempt = considerAttemptService.getById(locId);
        return considerAttempt;
    }
}

