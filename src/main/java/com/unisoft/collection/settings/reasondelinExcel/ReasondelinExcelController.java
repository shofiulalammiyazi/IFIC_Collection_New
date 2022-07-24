package com.unisoft.collection.settings.reasondelinExcel;

import com.unisoft.collection.reasonDelinquency.ReasonDelinquency;
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
@RequestMapping("/collection/settings/reasonForDelinExcel")
public class ReasondelinExcelController {

    @Autowired
    private ReasondelinExcelService reasondelinExcelService;

    @GetMapping(value = "/create")
    public String create(){
        return "collection/distribution/loan/reasonDelineExcel/create";
    }

    @PostMapping(value = "/create")
    public String saveReasonDelinExcel(@RequestParam("file") MultipartFile file, HttpSession session, Model model) {
        if(file.isEmpty()){
            model.addAttribute("validationError","Attach a excel file");
            return "collection/distribution/loan/reasonDelineExcel/create";
        }

        if(!file.getContentType().equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet") && !file.getContentType().equals("application/vnd.ms-excel")){
            model.addAttribute("validationError","Only excel file format is supported");
            return "collection/distribution/loan/reasonDelineExcel/create";
        }

        Map errors = reasondelinExcelService.saveReasonDelinExcel(file);
        model.addAttribute("distributionErrors", errors);
        return "redirect:/collection/settings/reasonForDelinExcel/list";
    }

    @GetMapping(value = "/list")
    public String list(Model model){
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("reasonExcelList", reasondelinExcelService.findAll());
        return "collection/distribution/loan/reasonDelineExcel/list";
    }

    @GetMapping(value = "/find")
    @ResponseBody
    public ReasonDelinquency findReasonByAccountNo(@RequestParam String accountNo){
        ReasonDelinquency reasonDelinquency = reasondelinExcelService.findByAccountNo(accountNo);
        return reasonDelinquency;
    }
}
