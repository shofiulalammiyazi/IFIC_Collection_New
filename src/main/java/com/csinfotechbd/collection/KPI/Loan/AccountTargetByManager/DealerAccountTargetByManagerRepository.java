package com.csinfotechbd.collection.KPI.Loan.AccountTargetByManager;
/*
Created by Monirul Islam at 9/23/2019 
*/

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DealerAccountTargetByManagerRepository extends JpaRepository<DealerAccountTargetByManager, Long> {
}
