package com.csinfotechbd.collection.samd.dataEntry.generationClassificationProvisioning.automatedProvisioning;


import java.util.List;

public interface AutomatedCLProvisioningService {
    AutomatedCLProvisioning save(AutomatedCLProvisioning automatedCLProvisioning);

    AutomatedCLProvisioning findAutomatedProvisioningByCustomerId(String customerId);
}
