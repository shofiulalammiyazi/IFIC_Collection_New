package com.csinfotechbd.collection.KPI.agency.card.targetByAccount;
/*
Created by Monirul Islam at 9/22/2019 
*/

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgencyKpiTargetCardAccountSetupRepository extends JpaRepository<AgencyKpiTargetCardAccountSetup, Long> {
}
