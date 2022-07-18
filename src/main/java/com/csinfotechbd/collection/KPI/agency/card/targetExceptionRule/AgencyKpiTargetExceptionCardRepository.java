package com.csinfotechbd.collection.KPI.agency.card.targetExceptionRule;
/*
Created by Monirul Islam at 9/23/2019 
*/

import com.csinfotechbd.collection.KPI.agency.card.targetByCommision.AgencyKpiCommisionRateCardSetup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgencyKpiTargetExceptionCardRepository extends JpaRepository<AgencyKpiTargetExceptionCard, Long> {

}
