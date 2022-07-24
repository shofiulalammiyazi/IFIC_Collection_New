package com.unisoft.collection.settings.loanguarantorInfo;

import com.unisoft.user.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/collection/loan/settings/guarantor-info")
public class LoanCustomerGuarantorInfoSettingsController {

    @Autowired
    private  LoanCustomerGuarantorInfoSettingsService service;

    @GetMapping("/create")
    public String create(){
        return "collection/distribution/loan/guarantor/create";
    }

    @GetMapping("/list")
    public String list(Model model){
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", user.getEmpId());
        model.addAttribute("guarantorList", service.AllPendingGuarantorInfo());
        model.addAttribute("status", "pending");
        return "collection/distribution/loan/guarantor/guarantor";
    }


    @PostMapping("/save")
    public String saveLoanGuarantor(@RequestParam("file") MultipartFile file, HttpSession session, Model model) {
        if(file.isEmpty()){
            model.addAttribute("validationError","Attach a excel file");
            return "collection/distribution/loan/guarantor/create";
        }

        if(!file.getContentType().equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet") && !file.getContentType().equals("application/vnd.ms-excel")){
            model.addAttribute("validationError","Only excel file format is supported");
            return "collection/distribution/loan/guarantor/create";
        }

        Map errors = service.saveLoanCustomerGuarantor(file);
        model.addAttribute("distributionErrors", errors);
        return "redirect:/collection/loan/settings/guarantor-info/list";
    }


    @GetMapping("/approved-list")
    public String approvedList(Model model){
        model.addAttribute("guarantorList",service.AllApprovedGuarantorInfo());
        model.addAttribute("status","approved");
        return "collection/distribution/loan/guarantor/guarantor";
    }
    @GetMapping("/reject-list")
    public String rejectList(Model model){
        model.addAttribute("guarantorList",service.AllRejecteGuarantorInfo());
        model.addAttribute("status","reject");
        return "collection/distribution/loan/guarantor/guarantor";
    }



    @PostMapping("/approved")
    @ResponseBody
    public  boolean approvedGuarantor(@RequestBody LoanCustomerGurantornfoWrapper guarantorInfoEntityList){
        service.approvedGuarantorService(guarantorInfoEntityList.getGuarantorInfoEntityList());
        return true;

    }

    @PostMapping("/reject")
    @ResponseBody
    public  boolean rejectGuarantor(@RequestBody LoanCustomerGurantornfoWrapper guarantorInfoEntityList){
        service.rejectguarantorService(guarantorInfoEntityList.getGuarantorInfoEntityList());
        return true;
    }


}
