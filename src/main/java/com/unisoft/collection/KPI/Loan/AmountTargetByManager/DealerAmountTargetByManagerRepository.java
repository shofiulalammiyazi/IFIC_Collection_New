package com.unisoft.collection.KPI.Loan.AmountTargetByManager;
/*
Created by   Islam at 9/23/2019
*/

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DealerAmountTargetByManagerRepository extends JpaRepository<DealerAmountTargetByManager, Long> {
}
