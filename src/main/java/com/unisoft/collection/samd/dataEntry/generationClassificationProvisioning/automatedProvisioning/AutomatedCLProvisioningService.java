package com.unisoft.collection.samd.dataEntry.generationClassificationProvisioning.automatedProvisioning;


public interface AutomatedCLProvisioningService {
    AutomatedCLProvisioning save(AutomatedCLProvisioning automatedCLProvisioning);

    AutomatedCLProvisioning findAutomatedProvisioningByCustomerId(String customerId);
}
