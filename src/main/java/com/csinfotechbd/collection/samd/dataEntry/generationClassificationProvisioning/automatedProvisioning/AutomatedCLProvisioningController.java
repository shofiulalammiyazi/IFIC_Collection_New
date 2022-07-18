package com.csinfotechbd.collection.samd.dataEntry.generationClassificationProvisioning.automatedProvisioning;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/collection/samd/data-entry/generation-classification-provisioning/automated-provisioning")
public class AutomatedCLProvisioningController {

    @Autowired
    private AutomatedCLProvisioningService automatedCLProvisioningService;



    @GetMapping("/find")
    @ResponseBody
    public AutomatedCLProvisioning getAutomatedProvisioningByCustomerId(@RequestParam String customerId){
        AutomatedCLProvisioning automatedCLProvisionings = automatedCLProvisioningService.findAutomatedProvisioningByCustomerId(customerId);
        return automatedCLProvisionings;
    }



    @PostMapping("/save")
    @ResponseBody
    public AutomatedCLProvisioning createAutomatedClProvisioning(AutomatedCLProvisioning automatedCLProvisioning){
        AutomatedCLProvisioning automatedCLProvisioning1 = automatedCLProvisioningService.save(automatedCLProvisioning);
        System.out.println("test");
        return automatedCLProvisioning1;
    }
}
