package com.csinfotechbd.collection.KPI.Loan.AmountTargetByManager;
/*
Created by Monirul Islam at 9/23/2019 
*/

import com.csinfotechbd.collection.KPI.Loan.AccountTargetByManager.DealerAccountTargetByManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DealerAmountTargetByManagerRepository extends JpaRepository<DealerAmountTargetByManager, Long> {
}
