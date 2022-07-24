package com.unisoft.collection.samd.dataEntry.generationClassificationProvisioning.automatedProvisioning;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutomatedCLProvisioningRepository extends JpaRepository<AutomatedCLProvisioning, Long> {

    AutomatedCLProvisioning findAutomatedProvisioningByCustomerId(String customerId);
}
