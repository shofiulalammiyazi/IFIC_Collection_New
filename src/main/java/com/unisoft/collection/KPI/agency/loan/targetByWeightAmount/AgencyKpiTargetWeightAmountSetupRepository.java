package com.unisoft.collection.KPI.agency.loan.targetByWeightAmount;
/*
Created by   Islam at 9/19/2019
*/

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgencyKpiTargetWeightAmountSetupRepository extends JpaRepository<AgencyKpiTargetWeightAmountSetup, Long> {
}
