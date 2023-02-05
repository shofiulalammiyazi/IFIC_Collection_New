package com.unisoft.collection.settings.loanType;

import java.util.List;

public interface LoanTypeRepository {

    List<LoanTypeEntity> findByEnable(boolean enabled);
}
