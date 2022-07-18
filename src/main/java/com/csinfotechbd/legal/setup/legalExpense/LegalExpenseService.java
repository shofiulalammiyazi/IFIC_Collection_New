package com.csinfotechbd.legal.setup.legalExpense;



import com.csinfotechbd.legal.setup.lawyers.Lawyer;

import java.util.List;

public interface LegalExpenseService {

    String save(LegalExpenseEntity legalExpenseEntity);

    List<LegalExpenseEntity> findByEnabled(boolean enabled);

    LegalExpenseEntity findById(Long id);
}
