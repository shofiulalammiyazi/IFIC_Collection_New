package com.csinfotechbd.legal.setup.caseFiledSubType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CaseFiledSubTypeRepository extends JpaRepository<CaseFiledSubType, Long> {

    List<CaseFiledSubType> findByEnabledOrderBySequence(boolean b);
//
//    @Query("SELECT C FROM CaseFiledSubType C WHERE C.enabled = :enabled")
//    List<CaseFiledSubTypeDto> findDtosByEnabled(@Param("enabled") boolean enabled);

    CaseFiledSubType findByName(String name);

    boolean existsByName(String name);
}
