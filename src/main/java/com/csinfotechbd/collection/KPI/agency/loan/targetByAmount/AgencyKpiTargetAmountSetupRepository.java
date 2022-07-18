package com.csinfotechbd.collection.KPI.agency.loan.targetByAmount;
/*
Created by Monirul Islam at 9/18/2019 
*/

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgencyKpiTargetAmountSetupRepository extends JpaRepository<AgencyKpiTargetAmountSetup, Long> {
}
