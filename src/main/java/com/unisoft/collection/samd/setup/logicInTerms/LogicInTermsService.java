package com.unisoft.collection.samd.setup.logicInTerms;

import java.util.List;

public interface LogicInTermsService {
    List<LogicInTerms> findAll();

    void save(LogicInTerms logicInTerms);

    LogicInTerms findLogicInTermsById(Long id);
}
