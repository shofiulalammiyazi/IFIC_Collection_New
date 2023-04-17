package com.unisoft.schedulerinformation;

import com.google.gson.Gson;
import com.unisoft.collection.KPI.Loan.TargetWeightByAmount.LoanKPITargetWeightByAmountEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/scheduler/information")
public class SchedulerInformationController {


    @GetMapping(value = "/list")
    public String viewAll(Model model) {

        return "collection/schedulerinformation/list";
    }

    @GetMapping("create")
    public String addNewPage(Model model){

        return "collection/schedulerinformation/create";
    }


}
