package com.csinfotechbd.customerloanprofile.legalAction;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/customer-loan-profile/legal-action")
public class LegalActionController {


    @Autowired
    private LegalActionService legalActionService;

    @GetMapping("/find")
    @ResponseBody
    public List<LegalActionDto> getLegalAction(@RequestParam String accountNo){
        List<LegalActionDto> legalActionDtoList = legalActionService.getLegalAction(accountNo);

        return legalActionDtoList;
    }
}
