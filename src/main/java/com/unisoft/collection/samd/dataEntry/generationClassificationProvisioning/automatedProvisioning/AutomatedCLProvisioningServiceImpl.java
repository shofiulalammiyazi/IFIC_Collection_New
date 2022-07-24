package com.unisoft.collection.samd.dataEntry.generationClassificationProvisioning.automatedProvisioning;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutomatedCLProvisioningServiceImpl implements AutomatedCLProvisioningService{

    @Autowired
    private AutomatedCLProvisioningRepository automatedCLProvisioningRepository;

    @Override
    public AutomatedCLProvisioning save(AutomatedCLProvisioning automatedCLProvisioning) {
        AutomatedCLProvisioning automatedCLProvisioning1 = automatedCLProvisioningRepository.save(automatedCLProvisioning);
        return automatedCLProvisioning1;
    }

    @Override
    public AutomatedCLProvisioning findAutomatedProvisioningByCustomerId(String customerId) {
        AutomatedCLProvisioning automatedCLProvisioning = automatedCLProvisioningRepository.findAutomatedProvisioningByCustomerId(customerId);
        return automatedCLProvisioning;
    }
}
