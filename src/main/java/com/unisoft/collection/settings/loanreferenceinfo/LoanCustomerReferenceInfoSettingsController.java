package com.unisoft.collection.settings.loanreferenceinfo;

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
@RequestMapping("/collection/loan/settings/reference-info")
public class LoanCustomerReferenceInfoSettingsController {
@Autowired
private  LoanCustomerReferenceInfoSettingsService service;


    @GetMapping("/create")
    public String create(){
        return "collection/distribution/loan/reference/create";
    }

    @PostMapping("save")
    public String saveLoanReference(@RequestParam("file") MultipartFile file, HttpSession session, Model model) {
        if(file.isEmpty()){
            model.addAttribute("validationError","Attach a excel file");
            return "collection/distribution/loan/reference/create";
        }

        if(!file.getContentType().equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet") && !file.getContentType().equals("application/vnd.ms-excel")){
            model.addAttribute("validationError","Only excel file format is supported");
            return "collection/distribution/loan/reference/create";
        }

        Map errors = service.saveLoanCustomerReferenceInfo(file);
        model.addAttribute("distributionErrors", errors);
        return "redirect:/collection/loan/settings/reference-info/list";
    }



    @GetMapping("/list")
    public String list(Model model){
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", user.getEmpId());
        model.addAttribute("referenceInfoList",service.AllPendingReferenceInfo());
        model.addAttribute("status","pending");
        return "collection/distribution/loan/reference/reference-list";
    }

    @GetMapping("/approved-list")
    public String approvedList(Model model){
        model.addAttribute("referenceInfoList",service.AllApprovedReferenceInfo());
        model.addAttribute("status","approved");
        return "collection/distribution/loan/reference/reference-list";
    }
    @GetMapping("/reject-list")
    public String rejectList(Model model){
        model.addAttribute("referenceInfoList",service.AllRejecteReferenceInfo());
        model.addAttribute("status","reject");
        return "collection/distribution/loan/reference/reference-list";
    }



    @PostMapping("/approved")
    @ResponseBody
    public  boolean approvedReference(@RequestBody LoanCustomerReferenceInfoWrapper referenceInfoEntityList){
        service.approvedReferenceService(referenceInfoEntityList.getReferenceInfoEntityList());
        return true;

    }

    @PostMapping("/reject")
    @ResponseBody
    public  boolean rejectReference(@RequestBody LoanCustomerReferenceInfoWrapper referenceInfoEntityList){
        service.rejectReferenceService(referenceInfoEntityList.getReferenceInfoEntityList());
        return true;
    }


}
