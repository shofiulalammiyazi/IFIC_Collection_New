package com.csinfotechbd.legal.setup.courseOfAction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseOfActionRepository extends JpaRepository<CourseOfAction, Long> {

    List<CourseOfAction> findByEnabled(boolean b);

    @Query("SELECT DISTINCT c FROM CourseOfAction c JOIN c.caseTypes ct WHERE ct.id = :caseTypeId ORDER BY c.sequence")
    List<CourseOfAction> findByCaseTypeId(@Param("caseTypeId") Long caseTypeIds);

    @Query("SELECT DISTINCT c FROM CourseOfAction c JOIN c.caseTypes ct WHERE ct.id = :caseTypeId AND c.contestedType = :contestedType ORDER BY c.sequence")
    List<CourseOfAction> findByCaseTypeIdAndContestedType(@Param("caseTypeId") Long caseTypeIds,
                                                          @Param("contestedType") String contestedType);

    CourseOfAction findByName(String name);

    CourseOfAction findFirstByNameContainsOrderBySequence(@Param("name") String caseStatusName);

}
