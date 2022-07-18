package com.csinfotechbd.collection.samd.dataEntry.generationClassificationProvisioning.automatedProvisioning;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutomatedCLProvisioningRepository extends JpaRepository<AutomatedCLProvisioning, Long> {

    AutomatedCLProvisioning findAutomatedProvisioningByCustomerId(String customerId);
}
