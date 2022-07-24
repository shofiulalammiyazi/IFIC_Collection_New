package com.unisoft.collection.samd.dataEntry.futureRecoveryPlan;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FutureRecoveryPlanRepository extends JpaRepository<FutureRecoveryPlan, Long> {
    List<FutureRecoveryPlan> findAllByAccountNumber(String accountNumber);

}
