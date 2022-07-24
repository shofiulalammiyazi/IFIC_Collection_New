package com.unisoft.collection.settings.officeTimeSetup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfficeTimeSetupRepository extends JpaRepository<OfficeTimeSetupInfo, Long> {
    OfficeTimeSetupInfo findFirstByEnabledOrderByCreatedDateDesc(boolean enabled);
}
