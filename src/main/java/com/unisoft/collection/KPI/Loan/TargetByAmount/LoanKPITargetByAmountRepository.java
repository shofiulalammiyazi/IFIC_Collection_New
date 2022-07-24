package com.unisoft.collection.KPI.Loan.TargetByAmount;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanKPITargetByAmountRepository extends JpaRepository<LoanKPITargetByAmountEntity, Long> {
    List<LoanKPITargetByAmountEntity> findAllByOrderByIdDesc();
}
