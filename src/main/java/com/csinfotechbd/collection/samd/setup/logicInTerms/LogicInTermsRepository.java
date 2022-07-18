package com.csinfotechbd.collection.samd.setup.logicInTerms;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogicInTermsRepository extends JpaRepository<LogicInTerms, Long> {
    LogicInTerms findLogicInTermsById(Long id);
}
