package com.csinfotechbd.legal.setup.caseType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CaseTypeRepository extends JpaRepository<CaseType, Long> {
    List<CaseType> findByEnabled(boolean b);

    //    List<CaseType> findAllByEnabled(boolean b);
    CaseType findFirstByOrderByIdDesc();

    List<CaseType> findByCaseFiledTypeIdOrderBySequence(Long cfid);

    List<CaseType> findByCaseFiledTypeIdAndEnabledOrderBySequence(Long cfid, boolean enabled);

    List<CaseType> findByCaseFiledTypeIdAndCaseFiledSubTypeIdOrderBySequence(Long cfid, Long csfid);

    List<CaseType> findByCaseFiledTypeNameAndEnabledOrderBySequence(String caseFiledSubType, boolean enabled);

    List<CaseType> findByCaseFiledSubTypeNameAndEnabledOrderBySequence(String caseFiledSubType, boolean enabled);

    List<CaseType> findByCaseFiledTypeIdAndCaseFiledSubTypeIdAndEnabledOrderBySequence(Long cfid, Long csfid, boolean enabled);

    @Query("SELECT c FROM CaseType c WHERE c.id IN (:caseTypeIds)")
    List<CaseType> findCaseTypesByIds(@Param("caseTypeIds") List<Long> caseTypeIds);

    @Query("SELECT c FROM CaseType c WHERE LOWER(c.name) LIKE CONCAT('%',LOWER(:name),'%') OR c.name = 'Others' ORDER BY c.sequence")
    List<CaseType> findByNameContainsOrderBySequence(@Param("name") String caseTypeName);

    @Query("SELECT new com.csinfotechbd.legal.setup.caseType.CaseTypeDto(" +
            "c.id, c.name, c.sequence, c.caseFiledType.id, c.caseFiledSubType.id) " +
            "FROM CaseType c WHERE c.enabled = :enabled")
    List<CaseTypeDto> findEnabledCaseTypes(@Param("enabled") boolean enabled);


    CaseType findByName(String ni_act);
}
