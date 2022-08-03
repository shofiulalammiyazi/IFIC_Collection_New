package com.unisoft.collection.KPI.agency.card.targetByAmount;
/*
Created by   Islam at 9/22/2019
*/

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgencyKpiTargetCardAmountSetupRepository extends JpaRepository<AgencyKpiTargetCardAmountSetup, Long> {
}
