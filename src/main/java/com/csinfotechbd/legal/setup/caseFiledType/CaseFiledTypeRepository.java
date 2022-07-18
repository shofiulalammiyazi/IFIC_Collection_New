package com.csinfotechbd.legal.setup.caseFiledType;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CaseFiledTypeRepository extends JpaRepository<CaseFiledType, Long> {

    List<CaseFiledType> findByEnabledOrderById(boolean b);

    List<CaseFiledType> findByEnabledOrderBySequence(boolean b);

    CaseFiledType findByName(String name);

    boolean existsByName(String name);
}
