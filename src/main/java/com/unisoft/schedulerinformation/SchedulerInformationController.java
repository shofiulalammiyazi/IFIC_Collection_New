package com.unisoft.schedulerinformation;

import com.google.gson.Gson;
import com.unisoft.collection.KPI.Loan.TargetWeightByAmount.LoanKPITargetWeightByAmountEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/scheduler/information")
public class SchedulerInformationController {

    @Autowired
    private SchedulerInformationService schedulerInformationService;

    @GetMapping(value = "/list")
    public String viewAll(Model model) {

        model.addAttribute("schedulerList",schedulerInformationService.findAll());
        return "collection/schedulerinformation/list";
    }

    @GetMapping("create")
    public String addNewPage(Model model){

        model.addAttribute("schedulerInformation",new SchedulerInformationEntity());

        return "collection/schedulerinformation/create";
    }

    @PostMapping(value = "/save")
    public String save(SchedulerInformationEntity schedulerInformationEntity){

        //schedulerInformationService.save(schedulerInformationEntity);

        SchedulerInformationEntity schedulerInformationEntity1 = schedulerInformationService.getBySchedulerName(schedulerInformationEntity.getSchedulerName());

        if(schedulerInformationEntity1 == null) {
            schedulerInformationService.save(schedulerInformationEntity);
        }
        else{
            schedulerInformationEntity.setId(schedulerInformationEntity1.getId());
            schedulerInformationService.save(schedulerInformationEntity);
        }

        return "redirect:/scheduler/information/list";
    }

    @GetMapping("edit")
    public String editPage(@RequestParam(value = "id") Long findId, Model model){
        SchedulerInformationEntity schedulerInformationEntity= schedulerInformationService.getById(findId);

        model.addAttribute("schedulerInformation", schedulerInformationEntity);

        return "collection/schedulerinformation/create";
    }

}
