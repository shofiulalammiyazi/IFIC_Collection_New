package com.csinfotechbd.collection.settings.previousaccount;

import com.csinfotechbd.user.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/collection/loan/settings/previous-account")
public class PreviousAccountController {

    @Autowired
    private PreviousAccountService service;

    @GetMapping("/create")
    public String create(){
        return "collection/distribution/loan/previousaccount/create";
    }

    @GetMapping("/list")
    public String list(Model model){
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("previousAccount", service.findAllPreviousAccount());
        return "collection/distribution/loan/previousaccount/previousaccountlist";
    }


    @PostMapping("/save")
    public String saveLoanGuarantor(@RequestParam("file") MultipartFile file, HttpSession session, Model model) {
        if(file.isEmpty()){
            model.addAttribute("validationError","Attach a excel file");
            return "collection/distribution/loan/previousaccount/create";
        }

        if(!file.getContentType().equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet") && !file.getContentType().equals("application/vnd.ms-excel")){
            model.addAttribute("validationError","Only excel file format is supported");
            return "collection/distribution/loan/previousaccount/create";
        }

        Map errors = service.savePrevious(file);
        model.addAttribute("distributionErrors", errors);
        return "redirect:/collection/loan/settings/previous-account/list";
    }



    @GetMapping("/find")
    @ResponseBody
    public PreviousAccountEntity findAccountByLoanAccountNo(@RequestParam String accountNo){
        PreviousAccountEntity accountEntity = service.findPreviousAcccountByLoanAccountNo(accountNo);
        return accountEntity;
    }



}
