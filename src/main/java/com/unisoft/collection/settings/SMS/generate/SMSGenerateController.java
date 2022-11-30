package com.unisoft.collection.settings.SMS.generate;

import com.unisoft.collection.settings.SMS.sendSms.SentSMSToCustomerController;
import com.unisoft.collection.settings.SMS.template.TemplateGenerate;
import com.unisoft.collection.settings.SMS.template.TemplateGenerateRepository;
import com.unisoft.collection.settings.SMS.template.TemplateGenerateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping(value = "/collection/generate/sms")
public class SMSGenerateController {

    @Autowired
    private TemplateGenerateService templateGenerateService;

    @GetMapping("/generate")
    public String generateLetter(@RequestParam(name = "account") String[] accounts,
                                 @RequestParam(name = "letterType") Long letterType,
                                 @RequestParam(name = "mobile", required = false) String mobile,
                                 Model model){

        TemplateGenerate template = templateGenerateService.findTemGenById(letterType);
        model.addAttribute("template", template);
        //SentSMSToCustomerController.sendsmss(mobile,template.getMassege());
        //model.addAttribute("accounts", this.getAccountsInfo(unit, accounts, mobile));
        //model.addAttribute("unit", unit);

        return "collection/settings/LetterTemplates/letter_view";
    }
}