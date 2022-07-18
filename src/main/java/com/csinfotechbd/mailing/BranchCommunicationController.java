package com.csinfotechbd.mailing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/branch/communication/via/mail")
public class BranchCommunicationController {
    
    @Autowired
    private SendMailService sendMailService;
    
    @GetMapping(value = "/create")
    public String create(){
        return "collection/mailing/branchCommunicationViaMail";
    }
    
    @PostMapping(value = "/sent")
    @ResponseBody
    public boolean send(@RequestBody Mail mail){
        return sendMailService.sendMail(mail);
    }
}
