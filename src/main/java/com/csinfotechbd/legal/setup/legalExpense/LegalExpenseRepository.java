package com.csinfotechbd.legal.setup.legalExpense;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LegalExpenseRepository extends JpaRepository<LegalExpenseEntity, Long> {
    List<LegalExpenseEntity> findByEnabled(boolean b);
}
