package com.csinfotechbd.collection.KPI.agency.loan.targetByCommision;
/*
Created by Monirul Islam at 9/19/2019 
*/

import lombok.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Entity;

@Repository
public interface AgencyKpiCommisionRateLoanSetupRepository extends JpaRepository<AgencyKpiCommisionRateLoanSetup, Long> {
}
